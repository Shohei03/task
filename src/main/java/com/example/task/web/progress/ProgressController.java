package com.example.task.web.progress;

import com.example.task.domain.progress.ProgressEntity;
import com.example.task.domain.progress.ProgressService;
import com.example.task.domain.task.TaskEntity;
import com.example.task.domain.task.TaskService;
import com.example.task.web.task.TaskForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.task.web.progress.ConvertProgress.progressEntityToForm;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private MessageSource messageSource;


    @GetMapping("/detail/{taskId}")
    public String progressDetail(Model model,
                             @PathVariable("taskId") int taskId,
                             @PageableDefault(size = 5) Pageable pageable
    ){
        //進捗レコード取得
        Page<ProgressEntity> progressPageList = progressService.getProgressByTaskIdDateDesc(taskId, pageable);
        List<ProgressEntity> progressList = progressPageList.getContent();

        model.addAttribute("page", progressPageList);
        model.addAttribute("progressList", progressList);

        if(!model.containsAttribute("progressForm")){
            // 新規作成用
            ProgressForm newProgressForm = new ProgressForm();

            List<ProgressEntity> progressListFornewMake = progressService.getProgressByTaskId(taskId);

            if (!progressListFornewMake.isEmpty()) {
                ProgressEntity progressEntity = progressListFornewMake.get(progressListFornewMake.size() - 1);
                //progressEntity →　progressForm　前回の引継ぎ
                newProgressForm = progressEntityToForm(progressEntity, newProgressForm);
            }

            newProgressForm.setTaskId(taskId);

            model.addAttribute("progressForm", newProgressForm);
        }

        //タスク情報
        TaskEntity task = taskService.getTaskById(taskId);

        TaskForm taskForm = modelMapper.map(task, TaskForm.class);

        // 実績時間を計算
        float actualTime = progressService.getTotalProcessTime(taskId);

        taskForm.setUserId(task.getUser().getUserId());
        taskForm.setProcessTime(actualTime);

        model.addAttribute("taskForm", taskForm);


        return "progress/detail";
    }

    @PostMapping("/new")
    public String newTask(Model model, @Validated @ModelAttribute ProgressForm progressForm,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.progressForm", bindingResult);
            redirectAttributes.addFlashAttribute("progressForm", progressForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            int taskID = progressForm.getTaskId();
            // エラー時のリダイレクト
            return "redirect:/progress/detail/" + taskID;
        }


        //ログインユーザ取得
        final String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        //進捗レコード登録
        progressService.registerRecord(progressForm, userId);

        //タスクの更新
        //所要時間、（ステータス←変更があれば更新、完了であれば、完了日を本日の日にちで登録）
        progressService.updateTask(progressForm);

        String successMessage = messageSource.getMessage("registration.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/";
    }


    @GetMapping("/chart")
    public String chart(Model model) {

        return "progress/chart";
    }

}
