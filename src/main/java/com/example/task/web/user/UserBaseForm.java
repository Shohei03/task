package com.example.task.web.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserBaseForm implements Serializable {
    private static final long serialVersionUID = 1L;
    /* ユーザー番号 */
    private String userId;

    /* ユーザ名（姓） */
    @NotBlank(message ="名前（姓）を入力してください")
    @Size(max = 50, message="名前は50文字以内で入力してください")
    private String userNameSei;

    /* ユーザ名（名前） */
    @NotBlank(message ="名前（名）を入力してください")
    @Size(max = 50, message="名前は50文字以内で入力してください")
    private String userNameMei;


    /* ユーザ権限 */
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "ユーザ権限は項目から選択してください。")
    private String authority;
}
