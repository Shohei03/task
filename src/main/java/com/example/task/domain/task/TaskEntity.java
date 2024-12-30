package com.example.task.domain.task;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.task.domain.auth.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Data
@NoArgsConstructor
@Repository
public class TaskEntity implements Serializable {

    // タスク番号
    @Id
    private int taskId;

    // タスク名
    private String taskName;

    // タスクの担当者
    private UserEntity user;

    // タスクの詳細説明
    private String description;

    // タスク開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    // タスクの期日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    // タスク実施完了日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;

    // タスクのステータス
    private String status;

    // タスクの優先度
    private String priority;

    // 予定時間（時間）
    private float scheduleTime;

    // レコード作成日時
    private LocalDate createdAt;

    // レコード更新日時
    private LocalDate updatedAt;

    // 進捗率
    private int progressPercent;

    // 削除フラグ
    private int delFlg;

}
