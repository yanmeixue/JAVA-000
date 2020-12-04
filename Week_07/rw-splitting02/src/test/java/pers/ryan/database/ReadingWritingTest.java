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
