package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {

    @Bean
    public User user() {
        return new User(21, "Swap");
    }

    @Bean
    public Laptop laptop() {
        return new Laptop();
    }

    @Bean
    public Student student(Laptop laptop) {
        return new Student(laptop);
    }

    public void hello() {
        System.out.println("Hello from config");
    }
}
