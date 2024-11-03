package reisetech.StudentManagement.StudentRepository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE isDeleted = false")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  @Select("SELECT * FROM students_courses WHERE student_Id =#{studentId}")
  List<StudentCourse> searchStudentsCourses(String studentId);

  @Insert("INSERT INTO students(name,furigana,nickname,email,address,age,gender,remark,isDeleted) "
      + "VALUES(#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{remark},false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id,course_name,start_date,end_date)"
      + "VALUES(#{studentId},#{courseName},#{startDate},#{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentsCourses(StudentCourse studentCourse);

  @Update("UPDATE students SET name=#{name},furigana=#{furigana},nickname=#{nickname},"
      + "email=#{email},address=#{address},age=#{age},gender=#{gender},remark=#{remark},isDeleted=#{isDeleted} WHERE id =#{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name=#{courseName} WHERE course_Id =#{courseId}")
  void updateStudentsCourses(StudentCourse studentCourse);
}








