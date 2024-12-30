package com.example.task.web.task;

import com.example.task.domain.auth.UserEntity;
import com.example.task.domain.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestTaskController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserEntity user;


    /*
   　類似ユーザ名を表示
    */
    @GetMapping("/autocomplete")
    public List<UserEntity> newTask(Model model, @RequestParam(name = "query", required = false, defaultValue = "") String query){
        //ユーザを取得
        List<UserEntity> userList = userService.getUserByAmbiguousName(query);

        return userList;
    }


}
