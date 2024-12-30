package com.example.task.domain.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Repository
public class ApiProgressEntity implements Serializable {
    // 進捗記録ID
    private String id;

    // タスク名
    private String name;

    // タスク担当者
    //private String userName;

    // タスク詳細
    private String description;


    // 開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    // 期限
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate;

    // 進捗率
    private int progress;

}
