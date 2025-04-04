package reisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。 申込状況詳細を受講生、受講生コース情報、申込状況、もしくはその逆の変換を行うコンバーターです。
 */

@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。 受講生コース情報は受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList       　受講生一覧
   * @param studentCourseList 　受講生コース情報のリスト
   * @return　受講生詳細情報のリスト
   */

  public List<StudentDetail> convertStudentDetails(
      List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();

    for (Student student : studentList) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = new ArrayList<>();
      for (StudentCourse studentCourse : studentCourseList) {
        if (student.getId().equals(studentCourse.getStudentId())) {
          convertStudentCourseList.add(studentCourse);
        }
      }
      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }


}
