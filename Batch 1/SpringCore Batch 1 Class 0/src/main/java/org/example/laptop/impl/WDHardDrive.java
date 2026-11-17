package org.example.laptop.impl;

import org.example.laptop.HardDrive;
import org.springframework.stereotype.Component;

@Component
public class WDHardDrive implements HardDrive {
    public void hardDrive() {
        System.out.println("WD HardDrive Detected");
    }
}
