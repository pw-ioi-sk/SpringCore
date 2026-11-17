package org.example.laptop.impl;

import org.example.laptop.MotherBoard;
import org.springframework.stereotype.Service;

@Service
public class DellMotherBoard implements MotherBoard {
    public void mb() {
        System.out.println("Dell MotherBoard Detected");
    }
}
