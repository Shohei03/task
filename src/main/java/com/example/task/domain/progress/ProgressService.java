package com.example.task.domain.progress;

import com.example.task.domain.api.ApiProgressEntity;
import com.example.task.web.progress.ProgressForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgressService {
    //進捗テーブルからレコードを取得
    List<ProgressEntity> getProgress();


    //進捗テーブルからタスクIDに応じたレコードを取得　更新の新しい順
    Page<ProgressEntity> getProgressByTaskIdDateDesc(int taskId, Pageable pageable);

    //進捗テーブルからタスクIDに応じたレコードを取得
    List<ProgressEntity> getProgressByTaskId(int taskId);

    // 進捗レコード登録
    void registerRecord(ProgressForm progressForm, String userId);

    //タスクの更新
    //所要時間、（ステータス←変更があれば更新、完了であれば、完了日を本日の日にちで登録）
    void updateTask(ProgressForm progressForm);

    //チャート作成用進捗取得
    List<ApiProgressEntity> getProgressForChart();

    /* タスクの所要時間取得 */
    float getTotalProcessTime(int taskId);

    // 進捗レコードを更新
    void updateRecord(int progressId, ProgressEntity progress);

    // 更新レコード取得
    ProgressEntity getProgress(int progressId);

    // 対象レコードを削除
    void deleteRecord(int progressId);

}
