package com.example.task.domain.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TaskService {
    /* タスクIDよりタスク取得 */
    public TaskEntity getTaskById(int id);

    /*　更新の新しい順にタスク情報を5件取得 */
    List<TaskEntity> getNewTask();

    /* タスクの登録 */
    void registerTask(TaskEntity task);

    /* タスクリスト取得 */
    Page<TaskEntity> getAllTask(Pageable pageable);

    /* タスクの更新 */
    void updateTask(TaskEntity task);

    /* タスクの削除 */
    void deleteTask(int id);



    /* ステータスの更新 */
    void updateStatus(int taskId, String newStatus);


}
