package org.example;

import org.springframework.stereotype.Component;

@Component
public class Student {

    private Laptop laptop;

    public Student(Laptop laptop) {
        this.laptop = laptop;
    }

    public void learn() {
        laptop.building();
        System.out.println("Learning");
    }
}
