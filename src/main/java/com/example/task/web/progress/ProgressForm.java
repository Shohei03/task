package com.example.task.web.progress;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressForm implements Serializable {
    private static final long serialVersionUID = 1L;

    //タスクID
    private int taskId;

    // 進捗率
    @NotNull(message = "進捗率は必須です。")
    @Min(value = 0, message = "進捗率は0以上である必要があります。")
    @Max(value = 100, message = "進捗率は100以下である必要があります。")
    private int progressPercent;

    //コメント
    @Size(max = 200, message = "コメントは200文字以内で入力してください。")
    private String comment;

    // タスクのステータス
    @NotBlank(message = "ステータスは必須です。")
    private String status;

    // 所要時間（時間）
    @NotNull(message = "所要時間は必須です。")
    @Min(value = 0, message = "所要時間は0以上である必要があります。")
    private float processTime;
}
