package com.friday.friday;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FridayApplication implements CommandLineRunner, ApplicationRunner {

	private Student student;

    public FridayApplication(Student student) {
        this.student = student;
    }

    public static void main(String[] args) {
		SpringApplication.run(FridayApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println(student.getId());
		System.out.println(student.getName());
		System.out.println(student.getAddress());
		System.out.println(student.getPhone());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Hello");
	}
}
