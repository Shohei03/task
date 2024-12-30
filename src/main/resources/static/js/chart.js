window.onload = function() {

    fetch('/api/progress/chart')
        .then(response => response.json())
        .then(tasks => {
          // gantt をセットアップ
          var gantt = new Gantt("#gantt", tasks, {
            // ダブルクリック時
            on_click: (task) => {
              console.log(task.description);
            },
            // 日付変更時
            on_date_change: (task, start, end) => {
              console.log(`${task.name}: change date`);
            },
            // 進捗変更時
            on_progress_change: (task, progress) => {
              console.log(`${task.name}: change progress to ${progress}%`);
            },
          });
        })
        .catch(error => console.error('エラー:', error));

};


