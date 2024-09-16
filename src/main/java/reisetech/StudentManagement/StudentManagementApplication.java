package reisetech.StudentManagement;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private StudentRepository repository;

  private String name = "Iwase Anru";
  private String age = "24";

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/student")
  public String getStudent() {
    Student student = repository.searchByName("NakayamaKiho");
		return student.getName() + " " + student.getAge() + "歳";
	}

  @PostMapping("/student")
  public void registerStudent(String name,int age){
    repository.registerStudent(name,age);
  }

  @PatchMapping("/student")
  public void updateStudent(String name,int age){
    repository.updateStudent(name,age);
  }

  @DeleteMapping("/student")
  public  void deleteStudent(String name){
    repository.deleteStudent(name);
  }
}

