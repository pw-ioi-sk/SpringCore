package org.example;

import org.springframework.stereotype.Component;

@Component
public class WDHardDrive implements HardDrive {
    public void detect() {
        System.out.println("WD Hard Drive detected");
    }
}
