package com.example.task.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // パスワードが入力されている場合のみチェックを行う
        if (password == null || password.isEmpty()) {
            return true; // 入力がない場合はバリデーション成功
        }
        // パスワードが8文字以上の場合は成功
        return password.length() >= 8;
    }
}
