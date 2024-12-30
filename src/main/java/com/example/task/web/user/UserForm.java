package com.example.task.web.user;

import com.example.task.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserForm extends UserBaseForm implements Serializable {
    /* メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "正しいメールアドレスの形式で入力してください")
    private String email;

    /* パスワード */
    @ValidPassword
    private String password;

}

