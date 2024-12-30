package com.example.task.domain.progress;

import com.example.task.domain.api.ApiProgressEntity;
import com.example.task.domain.task.TaskEntity;
import com.example.task.domain.task.TaskMapper;
import com.example.task.web.progress.ProgressForm;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    ProgressMapper progressMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskEntity taskEntity;

    //進捗テーブルからレコードを取得
    public List<ProgressEntity> getProgress(){
        return progressMapper.findProgress();
    };


    //進捗テーブルからタスクIDに応じたレコードを取得 更新日付新しい順
    public Page<ProgressEntity> getProgressByTaskIdDateDesc(int taskId, Pageable pageable){

        RowBounds rowBounds = new RowBounds(
                (int)pageable.getOffset(), pageable.getPageSize());

        List<ProgressEntity> progressList = progressMapper.findProgressByTaskIdDateDesc(taskId, rowBounds);

        int total = progressMapper.getProgressCountOfTask(taskId);

        return new PageImpl<>(progressList, pageable, total);
    };

    //進捗テーブルからタスクIDに応じたレコードを取得
    public List<ProgressEntity> getProgressByTaskId(int taskId){

        List<ProgressEntity> progressList = progressMapper.findProgressByTaskId(taskId);

        return progressList;
    };



    // 進捗レコード登録
    @Transactional
    public void registerRecord(ProgressForm progressForm,String userId){
        progressMapper.registerRecord(progressForm, userId);
    };

    //タスクの更新
    //所要時間、（ステータス←変更があれば更新、完了であれば、完了日を本日の日にちで登録）
    @Transactional
    public void updateTask(ProgressForm progressForm){
        LocalDate completionDate = null;

        if(progressForm.getStatus() == "完了") {
            completionDate = LocalDate.now();; // 現在の日時を取得
        }

        taskEntity.setTaskId(progressForm.getTaskId());
        taskEntity.setStatus(progressForm.getStatus());
        taskEntity.setCompletionDate(completionDate);
        taskEntity.setProgressPercent(progressForm.getProgressPercent());

        taskMapper.updateTaskByProgress(taskEntity);
    };

    //チャート作成用進捗取得
    public List<ApiProgressEntity> getProgressForChart(){
        return progressMapper.getProgressForChart();
    };

    /* タスクの所要時間取得 */
    public float getTotalProcessTime(int taskId){
        return progressMapper.getTotalProcessTime(taskId);
    }

    // 進捗レコードを更新
    @Transactional
    public void updateRecord(int progressId, ProgressEntity progress){
        progressMapper.updateRecord(progressId, progress);
    };

    // 更新レコード取得
    @Transactional
    public ProgressEntity getProgress(int progressId){
        return progressMapper.getProgress(progressId);
    };

    // 対象レコードを削除
    @Transactional
    public void deleteRecord(int progressId){
        progressMapper.deleteRecord(progressId);
    }

}