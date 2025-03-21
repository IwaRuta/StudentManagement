package reisetech.StudentManagement.data;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "申し込み状況")
@Getter
@Setter

public class StudentCourseApplication {

  @Pattern(regexp = "^\\d+$", message = "数字のみで入力してください。")
  private String statusId;
  private String courseId;
  private String studentId;

  @NotBlank(message = "申し込み状況を入力してください。")
  private String status;

  public StudentCourseApplication(String statusId, String courseId, String studentId,
      String status) {
    this.statusId = statusId;
    this.courseId = courseId;
    this.studentId = studentId;
    this.status = status;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    StudentCourseApplication that = (StudentCourseApplication) other;

    return Objects.equals(statusId, that.statusId) &&
        Objects.equals(courseId, that.courseId) &&
        Objects.equals(studentId, that.studentId) &&
        Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusId, courseId, statusId);
  }
}
