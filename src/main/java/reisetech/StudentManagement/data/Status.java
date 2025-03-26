package reisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "申込の状態を示す列挙型")
public enum Status {
  仮申込, 本申込, 受講中, 受講終了
}

