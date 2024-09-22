package reisetech.StudentManagement.service;

import java.util.List;
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

  public List<Student> searchStudentList(){
    return repository.searchStudent();
  }

  public List<StudentCourse>searchStudentCourseList(){
    return repository.searchStudentCourse();
  }
}
