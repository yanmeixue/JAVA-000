package pers.ryan.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.ryan.database.persistence.mall.OrderMain;
import pers.ryan.database.service.OrderService;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingWritingTest {
    @Resource
    private OrderService orderService;

    @Test
    public void testSelect() {
        for (int i = 1; i < 6; i++) {
            log.info("第{}次查询", i);
            log.info(orderService.selectById(i).toString());
        }
    }

    @Test
    public void testSelectFromMaster() {
        log.info(orderService.selectFromMaster(1L).toString());
    }

    private OrderMain getOrder() {
        long now = System.currentTimeMillis();
        return new OrderMain(null, 1L, 1L, "n",
                (short) 1, (short) 1, (short) 1, "n", (byte) 1,
                new BigDecimal(100), new BigDecimal(10), new BigDecimal(10),
                null, null, now, null, null, null, (byte) 1, now);
    }

    @Test
    public void testInsert() {
        orderService.insert(getOrder());
    }
}
