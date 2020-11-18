package pers.ryan.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.ryan.spring.bean.Dog;
import pers.ryan.spring.controller.MyController;

public class AssembleTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取通过xml方式配置的bean
        Dog dog1 = (Dog) context.getBean("dog1");
        // 获取用Java Config方式配置的bean
        Dog dog2 = (Dog) context.getBean("dog2");
        // 获取自动注解配置的controller
        MyController myController = context.getBean(MyController.class);

        System.out.println(dog1);
        System.out.println(dog2);
        myController.print();
        // 输出：
        // Dog(name=Amanda, age=5)
        // Dog(name=Adela, age=2)
        // Annotation injection is awesome!
    }
}
