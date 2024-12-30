package com.example.task.domain.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Repository
public class UserEntity implements Serializable {
    // ユーザーID
    @Id
    private String userId;

    // ユーザー名
    private String userName;

    // パスワード
    private String password;

    // メールアドレス
    private String email;

    // ユーザーの役割
    private Authority role;

    // レコード作成日時
    private LocalDate createdAt;

    // レコード更新日時
    private LocalDate updatedAt;

    // token（メール送信時使用）
    private String token;

    public enum Authority {
        ROLE_ADMIN, ROLE_USER
    }
}
