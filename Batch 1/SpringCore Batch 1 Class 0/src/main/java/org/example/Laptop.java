package org.example;

import org.example.laptop.*;
import org.springframework.stereotype.Component;

@Component
public class Laptop {
    private MotherBoard motherBoard;
    private Ram ram;
    private HardDrive hardDrive;

    public Laptop(MotherBoard motherBoard,
                  Ram ram,
                  HardDrive hardDrive) {
        this.motherBoard = motherBoard;
        this.ram = ram;
        this.hardDrive = hardDrive;
        System.out.println("Laptop args");
    }

    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
        System.out.println("Setter");
    }

    public void setHardDrive(HardDrive hardDrive) {
        this.hardDrive = hardDrive;
    }

    public void boot() {
        motherBoard.mb();
        ram.ram();
        hardDrive.hardDrive();
        System.out.println("Laptop Booted");
    }
}
