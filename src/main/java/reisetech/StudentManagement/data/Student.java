package reisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Pattern(regexp = "^\\d+$", message = "数字のみで入力してください。")
  private String id;

  @NotBlank(message = "氏名を入力してください。")
  private String name;

  @NotBlank(message = "フリガナを入力してください。")
  private String furigana;

  @NotBlank(message = "ニックネームを入力してください。")
  private String nickname;

  @NotBlank(message = "Emailを入力してください。")
  @Email(message = "Email形式で入力してください。")
  private String email;

  @NotBlank(message = "住所を入力してください。")
  private String address;

  private int age;

  @NotBlank(message = "性別を入力してください。")
  private String gender;

  private String remark;
  private boolean isDeleted;
}
