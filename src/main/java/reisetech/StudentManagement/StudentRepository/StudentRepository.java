package reisetech.StudentManagement.StudentRepository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生情報と受講生コース情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受講生の条件指定検索と一覧検索を行います。
   *
   * @param id       　受講生ID
   * @param name     　受講生名
   * @param furigana 　ふりがな
   * @param nickname 　ニックネーム
   * @param email    　メールアドレス
   * @param address  　出身地域
   * @param age      　年齢
   * @param gender   　性別
   * @return　条件に紐づく受講生詳細一覧
   */

  List<Student> searchStudentList(
      @Param("id") String id,
      @Param("name") String name,
      @Param("furigana") String furigana,
      @Param("nickname") String nickname,
      @Param("email") String email,
      @Param("address") String address,
      @Param("age") Integer age,
      @Param("gender") String gender);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生の検索を行います。(一件)
   *
   * @param id 　受講生ID
   * @return　受講生(一件)
   */
  Student searchStudent(String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。(一件)
   *
   * @param studentId 　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 新規受講生の基本情報の登録を行います。IDに関しては自動採番を行います。
   *
   * @param student 　受講生情報
   */
  void registerStudent(Student student);

  /**
   * 新規受講生のコース情報の登録を行います。IDに関しては自動採番を行います。
   *
   * @param studentCourse 　受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報の更新を行います。
   *
   * @param student 　受講生情報
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名の更新を行います。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);
}








