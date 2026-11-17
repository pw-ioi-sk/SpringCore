package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SamsungRam implements Ram {
    public void detect() {
        System.out.println("Samsung Ram detected");
    }
}
