package reisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reisetech.StudentManagement.StudentRepository.StudentRepository;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();


     // 30代のみ抽出実装
//    List<Student> students = repository.searchStudent();
//    List<Student> thirtiesStudents = students.stream()
//        .filter(student -> student.getAge() >= 30 && student.getAge() < 40)
//        .collect(Collectors.toList());
//
//    return thirtiesStudents;
  }




  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourse();

    // "Javaスタンダード"のみ抽出実装。
//    List<StudentCourse> studentCourses = repository.searchStudentCourse();
//    List<StudentCourse> javaStandardStudents = new ArrayList<>();
//
//    for (StudentCourse studentCourse : studentCourses) {
//      if ("Javaスタンダード".equals(studentCourse.getCourseName())) {
//        javaStandardStudents.add(studentCourse);
//      }
//    }
//    return javaStandardStudents;
  }
}


