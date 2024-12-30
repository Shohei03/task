package com.example.task.domain.util;

import java.text.DecimalFormat;

public class Util {

    // 小数点以下がない場合は表示しない。
    public static float formatValue(float value) {
        // 小数点以下がない場合は整数として表示
        if (value == Math.floor(value)) {
            return (float) ((int) value);
        }
        // 小数点以下がある場合は全体を表示
        DecimalFormat df = new DecimalFormat("#.##"); // 必要な精度に応じて変更
        return value;
    }



}
