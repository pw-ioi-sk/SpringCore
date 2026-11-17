package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class Student {

    private Laptop laptop;

    public Student(Laptop laptop) {
        this.laptop = laptop;
        System.out.println("From Student");
    }

    public void learn() {
        laptop.building();
        System.out.println("Learing");
    }
}
