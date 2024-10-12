package reisetech.StudentManagement.StudentRepository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourse();

  @Insert("INSERT INTO students(id,name,furigana,nickname,email,address,age,gender,remark,isDeleted) "
      + "VALUES(#{id},#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{remark},false)")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(course_id,student_id,course_name,start_date,end_date)"
      + "VALUES(#{courseId},#{studentId},#{courseName},#{startDate},#{endDate})")
  void registerStudentsCourses(StudentCourse studentCourse);
}








