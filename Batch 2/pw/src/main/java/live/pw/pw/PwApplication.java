package live.pw.pw;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Order(1)
public class PwApplication implements CommandLineRunner, ApplicationRunner {

	private Student student;

    public PwApplication(Student student) {
        this.student = student;
    }

    public static void main(String[] args) {
		SpringApplication.run(PwApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(student.getStudentName());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(student.getStudentId());
	}
}
