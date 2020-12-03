package pers.ryan.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.ryan.database.persistence.master.OrderMain;
import pers.ryan.database.persistence.master.OrderMainMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    private static final int TOTAL = 1000000;
    @Resource
    private OrderMainMapper orderMainMapper;

    @Test
    public void testInsert() throws Exception {
        long start = System.currentTimeMillis();
        OrderMain orderMain = new OrderMain(null, 1L, 1L, "shippingName",
                (short) 1, (short) 1, (short) 1, "address", (byte) 1, new BigDecimal(100), new BigDecimal(10), new BigDecimal(10),
                null, null, start, null, null, null, (byte) 1, start);
        for (int i = 0; i < TOTAL; i++) {
            System.out.println(i);
            orderMainMapper.insert(orderMain);
        }
        System.out.println("time consumed: " + (System.currentTimeMillis() - start));
    }
}
