package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class AppConfig {
    @Bean
    public User doesnotmatter() {
        return new User("swap", "21");
    }

    @Bean
    public Laptop laptop() {
        return new Laptop();
    }

    @Bean
    public Student student(Laptop laptop) {
        return new Student(laptop);
    }
}
