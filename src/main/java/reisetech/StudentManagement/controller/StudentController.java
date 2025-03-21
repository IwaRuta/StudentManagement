package reisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reisetech.StudentManagement.domain.StudentCourseStatus;
import reisetech.StudentManagement.domain.StudentDetail;
import reisetech.StudentManagement.controller.exceptionHandler.exception.TestException;
import reisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */

@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細一覧検索を行います。 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧(全件)
   */

  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索を行います。 IDに紐づく任意の受講生情報を取得します。 受講生IDは、整数値で入力してください。
   *
   * @param id 　受講生ID
   * @return　受講生詳細(一件)
   */

  @Operation(summary = "受講生詳細検索", description = "受講生IDに紐づく任意の受講生情報を検索します。※受講生IDは、整数値で入力してください。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 申込状況の一覧検索を行います。
   *
   * @return　申込状況（全件）
   */

  @Operation(summary = "申込状況全件検索", description = "申し込み状況の一覧検索を行います。")
  @GetMapping("/studentCourseStatusList")
  public List<StudentCourseStatus> getStudentCourseStatusList() {
    return service.searchStudentCourseStatusList();
  }

  /**
   * 申し込み状況の詳細検索を行います。
   *
   * @param statusId 　申込状況ID
   * @return　申込状況（一件）
   */
  @Operation(summary = "申し込み状況の詳細検索", description = "申し込み状況の詳細検索を行います。")
  @GetMapping("/studentCourseStatus/{statusId}")
  public List<StudentCourseStatus> getStudentCourseStatus(
      @PathVariable @Pattern(regexp = "^\\d+$") String statusId) {
    return service.studentCourseStatus(statusId);
  }

  /**
   * 受講生の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @Operation(summary = "受講生登録", description = "新規受講生を登録します。※受講生IDと受講生コースIDは、自動採番のため入力する必要はありません。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail resoponseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(resoponseStudentDetail);
  }


  /**
   * 申込状況の登録を行います。
   *
   * @param studentCourseStatus 　申込状況詳細
   * @return　実行結果
   */

  @Operation(summary = "申込状況登録", description = "新規申込の登録を行います。")
  @PostMapping("/registerStudentCourseStatus")
  public ResponseEntity<StudentCourseStatus> registerStudentCourseStatus(
      @RequestBody @Valid StudentCourseStatus studentCourseStatus) {
    StudentCourseStatus resoponseStudentCourseStatus = service.registerStudentCourseStatus(
        studentCourseStatus);
    return ResponseEntity.ok(resoponseStudentCourseStatus);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新も行います。(論理削除)
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @Operation(summary = "受講生詳細更新", description = "受講生詳細の更新を行います。※キャンセルフラグ(論理削除)の更新も行います。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 申込状況の更新を行います。
   *
   * @param studentCourseStatus 　申込状況詳細
   * @return　実行結果
   */

  @Operation(summary = "申込状況詳細更新", description = "申込状況の更新を行います。")
  @PutMapping("/updateStudentCourseStatus")
  public ResponseEntity<String> updateStudentCourseStatus(
      @RequestBody @Valid StudentCourseStatus studentCourseStatus) {
    service.updateStudentCourseStatus(studentCourseStatus);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 受講生詳細一覧検索の例外を実装します。
   *
   * @throws TestException 　例外処理
   * @return　エラーメッセージ
   */

  @Operation(summary = "一覧検索の例外実装", description = "受講生一覧検索の例外が実装され、エラーメッセージが表示されます。")
  @GetMapping("/exceptionStudentList")
  public List<StudentDetail> getExceptionStudentList() throws TestException {
    throw new TestException(
        "現在、このAPIは利用できません。URLは、「http://localhost:8080/exceptionStudentList」ではなく、「http://localhost:8080/studentList」を利用してください。");
  }
}
