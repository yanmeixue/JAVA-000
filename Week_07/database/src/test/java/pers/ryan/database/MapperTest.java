package pers.ryan.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.ryan.database.persistence.master.OrderMain;
import pers.ryan.database.persistence.master.OrderMainMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    private static final int TOTAL_MOUNT = 1000000;
    private static final int EACH_MOUNT = 10000;
    private final Queue<OrderMain> orderQueue = new ConcurrentLinkedQueue<>();
    private long start = System.currentTimeMillis();
    private final OrderMain targetOrder = getOrder();
    private volatile boolean end = false;
    @Resource
    private OrderMainMapper orderMainMapper;

    private OrderMain getOrder() {
        long now = System.currentTimeMillis();
        return new OrderMain(null, 1L, 1L, "n",
                (short) 1, (short) 1, (short) 1, "n", (byte) 1,
                new BigDecimal(100), new BigDecimal(10), new BigDecimal(10),
                null, null, start, null, null, null, (byte) 1, now);
    }

    @Before
    public void init() {
        orderMainMapper.selectByPrimaryKey(1L);
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        log.info("time consumed: {}", System.currentTimeMillis() - start);
    }

    @Test
    public void testInsert() {
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            log.info("插入数据{}", i);
            orderMainMapper.insertSelective(targetOrder);
        }
    }

    @Test
    public void testInsertBatch() {
        List<OrderMain> orderMainList = new ArrayList<>(EACH_MOUNT);
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            orderMainList.add(targetOrder);
            if (orderMainList.size() >= EACH_MOUNT) {
                log.info("插入数据{}", i);
                orderMainMapper.insertBatch(orderMainList);
                orderMainList = new ArrayList<>(EACH_MOUNT);
            }
        }
    }

    @Test
    public void testInsertBatchThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.submit(new handleData());
        }
        executorService.shutdown();
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            orderQueue.add(targetOrder);
        }
        end = true;
        start = System.currentTimeMillis();
        while (!orderQueue.isEmpty()) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private class handleData implements Runnable {
        @Override
        public void run() {
            while (true) {
                List<OrderMain> temp = new ArrayList<>();
                for (int i = 0; i < 5000; i++) {
                    OrderMain orderMain = orderQueue.poll();
                    if (orderMain != null) {
                        temp.add(orderMain);
                    } else {
                        break;
                    }
                }
                if (!temp.isEmpty()) {
                    log.info("插入数据: {}", temp.size());
                    orderMainMapper.insertBatch(temp);
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (end && orderQueue.isEmpty()) {
                    break;
                }
            }
        }
    }
}
