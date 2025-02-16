package reisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @Pattern(regexp = "^\\d+$", message = "数字のみで入力してください。")
  private String courseId;

  @Pattern(regexp = "^\\d+$", message = "数字のみで入力してください。")
  private String studentId;

  @NotBlank(message = "コース名を入力してください。")
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public StudentCourse(String courseId, String studentId, String courseName,
      LocalDateTime startDate, LocalDateTime endDate) {
    this.courseId = courseId;
    this.studentId = studentId;
    this.courseName = courseName;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}