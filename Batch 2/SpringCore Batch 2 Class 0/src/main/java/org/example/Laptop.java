package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope(value = "prototype")
public class Laptop {
    // field injection
    private MotherBoard motherBoard;
    private Ram ram;
    private HardDrive hardDrive;

    // constructor injection
    @Autowired
    public Laptop(MotherBoard motherBoard, Ram ram, HardDrive hardDrive) {
        this.motherBoard = motherBoard;
        this.ram = ram;
        this.hardDrive = hardDrive;
        System.out.println("prototype");
    }

    // setter injection
    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public void setHardDrive(HardDrive hardDrive) {
        this.hardDrive = hardDrive;
    }

    public void boot() {
        motherBoard.detect();
        ram.detect();
        hardDrive.detect();
        System.out.println("Booting");
    }
}
