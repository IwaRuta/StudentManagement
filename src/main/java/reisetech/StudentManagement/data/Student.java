package reisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
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

  public Student(String id, String name, String furigana, String nickname, String email,
      String address, int age, String gender, String remark, boolean isDeleted) {
    this.id = id;
    this.name = name;
    this.furigana = furigana;
    this.nickname = nickname;
    this.email = email;
    this.address = address;
    this.age = age;
    this.gender = gender;
    this.remark = remark;
    this.isDeleted = isDeleted;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Student student = (Student) other;
    return age == student.age &&
        Objects.equals(id, student.id) &&
        Objects.equals(name, student.name) &&
        Objects.equals(furigana, student.furigana) &&
        Objects.equals(nickname, student.nickname) &&
        Objects.equals(email, student.email) &&
        Objects.equals(address, student.address) &&
        Objects.equals(gender, student.gender) &&
        Objects.equals(remark, student.remark) &&
        Objects.equals(isDeleted, student.isDeleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, furigana, nickname, email, address, gender, remark, isDeleted);
  }
}
