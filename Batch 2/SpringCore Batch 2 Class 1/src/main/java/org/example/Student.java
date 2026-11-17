package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class Student {

    private Laptop laptop;

    @Autowired
    public Student(Laptop laptop) {
        this.laptop = laptop;
    }

    public void learn() {
        laptop.building();
        System.out.println("Learning");
    }
}
