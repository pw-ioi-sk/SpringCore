package org.example;

import org.example.laptop.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main(String[] args) {
//        Ram ram = new SamsungRam();
//        MotherBoard motherBoard = new DellMotherBoard();
//        HardDrive hardDrive = new WDHardDrive();
//
//        Laptop laptop = new Laptop(motherBoard, ram, hardDrive);
//        laptop.boot();

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Laptop laptop = applicationContext.getBean(Laptop.class);
        laptop.boot();
    }
}