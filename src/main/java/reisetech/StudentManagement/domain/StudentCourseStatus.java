package reisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reisetech.StudentManagement.data.Student;
import reisetech.StudentManagement.data.StudentCourse;
import reisetech.StudentManagement.data.StudentCourseApplication;

@Schema(description = "受講生コース情報ステータス詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StudentCourseStatus {

  @Valid
  private Student student;

  @Valid
  public List<StudentCourse> studentCourseList;

  @Valid
  public List<StudentCourseApplication> studentCourseApplicationList;
}
