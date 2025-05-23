package reisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
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

  @NotNull(message = "仮申込、本申込、受講中、受講終了のいずれかを入力してください。")
  private Status status;


  public StudentCourse(String courseId, String studentId, String courseName,
      LocalDateTime startDate, LocalDateTime endDate, Status status) {
    this.courseId = courseId;
    this.studentId = studentId;
    this.courseName = courseName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    StudentCourse that = (StudentCourse) other;

    return Objects.equals(courseId, that.courseId) &&
        Objects.equals(studentId, that.studentId) &&
        Objects.equals(courseName, that.courseName) &&
        Objects.equals(startDate.truncatedTo(ChronoUnit.SECONDS),
            that.startDate.truncatedTo(ChronoUnit.SECONDS)) &&
        Objects.equals(endDate.truncatedTo(ChronoUnit.SECONDS),
            that.endDate.truncatedTo(ChronoUnit.SECONDS)) &&
        status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(courseId, studentId, courseName,
        startDate.truncatedTo(ChronoUnit.SECONDS),
        endDate.truncatedTo(ChronoUnit.SECONDS), status);
  }
}
