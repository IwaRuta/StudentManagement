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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
   * 受講生の条件指定検索と一覧検索を行います。受講生IDと年齢は、整数値で入力してください。
   *
   * @param id       受講生ID
   * @param name     　名前
   * @param furigana 　ふりがな
   * @param nickname 　ニックネーム
   * @param email    　メールアドレス
   * @param address  　住所
   * @param age      　年齢
   * @param gender   　性別
   * @return　条件に一致した受講生詳細
   */

  @Operation(tags = "検索", summary = "条件指定検索", description = "条件に一致する受講生を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(
      @RequestParam(required = false) @Pattern(regexp = "^\\d+$") String id,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String furigana,
      @RequestParam(required = false) String nickname,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String address,
      @RequestParam(required = false) Integer age,
      @RequestParam(required = false) String gender
  ) {
    return service.searchStudentList(id, name, furigana, nickname, email, address, age,
        gender);
  }

  /**
   * 受講生詳細検索を行います。 IDに紐づく任意の受講生情報を取得します。 受講生IDは、整数値で入力してください。
   *
   * @param id 　受講生ID
   * @return　受講生詳細(一件)
   */

  @Operation(tags = "検索", summary = "受講生詳細検索", description = "受講生IDに紐づく任意の受講生情報を検索します。※受講生IDは、整数値で入力してください。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生の登録を行います。申込状況の登録も行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @Operation(tags = "登録", summary = "受講生登録", description = "新規受講生を登録します。※受講生IDと受講生コースIDは、自動採番のため入力する必要はありません。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail resoponseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(resoponseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新も行います。(論理削除)
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @Operation(tags = "更新", summary = "受講生詳細更新", description = "受講生詳細の更新を行います。※キャンセルフラグ(論理削除)の更新も行います。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 受講生詳細一覧検索の例外を実装します。
   *
   * @throws TestException 　例外処理
   * @return　エラーメッセージ
   */

  @Operation(tags = "例外検索", summary = "一覧検索の例外実装", description = "受講生一覧検索の例外が実装され、エラーメッセージが表示されます。")
  @GetMapping("/exceptionStudentList")
  public List<StudentDetail> getExceptionStudentList() throws TestException {
    throw new TestException(
        "現在、このAPIは利用できません。URLは、「http://localhost:8080/exceptionStudentList」ではなく、「http://localhost:8080/studentList」を利用してください。");
  }
}
