### 读写分离 - 动态切换数据源版本 1.0
- 主要原理：使用Spring提供的路由数据源，以及AOP
- 主要过程如下

1.多数据源配置。1个master，2两个slave，1个路由数据源
```java
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                          @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

}
```

2.MyBatis配置，略

3.通过ThreadLocal将数据源设置到每个线程上下文中。从库数据源轮询方式分配
```java
package pers.ryan.database.config;
import lombok.extern.slf4j.Slf4j;
import pers.ryan.database.enums.DBTypeEnum;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.info("切换到master");
    }

    public static void slave() {
        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            set(DBTypeEnum.SLAVE1);
            log.info("切换到slave1");
        } else {
            set(DBTypeEnum.SLAVE2);
            log.info("切换到slave2");
        }
    }

}
```

4.获取路由key
```java
package pers.ryan.database.config;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }

}
```
5.增加一个注解，使用该注解则操作主库
```java
public @interface Master {
}
```
6.最后，通过AOP，设置路由key。通过方法命名和是否有@Master注解判断读写操作
```java
package pers.ryan.database.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import pers.ryan.database.config.DBContextHolder;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(pers.ryan.database.annotation.Master) " +
            "&& (execution(* pers.ryan.database.service..*.select*(..)) " +
            "|| execution(* pers.ryan.database.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(pers.ryan.database.annotation.Master) " +
            "|| execution(* pers.ryan.database.service..*.insert*(..)) " +
            "|| execution(* pers.ryan.database.service..*.add*(..)) " +
            "|| execution(* pers.ryan.database.service..*.update*(..)) " +
            "|| execution(* pers.ryan.database.service..*.edit*(..)) " +
            "|| execution(* pers.ryan.database.service..*.delete*(..)) " +
            "|| execution(* pers.ryan.database.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
```

---
### 运行效果

1.测试从库读取数据
```java
    @Test
    public void testSelect() {
        for (int i = 1; i < 6; i++) {
            log.info("第{}次查询", i);
            log.info(orderService.selectById(i).toString());
        }
    }
```

- 成功设置数据源，并有轮询效果

```
2020-12-05 03:04:40.929  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : 第1次查询
2020-12-05 03:04:40.940  INFO 1877916 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到slave2
2020-12-05 03:04:40.978  INFO 1877916 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-05 03:04:41.232  INFO 1877916 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-12-05 03:04:41.303  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=1, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)
2020-12-05 03:04:41.303  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : 第2次查询
2020-12-05 03:04:41.304  INFO 1877916 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到slave1
2020-12-05 03:04:41.304  INFO 1877916 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Starting...
2020-12-05 03:04:41.310  INFO 1877916 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Start completed.
2020-12-05 03:04:41.315  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=2, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)
2020-12-05 03:04:41.315  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : 第3次查询
2020-12-05 03:04:41.315  INFO 1877916 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到slave2
2020-12-05 03:04:41.318  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=3, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)
2020-12-05 03:04:41.319  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : 第4次查询
2020-12-05 03:04:41.319  INFO 1877916 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到slave1
2020-12-05 03:04:41.323  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=4, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)
2020-12-05 03:04:41.323  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : 第5次查询
2020-12-05 03:04:41.323  INFO 1877916 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到slave2
2020-12-05 03:04:41.325  INFO 1877916 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=5, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)

```

2.测试使用了@Master注解的效果
```java
    @Test
    public void testSelectFromMaster() {
        log.info(orderService.selectFromMaster(1L).toString());
    }

    @Master
    public OrderMain selectFromMaster(long id) {
        return orderMainMapper.selectByPrimaryKey(id);
    }
```
- 虽然是select，也从主库读取了
```
2020-12-05 03:08:34.863  INFO 1877868 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到master
2020-12-05 03:08:34.908  INFO 1877868 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-05 03:08:35.204  INFO 1877868 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-12-05 03:08:35.298  INFO 1877868 --- [           main] pers.ryan.database.ReadingWritingTest    : OrderMain(id=1, orderSn=1, userId=1, shippingName=n, province=1, city=1, district=1, address=n, paymentMethod=1, orderMoney=100.00, shippingMoney=10.00, paymentMoney=10.00, shippingCompName=null, shippingSn=null, createTime=1607103033653, shippingTime=null, payTime=null, receiveTime=null, orderStatus=1, modifiedTime=1607103033653)
```

3.测试插入数据
```java
    @Test
    public void testInsert() {
        orderService.insert(getOrder());
    }
```
```
2020-12-05 03:09:42.829  INFO 1882828 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-05 03:09:43.166  INFO 1882828 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-12-05 03:09:43.188  INFO 1882828 --- [           main] p.ryan.database.config.DBContextHolder   : 切换到master
```