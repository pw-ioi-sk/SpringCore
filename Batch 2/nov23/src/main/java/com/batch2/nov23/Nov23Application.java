package com.batch2.nov23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Nov23Application {
	public static void main(String[] args) {
		ApplicationContext context =
				SpringApplication.run(Nov23Application.class, args);

		Student student = context.getBean(Student.class);
		System.out.println(student.getName());
		System.out.println(student.getSchool());
		System.out.println(student.getCode());
		System.out.println("----------------------------");
		Email email = context.getBean(Email.class);
		System.out.println(email.getId());
		System.out.println(email.getName());
	}
}
