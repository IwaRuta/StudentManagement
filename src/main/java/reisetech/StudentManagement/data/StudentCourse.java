package reisetech.StudentManagement.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private String courseId;
  private String studentId;
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
