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
import reisetech.StudentManagement.data.Status;
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
  void 受講生の条件指定検索が実行できて空で返ってくること() throws Exception {
    String id = "999";
    String name = "岩瀬　杏瑠";
    String furigana = "イワセ　アンル";
    String nickname = "るた";
    String email = "ruta@gmail.com";
    String address = "愛知県安城市";
    Integer age = 23;
    String gender = "女性";

    mockMvc.perform(get("/studentList")
            .param("id", id)
            .param("name", name)
            .param("furigana", furigana)
            .param("nickname", nickname)
            .param("email", email)
            .param("address", address)
            .param("age", String.valueOf(age))
            .param("gender", gender)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList(id, name, furigana, nickname, email, address,
        age, gender);
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
                              "courseName":"Javaスタンダード",
                              "status":"仮申込"
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
                          "endDate":"2026-01-18T11:35:50.833614",
                          "status":"仮申込"
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
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックが掛かること() {
    Student student = new Student("テスト", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生で名前に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で名前に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("氏名を入力してください。");
  }

  @Test
  void 受講生詳細の受講生でフリガナに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でフリガナに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("フリガナを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でニックネームに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でニックネームに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("ニックネームを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でEmailに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でEmailに値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("Emailを入力してください。");
  }

  @Test
  void 受講生詳細の受講生でEmail形式で入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でEmail形式で入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("Email形式で入力してください。");
  }

  @Test
  void 受講生詳細の受講生で住所に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で住所に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("住所を入力してください。");
  }

  @Test
  void 受講生詳細の受講生で性別に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "女性", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で性別に値が入力されていないときに入力チェックに掛かること() {
    Student student = new Student("1", "岩瀬杏瑠", "イワセアンル", "るた", "ruta@gmail.com",
        "愛知県安城市", 23, "", "", false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("性別を入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報で受講生IDに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報で受講生IDに数字以外を用いた時に入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse("1", "テストです", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報でコースIDに適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報でコースIDに数字以外を用いたときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse("テストです", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみで入力してください。");
  }

  @Test
  void 受講生詳細の受講生コース情報でコース名に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報でコース名に値が入力されていないときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "", LocalDateTime.now(),
        LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("コース名を入力してください。");
  }

  @Test
  void 受講詳細の受講生コース情報で申込状況に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(), LocalDateTime.now().plusYears(1), Status.仮申込);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報で申込状況に値が入力されていないときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse("1", "1", "Javaスタンダード",
        LocalDateTime.now(),
        LocalDateTime.now().plusYears(1), null);

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("仮申込、本申込、受講中、受講終了のいずれかを入力してください。");
  }
}