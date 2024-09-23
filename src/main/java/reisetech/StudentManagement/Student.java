package reisetech.StudentManagement;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String address;
  private int age;
  public String gender;
}
