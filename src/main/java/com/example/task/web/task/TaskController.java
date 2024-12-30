package com.example.task.web.task;

import com.example.task.domain.auth.UserEntity;
import com.example.task.domain.auth.UserService;
import com.example.task.domain.task.TaskEntity;
import com.example.task.domain.task.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TaskEntity task;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserEntity user;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/new")
    public String newTask(Model model) {
        if (!model.containsAttribute("taskForm")) {
            TaskForm taskForm = new TaskForm();
            model.addAttribute("taskForm", taskForm);
        }

        //model.addAttribute("taskForm", taskForm);
        return "task/taskNew";
    }

    @PostMapping("/new")
    public String createTask(Model model, @Validated @ModelAttribute TaskForm taskForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskForm", bindingResult);
            redirectAttributes.addFlashAttribute("taskForm", taskForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            // エラー時のリダイレクト
            return "redirect:/task/new";
        }


        String userId = taskForm.getUserId();

        taskForm.setUserId(null);

        task = modelMapper.map(taskForm, TaskEntity.class);

        //IDからユーザ取得
        user = userService.getUserById(userId);

        task.setUser(user);

        //タスクをDBに登録
        taskService.registerTask(task);

        String successMessage = messageSource.getMessage("registration.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        //model.addAttribute("taskForm", taskForm);
        return "redirect:/";
    }

    /*
        タスク一覧作成
    */
    @GetMapping("/list")
    public String taskList(Model model,
                           @PageableDefault(size = 5) Pageable pageable) {
        //タスク全件取得（ページングあり）
        Page<TaskEntity> taskPageList = taskService.getAllTask(pageable);
        List<TaskEntity> taskList = taskPageList.getContent();

        model.addAttribute("page", taskPageList);
        model.addAttribute("taskList", taskList);

        return "task/taskList";
    }

    @GetMapping("/detail/{taskId}")
    public String taskDetail(Model model, @PathVariable("taskId") int taskId){
        if(!model.containsAttribute("taskForm")){
            //タスク取得
            TaskEntity task = taskService.getTaskById(taskId);

            TaskForm taskForm = new TaskForm();
            taskForm = modelMapper.map(task, TaskForm.class);

            taskForm.setUserId(task.getUser().getUserId());

            model.addAttribute("taskForm", taskForm);
        }

        return "task/taskEditForm";
    }


    @PostMapping("/edit")
    public String editTask(Model model, @Validated @ModelAttribute TaskForm taskForm,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskForm", bindingResult);
            redirectAttributes.addFlashAttribute("taskForm", taskForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            // エラー時のリダイレクト
            return "redirect:/task/detail/" + taskForm.getTaskId();
        }

        String userId = taskForm.getUserId();

        taskForm.setUserId(null);
        taskForm.setUserName(null);

        task = modelMapper.map(taskForm, TaskEntity.class);

        //IDからユーザ取得
        user = userService.getUserById(userId);

        task.setUser(user);

        //タスク情報を更新（DB）
        taskService.updateTask(task);

        String successMessage = messageSource.getMessage("edit.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        //model.addAttribute("taskForm", taskForm);
        return "redirect:/";
    }

    // タスク削除
    @GetMapping("/delete/{taskId}")
    public String delete(Model model, @PathVariable("taskId") int taskId,
             RedirectAttributes redirectAttributes) {
        // 対象のタスクを削除（delete_flg = 1に）
        taskService.deleteTask(taskId);

        String delSuccessMessage = messageSource.getMessage("del.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("delSuccessMessage", delSuccessMessage);

        return "redirect:/task/list";
    }
}