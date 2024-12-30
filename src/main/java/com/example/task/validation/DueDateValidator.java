package com.example.task.validation;

import com.example.task.web.task.TaskForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DueDateValidator implements ConstraintValidator<ValidDueDate, TaskForm> {
    private String propertyName;

    @Override
    public void initialize(ValidDueDate constraintAnnotation) {
        this.propertyName = constraintAnnotation.propertyName();
    }

    @Override
    public boolean isValid(TaskForm form, ConstraintValidatorContext context) {
        LocalDate startDate = form.getStartDate();
        LocalDate dueDate = form.getDueDate();

        if (dueDate != null && startDate != null && dueDate.isBefore(startDate)) {
            context.buildConstraintViolationWithTemplate(
                            String.format("%sは開始日以降の日付を指定してください。", propertyName))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
