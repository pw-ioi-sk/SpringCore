package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class SKHynisk implements Ram{

    @Override
    public void detect() {
        System.out.println("Sk Ram Detected");
    }
}
