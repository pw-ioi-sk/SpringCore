package live.pw.pw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private Student student;

    public HelloController(Student student) {
        this.student = student;
    }

    @GetMapping("/hello")
    public Student hello() {
        return student;
    }
}
