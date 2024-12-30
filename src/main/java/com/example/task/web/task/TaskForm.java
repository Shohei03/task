package com.example.task.web.task;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

//@ValidDueDate  // カスタムアノテーションを適応
@Data
public class TaskForm implements Serializable {
    private static final long serialVersionUID = 1L;

    // タスクID
    private int taskId;

    // タスク名
    @NotBlank(message = "タスク名は必須です。")
    @Size(max = 100, message = "100文字以下で入力してください。")
    private String taskName;

    // タスクの担当者ID
    @NotNull(message = "担当者IDは必須です。")
    private String userId;

    // 担当者名
    @NotBlank(message = "担当者名は必須です。")
    @Size(min = 2, max = 50, message = "担当者名は2文字以上、50文字以下で入力してください。")
    private String userName;

    // タスクの詳細説明
    @NotBlank(message = "タスクの説明は必須です。")
    @Size(max = 300, message = "説明は300文字以内で入力してください。")
    private String description;

    // タスク開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "開始日は必須です。")
    private LocalDate startDate;

    // タスクの期日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "期日は必須です。")
    @Future(message = "期日には未来の日付を指定してください。")
    private LocalDate dueDate;

    // タスクのステータス
    @NotNull(message = "ステータスは必須です。")
    @Pattern(regexp = "未実施|実施中|完了|保留|中止", message = "ステータスは項目から選択してください。")
    private String status;

    // タスクの優先度
    @NotNull(message = "優先度は必須です。")
    @Pattern(regexp = "低|中|高", message = "優先度は「低」「中」「高」のいずれかで入力してください。")
    private String priority;

    // 予定時間（時間）
    @NotNull(message = "予定時間は必須です。")
    @Min(value = 0, message = "予定時間は0以上でなければなりません。")
    private Float scheduleTime;

    // 実績時間（合計）
    @Min(value = 0, message = "実績時間は0以上でなければなりません。")
    private float processTime;

    @AssertTrue(message="開始日は期日以前を入力してください。")
    public boolean isDateValid() {
        if (!this.dueDate.isBefore(this.startDate)) return true;
        return false;
    }

}