package reisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reisetech.StudentManagement.controller.converter.StudentConverter;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.domain.StudentDetail;
import reisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}
