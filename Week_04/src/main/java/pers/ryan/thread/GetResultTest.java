package pers.ryan.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class GetResultTest {

    private static final long FIBONACCI_41 = 267914296;
    private static long start = 0;

    private String methodName;
    private int result;

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    @Before
    public void init() {
        result = 0;
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        log.info("method: {}, time consumed: {}", methodName, System.currentTimeMillis() - start);
        log.info("result: {}", result);
        Assert.assertEquals(FIBONACCI_41, result);
    }

    @Test
    public void useWhile() throws InterruptedException {
        new Thread(() -> {
            result = FibUtil.fib41();
        }, methodName = getMethodName()).start();

        while (result == 0) {
            TimeUnit.MILLISECONDS.sleep(1);
        }
    }

    @Test
    public void useJoin() throws InterruptedException {
        Thread thread = new Thread(() -> result = FibUtil.fib41(), methodName = getMethodName());
        thread.start();
        thread.join();
    }

    /**
     * 确保子线程先拿到锁
     */
    @Test
    public void useSynchronized() throws InterruptedException {
        new Thread(() -> {
            synchronized (this) {
                result = FibUtil.fib41();
            }
        }, methodName = getMethodName()).start();

        TimeUnit.MILLISECONDS.sleep(500);
        synchronized (this) {
        }
    }

    /**
     * 确保主线程先拿到锁
     */
    @Test
    public void useWaitNotify() throws InterruptedException {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                result = FibUtil.fib41();
                notifyAll();
            }
        }, methodName = getMethodName()).start();

        synchronized (this) {
            wait();
        }
    }

    /**
     * 确保子线程先拿到锁
     */
    @Test
    public void useLock() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        new Thread(() -> {
            try {
                lock.lock();
                result = FibUtil.fib41();
            } finally {
                lock.unlock();
            }
        }, methodName = getMethodName()).start();

        TimeUnit.MILLISECONDS.sleep(500);
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 确保主线程先拿到锁
     */
    @Test
    public void useLockAndCondition() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                lock.lock();
                result = FibUtil.fib41();
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, methodName = getMethodName()).start();

        try {
            lock.lock();
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 类似可以采用CyclicBarrier
     */
    @Test
    public void useCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            result = FibUtil.fib41();
            countDownLatch.countDown();
        }, methodName = getMethodName()).start();
        countDownLatch.await();
    }

    @Test
    public void useFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(FibUtil::fib41);
        new Thread(task, methodName = getMethodName()).start();
        result = task.get();
    }

    @Test
    public void useTreadPoolAndFuture() throws ExecutionException, InterruptedException {
        methodName = getMethodName();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(FibUtil::fib41);
        executorService.shutdown();
        result = future.get();
    }

    @Test
    public void useCompletableFuture() throws ExecutionException, InterruptedException {
        methodName = getMethodName();
        result = CompletableFuture.supplyAsync(FibUtil::fib41).get();
    }

    /**
     * 生产-消费模型
     */
    @Test
    public void useBlockingQueue() throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        new Thread(() -> {
            int result = FibUtil.fib41();
            try {
                blockingQueue.put(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getMethodName()).start();
        result = blockingQueue.take();
    }
}
