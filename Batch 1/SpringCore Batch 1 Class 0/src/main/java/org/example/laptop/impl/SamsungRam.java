package org.example.laptop.impl;

import org.example.laptop.Ram;
import org.springframework.stereotype.Component;

@Component
public class SamsungRam implements Ram {
    public void ram() {
        System.out.println("Samsung Ram Detected");
    }
}
