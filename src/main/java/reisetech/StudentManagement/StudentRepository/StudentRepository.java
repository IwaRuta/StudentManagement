package reisetech.StudentManagement.StudentRepository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生情報と受講生コース情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧(全件)
   */
  @Select("SELECT * FROM students WHERE isDeleted = false")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 　受講生ID
   * @return　受講生(一件)
   */

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報(全件)
   */

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */

  @Select("SELECT * FROM students_courses WHERE student_Id =#{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 新規受講生の基本情報の登録を行います。IDに関しては自動採番を行います。
   *
   * @param student 　受講生情報
   */

  @Insert("INSERT INTO students(name,furigana,nickname,email,address,age,gender,remark,isDeleted) "
      + "VALUES(#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{remark},false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 新規受講生のコース情報の登録を行います。IDに関しては自動採番を行います。
   *
   * @param studentCourse 　受講生コース情報
   */

  @Insert("INSERT INTO students_courses(student_id,course_name,start_date,end_date)"
      + "VALUES(#{studentId},#{courseName},#{startDate},#{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報の更新を行います。
   *
   * @param student 　受講生情報
   */

  @Update("UPDATE students SET name=#{name},furigana=#{furigana},nickname=#{nickname},"
      + "email=#{email},address=#{address},age=#{age},gender=#{gender},remark=#{remark},isDeleted=#{isDeleted} WHERE id =#{id}")
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新を行います。
   *
   * @param studentCourse 受講生コース情報
   */

  @Update("UPDATE students_courses SET course_name=#{courseName} WHERE course_Id =#{courseId}")
  void updateStudentCourse(StudentCourse studentCourse);
}








