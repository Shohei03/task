package com.example.task.web.progress;

import com.example.task.domain.progress.ProgressEntity;

public class ConvertProgress {

    // 前回の進捗レコードを引継ぎ用
    public static ProgressForm progressEntityToForm(ProgressEntity progressEntity, ProgressForm progressForm) {

        progressForm.setStatus(progressEntity.getTask().getStatus());
        progressForm.setProgressPercent(progressEntity.getProgressPercent());

        return progressForm;
    }

}
