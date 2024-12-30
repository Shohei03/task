package com.example.task.web.rest;

import com.example.task.domain.api.ApiProgressEntity;
import com.example.task.domain.progress.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
//@CrossOrigin(origins = "http://localhost:3000/")
public class RestProgressController {
    @Autowired
    ProgressService progressService;

    @GetMapping("/chart")
    public List<ApiProgressEntity> getProgressForChart() {
        List<ApiProgressEntity> chartList = progressService.getProgressForChart();

        return chartList;
    }

}
