package reisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reisetech.StudentManagement.domain.StudentDetail;
import reisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */

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

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索を行います。 IDに紐づく任意の受講生情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生詳細(一件)
   */

  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail resoponseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(resoponseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新も行います。(論理削除)
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */

  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
