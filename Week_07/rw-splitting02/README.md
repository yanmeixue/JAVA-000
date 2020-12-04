### 读写分离 - 数据库中间件版本 3.0

- 引入sharding-jdbc，正确配置后则有读写分离功能，且轮询读取各从库

```java
package pers.ryan.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingWritingTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            String readSql = "SELECT * FROM `order_main` limit 1";
            String writeSql = "INSERT INTO `order_main`(`order_sn`, `user_id`, `shipping_name`, `province`, `city`, `district`, " +
                    "`address`, `payment_method`, `order_money`, `shipping_money`, `payment_money`, `shipping_comp_name`, `shipping_sn`, " +
                    "`create_time`, `shipping_time`, `pay_time`, `receive_time`, `order_status`, `modified_time`) VALUES (1, 1, 'n', 1, " +
                    "1, 1, 'n', 1, 100.00, 10.00, 10.00, NULL, NULL, 1607103033653, NULL, NULL, NULL, 1, 1607103033653)";
            log.info("测试查询数据");
            for (int i = 0; i < 5; i++) {
                statement.executeQuery(readSql);
            }
            log.info("测试插入数据");
            statement.executeUpdate(writeSql);
            log.info("测试插入数据结束");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}

```

- 输出日志
```
2020-12-05 04:23:36.982  INFO 1896956 --- [           main] o.a.s.core.log.ConfigurationLogger       : MasterSlaveRuleConfiguration:
masterDataSourceName: master
name: ms
slaveDataSourceNames:
- slave1
- slave2

2020-12-05 04:23:36.985  INFO 1896956 --- [           main] o.a.s.core.log.ConfigurationLogger       : Properties:
sql.show: 'true'

2020-12-05 04:23:37.045  INFO 1896956 --- [           main] ShardingSphere-metadata                  : Loading 7 tables' meta data.
2020-12-05 04:23:37.176  INFO 1896956 --- [           main] ShardingSphere-metadata                  : Meta data load finished, cost 191 milliseconds.
2020-12-05 04:23:37.456  INFO 1896956 --- [           main] pers.ryan.database.ReadingWritingTest    : Started ReadingWritingTest in 2.9 seconds (JVM running for 4.246)
2020-12-05 04:23:37.642  INFO 1896956 --- [           main] pers.ryan.database.ReadingWritingTest    : 测试查询数据
2020-12-05 04:23:38.196  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.196  INFO 1896956 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@31db34da, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@109f8c7e), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@109f8c7e, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=order_sn, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_name, alias=Optional.empty), ColumnProjection(owner=null, name=province, alias=Optional.empty), ColumnProjection(owner=null, name=city, alias=Optional.empty), ColumnProjection(owner=null, name=district, alias=Optional.empty), ColumnProjection(owner=null, name=address, alias=Optional.empty), ColumnProjection(owner=null, name=payment_method, alias=Optional.empty), ColumnProjection(owner=null, name=order_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_money, alias=Optional.empty), ColumnProjection(owner=null, name=payment_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_comp_name, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_sn, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_time, alias=Optional.empty), ColumnProjection(owner=null, name=receive_time, alias=Optional.empty), ColumnProjection(owner=null, name=order_status, alias=Optional.empty), ColumnProjection(owner=null, name=modified_time, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@5edacf20, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@16a5eb6d, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@7e307087, containsSubquery=false)
2020-12-05 04:23:38.196  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.200  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.200  INFO 1896956 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@724aefc3, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@7cfb4736), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@7cfb4736, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=order_sn, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_name, alias=Optional.empty), ColumnProjection(owner=null, name=province, alias=Optional.empty), ColumnProjection(owner=null, name=city, alias=Optional.empty), ColumnProjection(owner=null, name=district, alias=Optional.empty), ColumnProjection(owner=null, name=address, alias=Optional.empty), ColumnProjection(owner=null, name=payment_method, alias=Optional.empty), ColumnProjection(owner=null, name=order_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_money, alias=Optional.empty), ColumnProjection(owner=null, name=payment_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_comp_name, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_sn, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_time, alias=Optional.empty), ColumnProjection(owner=null, name=receive_time, alias=Optional.empty), ColumnProjection(owner=null, name=order_status, alias=Optional.empty), ColumnProjection(owner=null, name=modified_time, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@2a097d77, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@53c1179a, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@7650ded6, containsSubquery=false)
2020-12-05 04:23:38.200  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave2 ::: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.203  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.203  INFO 1896956 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@6a916402, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@1a47a1e8), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@1a47a1e8, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=order_sn, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_name, alias=Optional.empty), ColumnProjection(owner=null, name=province, alias=Optional.empty), ColumnProjection(owner=null, name=city, alias=Optional.empty), ColumnProjection(owner=null, name=district, alias=Optional.empty), ColumnProjection(owner=null, name=address, alias=Optional.empty), ColumnProjection(owner=null, name=payment_method, alias=Optional.empty), ColumnProjection(owner=null, name=order_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_money, alias=Optional.empty), ColumnProjection(owner=null, name=payment_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_comp_name, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_sn, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_time, alias=Optional.empty), ColumnProjection(owner=null, name=receive_time, alias=Optional.empty), ColumnProjection(owner=null, name=order_status, alias=Optional.empty), ColumnProjection(owner=null, name=modified_time, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@6d2a2560, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@62d1dc3c, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@3811510, containsSubquery=false)
2020-12-05 04:23:38.203  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.205  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.205  INFO 1896956 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@2a43e0ac, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@22d9bc14), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@22d9bc14, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=order_sn, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_name, alias=Optional.empty), ColumnProjection(owner=null, name=province, alias=Optional.empty), ColumnProjection(owner=null, name=city, alias=Optional.empty), ColumnProjection(owner=null, name=district, alias=Optional.empty), ColumnProjection(owner=null, name=address, alias=Optional.empty), ColumnProjection(owner=null, name=payment_method, alias=Optional.empty), ColumnProjection(owner=null, name=order_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_money, alias=Optional.empty), ColumnProjection(owner=null, name=payment_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_comp_name, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_sn, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_time, alias=Optional.empty), ColumnProjection(owner=null, name=receive_time, alias=Optional.empty), ColumnProjection(owner=null, name=order_status, alias=Optional.empty), ColumnProjection(owner=null, name=modified_time, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@346f41a9, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@1084f78c, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@25f723b0, containsSubquery=false)
2020-12-05 04:23:38.205  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave2 ::: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.206  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.206  INFO 1896956 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@40d60f2, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3382cf68), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3382cf68, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=order_sn, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_name, alias=Optional.empty), ColumnProjection(owner=null, name=province, alias=Optional.empty), ColumnProjection(owner=null, name=city, alias=Optional.empty), ColumnProjection(owner=null, name=district, alias=Optional.empty), ColumnProjection(owner=null, name=address, alias=Optional.empty), ColumnProjection(owner=null, name=payment_method, alias=Optional.empty), ColumnProjection(owner=null, name=order_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_money, alias=Optional.empty), ColumnProjection(owner=null, name=payment_money, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_comp_name, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_sn, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=shipping_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_time, alias=Optional.empty), ColumnProjection(owner=null, name=receive_time, alias=Optional.empty), ColumnProjection(owner=null, name=order_status, alias=Optional.empty), ColumnProjection(owner=null, name=modified_time, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@2f74900b, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@6d8796c1, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@2e26173, containsSubquery=false)
2020-12-05 04:23:38.207  INFO 1896956 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT * FROM `order_main` limit 1
2020-12-05 04:23:38.207  INFO 1896956 --- [           main] pers.ryan.database.ReadingWritingTest    : 测试插入数据
2020-12-05 04:23:38.443  INFO 1896956 --- [           main] pers.ryan.database.ReadingWritingTest    : 测试插入数据结束
2020-12-05 04:23:38.458  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} closing ...
2020-12-05 04:23:38.461  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} closed
2020-12-05 04:23:38.461  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-2} closing ...
2020-12-05 04:23:38.461  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-2} closed
2020-12-05 04:23:38.461  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-3} closing ...
2020-12-05 04:23:38.462  INFO 1896956 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-3} closed

Process finished with exit code 0

```