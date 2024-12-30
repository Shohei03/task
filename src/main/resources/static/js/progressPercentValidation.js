'use strict'

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('progressForm');
    const initialProgressPercent = parseInt(form.dataset.initialProgressPercent, 10);

    form.addEventListener('submit', function(event) {
        const currentProgressPercent = parseInt(document.getElementById('progressPercent').value, 10);

        console.log('currentProgressPercent: ' + currentProgressPercent);
        // 小さい場合に確認メッセージ
        if (currentProgressPercent < initialProgressPercent) {
            console.log('initialProgressPercent: ' + initialProgressPercent);
            const confirmMessage = `進捗率が前回の値（${initialProgressPercent}）より小さいです。登録しますか？`;
            if(!confirm(confirmMessage)) {
                // 確認をキャンセルした場合、送信を中止
                event.preventDefault();
                return false;
            }
        }
    });
});