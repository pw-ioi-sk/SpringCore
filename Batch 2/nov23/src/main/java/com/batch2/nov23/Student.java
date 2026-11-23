package com.batch2.nov23;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
    private String name;
    private String school;
    private String code;

    public Student(@Value("${student.name:Default Name}") String name,
                   @Value("${student.school:Default School}") String school,
                   @Value("${student.code:dkjvdgfvdgfv}") String code) {
        this.name = name;
        this.school = school;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
