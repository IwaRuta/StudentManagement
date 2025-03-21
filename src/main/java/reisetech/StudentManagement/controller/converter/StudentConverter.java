package reisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.data.StudentCourseApplication;
import reisetech.StudentManagement.domain.StudentCourseStatus;
import reisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
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

  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  /**
   * 受講生に紐づく受講生コース情報、申込状況をマッピングする。
   *
   * @param studentList                  　受講生一覧
   * @param studentCourseList            　受講生コース情報
   * @param studentCourseApplicationList 　申込状況一覧
   * @return　申込状況詳細一覧
   */

  public List<StudentCourseStatus> couvertStudentCourseStausList(List<Student> studentList,
      List<StudentCourse> studentCourseList,
      List<StudentCourseApplication> studentCourseApplicationList) {

    List<StudentCourseStatus> studentCourseStatusList = new ArrayList<>();

    studentList.forEach(student -> {
      StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
      studentCourseStatus.setStudent(student);
      List<StudentCourse> convertStudentCourseList = getStudentCourseList(student,
          studentCourseList);
      List<StudentCourseApplication> convertStudentCourseApplicationList = getStudentCourseAplicationList(
          convertStudentCourseList, studentCourseApplicationList);
      studentCourseStatus.setStudentCourseList(convertStudentCourseList);
      studentCourseStatus.setStudentCourseApplicationList(convertStudentCourseApplicationList);
      studentCourseStatusList.add(studentCourseStatus);
    });
    return studentCourseStatusList;
  }

  private List<StudentCourse> getStudentCourseList(Student student,
      List<StudentCourse> studentCourseList) {
    List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
        .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
        .collect(Collectors.toList());
    return convertStudentCourseList;
  }

  private List<StudentCourseApplication> getStudentCourseAplicationList(
      List<StudentCourse> studentCourseList,
      List<StudentCourseApplication> studentCourseApplicationList) {
    List<StudentCourseApplication> convertStudentCourseApplicationList = new ArrayList<>();
    studentCourseList.forEach(studentCourse -> studentCourseApplicationList.stream()
        .filter(studentCourseApplication -> studentCourse.getStudentId()
            .equals(studentCourseApplication.getStudentId()))
        .forEach(convertStudentCourseApplicationList::add));
    return convertStudentCourseApplicationList;
  }
}

