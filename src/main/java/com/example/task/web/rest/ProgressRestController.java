package com.example.task.web.rest;

import com.example.task.domain.progress.ProgressEntity;
import com.example.task.domain.progress.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/restProgress")
public class ProgressRestController {
    @Autowired
    ProgressService progressService;


    @PostMapping("/updateRecord/{id}")
    public ResponseEntity<Map<String, Object>> updateRecord(@PathVariable("id") int progressId,
                                                            @RequestBody Map<String, String> request) {

        ProgressEntity progress = new ProgressEntity();


        progress.setComment(request.get("newComment"));
        progress.setProcessTime(Float.parseFloat(request.get("newProcessTime")));
        progress.setProgressPercent(Integer.parseInt(request.get("newProgressPercent")));

        // 進捗レコードの更新
        progressService.updateRecord(progressId, progress);

        // 更新レコードを取得
        ProgressEntity newProgress = progressService.getProgress(progressId);

        // 実績時間を取得
        float actualTime = progressService.getTotalProcessTime(newProgress.getTask().getTaskId());

        // 更新データ表示用に送信設定
        Map<String, Object> response = new HashMap<>();
        response.put("sumTime", actualTime);
        response.put("progress", newProgress);
        response.put("success", true);

        // 更新レコードを返す
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deleteRecord/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecord(@PathVariable("id") int progressId) {

        // 対象レコードを削除
        progressService.deleteRecord(progressId);

        // 更新データ表示用に送信設定
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        // 更新レコードを返す
        return ResponseEntity.ok(response);


    }

}
