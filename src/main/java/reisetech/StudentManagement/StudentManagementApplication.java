package reisetech.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reisetech.StudentManagement.StudentRepository.StudentRepository;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@SpringBootApplication

public class StudentManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
}

