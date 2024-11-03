package reisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reisetech.StudentManagement.StudentRepository.StudentRepository;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.domain.StudentDetail;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourse> studentCourseList() {
    return repository.searchStudentCourseList();
  }

  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentsCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);
    return studentDetail;
  }

  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourseList();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setStartDate(LocalDateTime.now());
      studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentCourse);
    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentsCourses(studentCourse);
    }
  }
}


