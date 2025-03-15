package reisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    List<Student> studentList = List.of(
        new Student("1", "岩瀬　杏瑠", "イワセ　アンル", "るた", "ruta@gmail.com", "愛知県安城市", 23,
            "女性", "", false));
    List<StudentCourse> studentCourseList = List.of(
        new StudentCourse("1", "1", "Javaスタンダード", LocalDateTime.now(),
            LocalDateTime.now().plusYears(1)));
    List<StudentDetail> expected = List.of(
        new StudentDetail(studentList.get(0), studentCourseList));

    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(converter.convertStudentDetails(studentList, studentCourseList)).thenReturn(expected);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).searchStudentList();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    assertEquals(expected, actual);
  }

  @Test
  void 受講生詳細検索_リポジトリから受講生IDに基づいたデータが取得できること() {
    String id = "999";

    Student student = new Student(id, "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail expected = new StudentDetail(student, studentCourseList);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(studentCourseList);

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);

    assertEquals(expected, actual);
  }

  @Test
  void 受講生詳細検索_存在しないIDのデータ取得(){
    String id="9999999";

    when(repository.searchStudent(id)).thenReturn(null);

    StudentDetail actual=sut.searchStudent(id);

    verify(repository,times(1)).searchStudent(id);
    verify(repository,times(0)).searchStudentCourse(id);

    assertNull(actual,"存在しないIDのとき、searchStudentはNullを返すべき");
  }

  @Test
  void 受講生新規登録_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    doNothing().when(repository).registerStudent(student);
    doNothing().when(repository).registerStudentCourse(studentCourse);

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

    doNothing().when(repository).updateStudent(student);
    doNothing().when(repository).updateStudentCourse(studentCourse);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録_初期化処理が動作すること() {
    String id = "999";
    Student student = new Student(id, "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));

    sut.initStudentCourse(studentCourse, id);

    assertEquals(id, studentCourse.getStudentId());

    LocalDateTime now = LocalDateTime.now();
    assertTrue(ChronoUnit.MINUTES.between(now, studentCourse.getStartDate()) < 1);
    assertEquals(now.plusYears(1).getYear(), studentCourse.getEndDate().getYear());
  }
}