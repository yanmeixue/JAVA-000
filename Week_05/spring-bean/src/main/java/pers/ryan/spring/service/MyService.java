package pers.ryan.spring.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void print() {
        System.out.println("Annotation injection is awesome!");
    }
}
