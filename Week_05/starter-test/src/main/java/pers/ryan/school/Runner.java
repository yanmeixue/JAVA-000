package pers.ryan.school;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pers.ryan.school.service.ISchool;

import javax.annotation.Resource;

@Component
public class Runner implements ApplicationRunner {
    @Resource
    private ISchool school;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        school.ding();
    }
}
