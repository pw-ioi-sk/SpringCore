package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Student student = context.getBean(Student.class);
        student.learn();

        Config config = context.getBean(Config.class);
        config.hello();

        User user = context.getBean(User.class);
        System.out.println(user.getName());
        System.out.println(user.getId());
    }
}
