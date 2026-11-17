package org.example;


import org.springframework.stereotype.Component;

@Component
public class HPMotherBoard implements MotherBoard {
    public void detect() {
        System.out.println("HP motherboard detected");
    }
}
