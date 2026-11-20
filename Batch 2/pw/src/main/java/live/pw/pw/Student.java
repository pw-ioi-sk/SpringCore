package live.pw.pw;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private StudentConfiguration studentConfiguration;

    public Student(StudentConfiguration studentConfiguration) {
        this.studentConfiguration = studentConfiguration;
    }

    public int getStudentId() {
        return studentConfiguration.getId();
    }

    public String getStudentName() {
        return studentConfiguration.getName();
    }
}
