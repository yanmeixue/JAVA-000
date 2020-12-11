package pers.ryan.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.ryan.database.persistence.OrderMain;
import pers.ryan.database.persistence.OrderMainExample;
import pers.ryan.database.persistence.OrderMainMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DMLTest {
    @Resource
    private OrderMainMapper orderMainMapper;

    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {
            orderMainMapper.insertSelective(getOrder((long) i));
        }
    }

    @Test
    public void testSelect() {
        OrderMainExample example = new OrderMainExample();
        example.createCriteria().andUserIdEqualTo(11L).andIdEqualTo(544354764750585857L);
        for (OrderMain orderMain : orderMainMapper.selectByExample(example)) {
            System.out.println("查询结果: " + orderMain.toString());
        }
    }

    @Test
    public void testUpdate() {
        OrderMain orderMain = new OrderMain();
        orderMain.setAddress("updated address");
        OrderMainExample example = new OrderMainExample();
        example.createCriteria().andUserIdEqualTo(11L).andIdEqualTo(544354764750585857L);
        orderMainMapper.updateByExampleSelective(orderMain, example);
        testSelect();
    }

    @Test
    public void testDelete() {
        OrderMainExample example = new OrderMainExample();
        example.createCriteria().andUserIdEqualTo(32L).andIdEqualTo(544354783629148160L);
        orderMainMapper.deleteByExample(example);
    }


    private OrderMain getOrder(Long userId) {
        long now = System.currentTimeMillis();
        return new OrderMain(null, 1L, userId, "n",
                (short) 1, (short) 1, (short) 1, "n", (byte) 1,
                new BigDecimal(100), new BigDecimal(10), new BigDecimal(10),
                null, null, now, null, null, null, (byte) 1, now);
    }


}
