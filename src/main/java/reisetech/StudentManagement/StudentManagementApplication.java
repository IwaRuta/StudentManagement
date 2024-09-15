package reisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private String name = "Iwase Anru";
  private String age = "23";

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
		return name + " " + age + "歳";
	}

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name,String age){
    this.name=name;
    this.age=age;
  }
}

