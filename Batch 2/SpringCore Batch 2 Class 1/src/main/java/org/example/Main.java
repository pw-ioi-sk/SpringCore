package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Student student = context.getBean(Student.class);
        student.learn();

        User user = context.getBean(User.class);
        System.out.println(user.getName());
        System.out.println(user.getRoll());

    }
}
