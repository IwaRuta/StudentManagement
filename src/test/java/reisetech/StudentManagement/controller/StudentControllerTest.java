package reisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の単一の検索が実行できて空で返ってくること()
      throws Exception {
    String id = "999";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の新規登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON).content(
                """
                        {
                          "student":{
                            "name":"岩瀬 杏瑠",
                            "furigana":"イワセ アンル",
                            "nickname":"るた",
                            "email":"ruta@gmail.com",
                            "address":"愛知県安城市",
                            "age":"23",
                           "gender":"女性",
                            "remark":""
                         },
                          "studentCourseList":[
                            {
                              "courseName":"Javaスタンダード"
                            }
                          ]
                       }
                    """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新ができて空が返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                    {
                      "student":{
                        "id":"1",
                        "name":"岩瀬　杏瑠",
                        "furigana":"イワセ　アンル",
                        "nickname":"るた",
                        "email":"ruta@gmail.com",
                        "address":"愛知県名古屋市",
                        "age":"23",
                        "gender":"女性",
                        "remark":""　
                      },
                      "studentCourseList":[
                        {
                          "courseId":"2",
                          "studentId":"1",
                          "courseName":"Javaスタンダード",
                          "startDate":"2025-01-18T11:35:50.833614",
                          "endDate":"2026-01-18T11:35:50.833614"
                        }
                      ]
                    }
                """
        ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスに400で返ってくること() throws Exception {
    mockMvc.perform(get("/exceptionStudentList"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(
            "現在、このAPIは利用できません。URLは、「http://localhost:8080/exceptionStudentList」ではなく、「http://localhost:8080/studentList」を利用してください。"));
  }

  @Test
  void 受講生詳細の受講生でIDに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックが掛かること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生で名前に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で名前に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("氏名を入力してください。");
  }

  @Test
  void 受講生詳細の受講生でフリガナに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でフリガナに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("フリガナを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でニックネームに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でニックネームに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("ニックネームを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でEmailに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でEmailに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("Emailを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でEmail形式で入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でEmail形式で入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("Email形式で入力してください。");
  }

  @Test
  void 受講生詳細の受講生で住所に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で住所に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("住所を入力してください。");
  }

  @Test
  void 受講生詳細の受講生で性別に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で性別に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("岩瀬杏瑠");
    student.setFurigana("イワセアンル");
    student.setNickname("るた");
    student.setEmail("ruta@gmail.com");
    student.setAddress("愛知県安城市");
    student.setGender("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("性別を入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報で受講生IDに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId("1");
    studentCourse.setCourseName("Javaスタンダード");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報で受講生IDに数字以外を用いた時に入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("テストです");
    studentCourse.setCourseId("1");
    studentCourse.setCourseName("Javaスタンダード");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報でコースIDに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId("1");
    studentCourse.setCourseName("Javaスタンダード");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報でコースIDに数字以外を用いたときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId("テストです");
    studentCourse.setCourseName("Javaスタンダード");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報でコース名に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId("1");
    studentCourse.setCourseName("Javaスタンダード");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報でコース名に値が入力されていないときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId("1");
    studentCourse.setCourseName("");
    studentCourse.setStartDate(LocalDateTime.parse("2025-01-18T11:35:50.833614"));
    studentCourse.setEndDate(LocalDateTime.parse("2026-01-18T11:35:50.833614"));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("コース名を入力してください。");
  }
}