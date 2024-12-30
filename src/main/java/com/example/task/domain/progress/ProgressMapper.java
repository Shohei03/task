package com.example.task.domain.progress;

import com.example.task.domain.api.ApiProgressEntity;
import com.example.task.web.progress.ProgressForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ProgressMapper {

    // 進捗レコード取得
    List<ProgressEntity> findProgress();

    //進捗テーブルからレコードを取得（トップページ用）
    List<ProgressEntity> findProgressTop();

    // タスクIDに応じた進捗レコードを取得 更新日の新しい順
    List<ProgressEntity> findProgressByTaskIdDateDesc(int taskId, RowBounds rowBounds);

    // タスクIDに応じた進捗レコードを取得
    List<ProgressEntity> findProgressByTaskId(int taskId);

    // タスクIDごとに進捗レコードをカウント
    int getProgressCountOfTask(int taskId);

    // 進捗レコード登録
    void registerRecord(ProgressForm progressForm, String userId);

    //タスクの更新
    //所要時間、（ステータス←変更があれば更新、完了であれば、完了日を本日の日にちで登録）
    void updateTask(ProgressForm progressForm);


    //チャート作成用進捗取得
    List<ApiProgressEntity> getProgressForChart();

    //　タスクのトータル所要時間取得
    float getTotalProcessTime(int taskId);

    // 進捗レコードの更新
    void updateRecord(int progressId, ProgressEntity progress);

    // 更新レコード取得
    ProgressEntity getProgress(int progressId);

    // 対象レコードを削除
    void deleteRecord(int progressId);


}