package reisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.domain.StudentDetail;
import reisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  private StudentService service;


  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentsCourses = studentCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourses(convertStudentsCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}
