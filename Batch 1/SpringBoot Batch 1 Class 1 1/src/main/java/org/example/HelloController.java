package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private User user;

    public HelloController(User user) {
        this.user = user;
    }

    @GetMapping("/user")
    public User hello() {
        user.setName("swap");
        user.setId(21);
        return user;
    }
}
