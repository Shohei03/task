package com.example.task.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DueDateValidator.class) // バリデーションロジックを定義するクラス
@Target(ElementType.TYPE) // クラス単位で適用
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDueDate {
    String message() default "期日は開始日以降の日付を指定してください。";
    String propertyName() default "validDueDate"; // プロパティ名を指定
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}