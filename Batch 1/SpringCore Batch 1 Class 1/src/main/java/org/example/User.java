package org.example;


public class User {
    private String name;
    private String roll;

    public User(String name, String roll) {
        this.name = name;
        this.roll = roll;
        System.out.println("From User");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
