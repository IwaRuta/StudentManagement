package reisetech.StudentManagement.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import reisetech.StudentManagement.data.Status;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudentList();
    assertThat(actual).hasSize(5);
  }

  @Test
  void 受講生の単一検索が行えること() {
    String id = "2";
    Student expectedStudent = createStudent();

    expectedStudent.setId(id);
    expectedStudent.setName("石川　塁");
    expectedStudent.setFurigana("イシカワ　ルイ");
    expectedStudent.setNickname("るい");
    expectedStudent.setEmail("rui@gmail.com");
    expectedStudent.setAddress("愛知県豊橋市");
    expectedStudent.setAge(25);
    expectedStudent.setGender("男性");
    expectedStudent.setRemark("");
    expectedStudent.setDeleted(false);

    Student actual = sut.searchStudent(id);

    assertThat(actual).isEqualTo(expectedStudent);
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();

    StudentCourse expectedStudentCourse = actual.get(0);

    assertThat(expectedStudentCourse.getStudentId()).isEqualTo("1");
    assertThat(expectedStudentCourse.getCourseName()).isEqualTo("Javaスタンダード");
    assertThat(expectedStudentCourse.getStartDate()).isEqualTo(
        LocalDateTime.of(2024, 10, 6, 14, 16, 17));
    assertThat(expectedStudentCourse.getEndDate()).isEqualTo(
        LocalDateTime.of(2025, 10, 6, 14, 16, 17));

    assertThat(actual).hasSize(8);
  }

  @Test
  void 受講生コース情報の単一検索が行えること() {
    String courseId = "1";
    List<StudentCourse> actual = sut.searchStudentCourse(courseId);

    StudentCourse expectedStudentCourse = actual.get(0);

    assertThat(expectedStudentCourse.getStudentId()).isEqualTo("1");
    assertThat(expectedStudentCourse.getCourseName()).isEqualTo("Javaスタンダード");
    assertThat(expectedStudentCourse.getStartDate()).isEqualTo(
        LocalDateTime.of(2024, 10, 6, 14, 16, 17));
    assertThat(expectedStudentCourse.getEndDate()).isEqualTo(
        LocalDateTime.of(2025, 10, 6, 14, 16, 17));

    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生の条件指定検索が行えること() {
    Student student = createStudent();

    List<Student> expected = List.of(student);

    List<Student> actual = sut.conditionSearchStudent(
        "1", "岩瀬　杏瑠", "イワセ　アンル", "るた",
        "ruta@gmail.com", "愛知県安城市", 23, "女性"
    );

    assertEquals(expected, actual);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = createStudent();

    sut.registerStudent(student);

    List<Student> actual = sut.searchStudentList();

    assertThat(actual.size()).isEqualTo(6);

    Student expectedStudent = actual.get(actual.size() - 1);
    assertThat(expectedStudent).isEqualTo(student);
    assertThat(expectedStudent.hashCode()).isEqualTo(student.hashCode());
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = createStudentCourse();

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(9);

    StudentCourse expectedStudentCourse = actual.get(actual.size() - 1);

    assertThat(expectedStudentCourse.getStartDate().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(
        studentCourse.getStartDate().truncatedTo(ChronoUnit.SECONDS));
    assertThat(expectedStudentCourse.getEndDate().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(
        studentCourse.getEndDate().truncatedTo(ChronoUnit.SECONDS));

    assertThat(expectedStudentCourse).isEqualTo(studentCourse);
    assertThat(expectedStudentCourse.hashCode()).isEqualTo(studentCourse.hashCode());
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = sut.searchStudent("1");
    student.setName("伊藤　杏瑠");
    student.setEmail("test@gmail.com");

    sut.updateStudent(student);
    Student updateStudent = sut.searchStudent("1");

    assertThat(updateStudent).isEqualTo(student);
    assertThat(updateStudent.hashCode()).isEqualTo(student.hashCode());
  }

  @Test
  void 受講生コース情報のコース名の更新が行えること() {
    List<StudentCourse> studentCourses = sut.searchStudentCourse("1");
    StudentCourse studentCourse = studentCourses.get(0);
    studentCourse.setCourseName("Javaアドバンスド");

    sut.updateStudentCourse(studentCourse);
    List<StudentCourse> updateStudentCourses = sut.searchStudentCourse("1");
    StudentCourse updateStudentCourse = updateStudentCourses.get(0);

    assertThat(updateStudentCourse).isEqualTo(studentCourse);
    assertThat(updateStudentCourse.hashCode()).isEqualTo(studentCourse.hashCode());
  }

  private Student createStudent() {
    Student student = new Student("1", "岩瀬　杏瑠", "イワセ　アンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);
    return student;
  }

  private StudentCourse createStudentCourse() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);
    return studentCourse;
  }
}