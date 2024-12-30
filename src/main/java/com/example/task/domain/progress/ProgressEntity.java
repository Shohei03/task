package com.example.task.domain.progress;

import com.example.task.domain.auth.UserEntity;
import com.example.task.domain.task.TaskEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Repository
public class ProgressEntity {
    // 進捗記録ID
    @Id
    private int progressId;

    // タスク
    private TaskEntity task;

    // タスク担当者
    private UserEntity user;

    // 進捗率
    private int progressPercent;

    // 所要時間
    private float processTime;

    // 進捗に関するコメントや詳細
    private String comment;

    // 進捗が記録された日時
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime progressDate;

    // レコード更新日時
    private LocalDateTime updatedAt;

}
