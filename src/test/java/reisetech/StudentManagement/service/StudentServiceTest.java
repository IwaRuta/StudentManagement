package reisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reisetech.StudentManagement.StudentRepository.StudentRepository;
import reisetech.StudentManagement.controller.converter.StudentConverter;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細検索_リポジトリから受講生IDに基づいたデータが取得できること() {
    String id = "123";
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);
    student.setId(id);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());
    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生新規登録_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
  }

  @Test
  void 受講生更新_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録_初期化処理が動作すること() {
    String id = "999";
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);
    student.setId(id);

    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    sut.initStudentCourse(studentCourse, student.getId());

    assertEquals("999", studentCourse.getStudentId());
    assertEquals(LocalDateTime.now().getHour(), studentCourse.getStartDate().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(), studentCourse.getEndDate().getYear());
  }
}