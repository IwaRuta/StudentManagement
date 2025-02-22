package reisetech.StudentManagement.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();

    assertThat(actual.getFirst().getName()).isEqualTo("岩瀬　杏瑠");
    assertThat(actual.getFirst().getFurigana()).isEqualTo("イワセ　アンル");
    assertThat(actual.getFirst().getNickname()).isEqualTo("るた");
    assertThat(actual.getFirst().getEmail()).isEqualTo("ruta@gmail.com");
    assertThat(actual.getFirst().getAddress()).isEqualTo("愛知県安城市");
    assertThat(actual.getFirst().getAge()).isEqualTo(23);
    assertThat(actual.getFirst().getGender()).isEqualTo("女性");
    assertThat(actual.getFirst().getRemark()).isEqualTo("");
    assertThat(actual.getFirst().isDeleted()).isEqualTo(false);

    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の単一検索が行えること() {
    String id = "2";
    Student actual = sut.searchStudent(id);

    assertThat(actual.getId()).isEqualTo(id);
    assertThat(actual.getName()).isEqualTo("石川　塁");
    assertThat(actual.getFurigana()).isEqualTo("イシカワ　ルイ");
    assertThat(actual.getNickname()).isEqualTo("るい");
    assertThat(actual.getEmail()).isEqualTo("rui@gmail.com");
    assertThat(actual.getAddress()).isEqualTo("愛知県豊橋市");
    assertThat(actual.getAge()).isEqualTo(25);
    assertThat(actual.getGender()).isEqualTo("男性");
    assertThat(actual.getRemark()).isEqualTo("");
    assertThat(actual.isDeleted()).isEqualTo(false);
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.getFirst().getStudentId()).isEqualTo("1");
    assertThat(actual.getFirst().getCourseName()).isEqualTo("Javaスタンダード");
    assertThat(actual.getFirst().getStartDate()).isEqualTo(
        LocalDateTime.of(2024, 10, 6, 14, 16, 17));
    assertThat(actual.getFirst().getEndDate()).isEqualTo(LocalDateTime.of(2025, 10, 6, 14, 16, 17));

    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void 受講生コース情報の単一検索が行えること() {
    String courseId = "1";
    List<StudentCourse> actual = sut.searchStudentCourse(courseId);

    assertThat(actual.getFirst().getStudentId()).isEqualTo("1");
    assertThat(actual.getFirst().getCourseName()).isEqualTo("Javaスタンダード");
    assertThat(actual.getFirst().getStartDate()).isEqualTo(
        LocalDateTime.of(2024, 10, 6, 14, 16, 17));
    assertThat(actual.getFirst().getEndDate()).isEqualTo(LocalDateTime.of(2025, 10, 6, 14, 16, 17));

    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = createStudent();

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = createStudentCourse();

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(9);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = createStudent();

    sut.updateStudent(student);

    Student updateStudent = sut.searchStudent("1");
    assertThat(updateStudent.getName()).isEqualTo("岩瀬杏瑠");
    assertThat(updateStudent.getAddress()).isEqualTo("愛知県安城市");
  }

  @Test
  void 受講生コース情報のコース名の更新が行えること() {
    StudentCourse studentCourse = createStudentCourse();

    sut.updateStudentCourse(studentCourse);

    StudentCourse updateStudentCourse = sut.searchStudentCourse("1").get(0);
    assertThat(updateStudentCourse.getCourseName()).isEqualTo("Javaスタンダード");
  }

  private Student createStudent() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);
    return student;
  }

  private StudentCourse createStudentCourse() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1));
    return studentCourse;
  }
}