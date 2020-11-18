package pers.ryan.school.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ryan.school.bean.Klass;
import pers.ryan.school.bean.Student;
import pers.ryan.school.service.School;

import java.util.Arrays;

@Configuration
@ConditionalOnProperty(prefix = "my-school", value = "enabled", havingValue = "true")
public class SchoolAutoConfiguration {

    @Bean
    public Student student100() {
        return new Student(100, "KK100");
    }

    @Bean
    public Student student123() {
        return new Student(123, "KK123");
    }

    @Bean
    @ConditionalOnBean(name = {"student100", "student123"})
    public Klass class1(Student student100, Student student123) {
        Klass klass = new Klass();
        klass.setStudents(Arrays.asList(student100, student123));
        return klass;
    }

    @Bean
    @ConditionalOnBean(name = "class1")
    public School school(Klass class1, Student student100) {
        School school = new School();
        school.setClass1(class1);
        school.setStudent(student100);
        return school;
    }
}
