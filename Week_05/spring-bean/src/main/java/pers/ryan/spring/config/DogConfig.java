package pers.ryan.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ryan.spring.bean.Dog;

@Configuration
public class DogConfig {
    @Bean
    public Dog dog2() {
        return new Dog("Adela", 2);
    }
}
