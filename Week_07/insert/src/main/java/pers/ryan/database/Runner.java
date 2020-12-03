package pers.ryan.database;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pers.ryan.database.persistence.master.OrderMain;
import pers.ryan.database.persistence.master.OrderMainMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class Runner implements ApplicationRunner {
    @Resource
    private OrderMainMapper orderMainMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long start = System.currentTimeMillis();
        OrderMain orderMain = new OrderMain(null, 1L, 1L, "shippingName",
                (short) 1, (short) 1, (short) 1, "address", (byte) 1, new BigDecimal(100), new BigDecimal(10), new BigDecimal(10),
                null, null, start, null, null, null, (byte) 1, start);
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            orderMainMapper.insertSelective(orderMain);
        }
        System.out.println("time consumed: " + (System.currentTimeMillis() - start));
    }
}
