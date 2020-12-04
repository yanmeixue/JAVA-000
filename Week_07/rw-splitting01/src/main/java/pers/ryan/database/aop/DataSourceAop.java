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
