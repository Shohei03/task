package com.example.task.domain.task;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TaskMapper {
    /* タスクIDよりタスクを取得 */
    TaskEntity findTaskById(int id);

    /*　更新の新しい順にタスク情報を5件取得 */
    List<TaskEntity> findNewTask();

    //タスク登録
    void registerTask(TaskEntity task);

    // タスクの更新
    void updateTask(TaskEntity task);

    //全タスク取得
    List<TaskEntity> findAllTask(RowBounds rowBounds);

    //全タスク数取得
    int getCount();

    //進捗更新時にタスクレコード更新
    void updateTaskByProgress(TaskEntity task);

    //指定タスクのこれまでの所要時間を取得
    //float getProcessTime(int taskId);

    /* タスクの削除 */
    void deleteTask(int id);

    /* ステータスの更新 */
    void updateStatus(int taskId, String newStatus);



}
