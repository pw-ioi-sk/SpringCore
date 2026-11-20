package org.example;

public class Student {

    private Laptop laptop;

    public Student(Laptop laptop) {
        this.laptop = laptop;
        System.out.println("Contructor Injection");
    }

    public void learn() {
        laptop.building();
        System.out.println("Learn");
    }
}
