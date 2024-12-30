package com.example.task.domain.task;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskRepository;

    /* タスクIDよりタスク取得 */
    public TaskEntity getTaskById(int id) {
        return taskRepository.findTaskById(id);
    };

    /*　更新の新しい順にタスク情報を5件取得 */
    public List<TaskEntity> getNewTask() {
        return taskRepository.findNewTask();
    };

    /* タスクの登録 */
    @Transactional
    public void registerTask(TaskEntity task){

        taskRepository.registerTask(task);
    };

    /* タスクの更新 */
    @Transactional
    public void updateTask(TaskEntity task){
        taskRepository.updateTask(task);
    };

    /* タスクリスト取得 */
    public Page<TaskEntity> getAllTask(Pageable pageable){
        RowBounds rowBounds = new RowBounds(
                (int)pageable.getOffset(), pageable.getPageSize());

        List<TaskEntity> taskList = taskRepository.findAllTask(rowBounds);

        int total = taskRepository.getCount();

        return new PageImpl<>(taskList, pageable, total);
    };

    /* タスクの削除 */
    @Transactional
    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    };

    /* ステータスの更新 */
    @Transactional
    public void updateStatus(int taskId, String newStatus){
        taskRepository.updateStatus(taskId, newStatus);
    };
}
