package pers.ryan.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FibUtil {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("res: {}", fib41());
        log.info("time: {}", System.currentTimeMillis() - start);
    }


    public static int fib41() {
        return fib(41);
    }

    public static int fib(int a) {
        if (a < 2)
            return 1;
        return fib(a - 1) + fib(a - 2);
    }
}
