package reisetech.StudentManagement.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "条件指定検索に使用")
@Getter
@Setter

public class StudentSearchCondition {

  @Pattern(regexp = "^\\d+$", message = "IDは数字で入力してください")
  private String id;

  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String address;
  private Integer age;
  private String gender;

  public StudentSearchCondition(String id, String name, String furigana,
      String nickname, String email, String address, Integer age, String gender) {
    this.id = id;
    this.name = name;
    this.furigana = furigana;
    this.nickname = nickname;
    this.email = email;
    this.address = address;
    this.age = age;
    this.gender = gender;
  }
}
