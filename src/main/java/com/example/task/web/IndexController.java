package com.example.task.web;

import com.example.task.domain.progress.ProgressEntity;
import com.example.task.domain.progress.ProgressService;
import com.example.task.domain.task.TaskEntity;
import com.example.task.domain.task.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private MessageSource messageSource;

    /**
     * タスクを5件取得（更新日の新しい順に）
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String successMessage = (String) session.getAttribute("successMessage");

        // ログイン成功時のメッセージを処理
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }


        // 更新の新しい順にタスクを5件取得
        List<TaskEntity> taskList = taskService.getNewTask();

        model.addAttribute("taskList", taskList);

        //　更新の新しい順にタスク進捗レコードを5件取得
        List<ProgressEntity> progressList = progressService.getProgress();

        model.addAttribute("progressList", progressList);

        return "index";
    }


}