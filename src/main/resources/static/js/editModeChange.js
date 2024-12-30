function enableEdit(row) {
    // 他の行を通常モードに戻す
    const rows = document.querySelectorAll('#progressList');
    rows.forEach( r => {
        // 他の行を通常モードに戻す
        r.querySelectorAll('.display-mode').forEach(el => el.classList.remove('hidden')); // 通常表示を有効
        r.querySelectorAll('.edit-mode').forEach(el => el.classList.add('hidden')); // 編集モードを非表示
        });

        if(row == null){
            return;  // 処理終了
        }

        isEditing = row.getAttribute('data-editing') === 'true';


        if(isEditing) {
            // 編集モードから通常モードに戻す
                row.querySelectorAll('.display-mode').forEach(el => el.classList.remove('hidden'));
                row.querySelectorAll('.edit-mode').forEach(el => el.classList.add('hidden'));
                row.setAttribute('data-editing', 'false');  // フラグをリセット
        } else {
                row.querySelectorAll('.display-mode').forEach(el => el.classList.add('hidden')); // 通常表示を非表示
                row.querySelectorAll('.edit-mode').forEach(el => el.classList.remove('hidden')); // 編集モードを有効
                row.setAttribute('data-editing', 'true'); // フラグを設定

        }
    }

function saveEdit(button) {
    const row = button.closest('#progressList');

    //コメント
    const comment = row.querySelector('[name="comment"]');
    const commentChanged = row.querySelector('[name="commentChanged"]');
    // 進捗率
    const progressPercent = row.querySelector('[name="progressPercent"]');
    const progressPercentChanged = row.querySelector('[name="progressPercentChanged"]');
    // 所要時間
    const processTime = row.querySelector('[name="processTime"]');
    const processTimeChanged = row.querySelector('[name="processTimeChanged"]');

    const id = row.getAttribute('data-id');

    const actualTime = document.querySelector('[name="actualTime"]')

    // 未入力チェック
    const errors = [];
    if (!commentChanged || commentChanged.value.trim() === ''){
        errors.push('コメントを入力してください');
        const errorDiv = commentChanged.nextElementSibling;
        errorDiv.textContent = 'コメントを入力してください';
        errorDiv.style.color = 'red';
    }else {
         commentChanged.nextElementSibling.textContent = ''; // エラークリア
     }
    if(!progressPercentChanged || progressPercentChanged.value.trim() === ''){
        errors.push('進捗率を入力してください');
        const errorDiv2 = progressPercentChanged.nextElementSibling;
        errorDiv2.textContent = '進捗率を入力してください';
        errorDiv2.style.color = 'red';
    } else if (isNaN(progressPercentChanged.value.trim())){
        errors.push('進捗率には数値を入力してください');
        const errorDiv2 = progressPercentChanged.nextElementSibling;
        errorDiv2.textContent = '進捗率には数値を入力してください';
        errorDiv2.style.color = 'red';
    }
    else {
         progressPercentChanged.nextElementSibling.textContent = ''; // エラークリア
     }
    // 所要時間チェック
    if(!processTimeChanged || processTimeChanged.value.trim() === ''){
        errors.push('所要時間を入力してください');
        const errorDiv3 = processTimeChanged.nextElementSibling;
        errorDiv3.textContent = '所要時間を入力してください';
        errorDiv3.style.color = 'red';
    } else if (isNaN(processTimeChanged.value.trim())) {
        errors.push('所要時間には数値を入力してください');
        const errorDiv3 = processTimeChanged.nextElementSibling;
        errorDiv3.textContent = '所要時間には数値を入力してください';
        errorDiv3.style.color = 'red';
    } else if (Number(processTimeChanged.value.trim()) < 0) {
          errors.push('所要時間には0以上の数値を入力してください');
          const errorDiv3 = processTimeChanged.nextElementSibling;
          errorDiv3.textContent = '所要時間には0以上の数値を入力してください';
          errorDiv3.style.color = 'red';
    }else {
         processTimeChanged.nextElementSibling.textContent = ''; // エラークリア
    }

    if(errors.length > 0){
        //alert(errors.join('\n'));
        return;
    }


    if (commentChanged || progressPercentChanged || processTimeChanged) {
        const commentValue = commentChanged.value;
        const processTimeValue = processTimeChanged.value
        const progressPercentValue = progressPercentChanged.value
        //const statusChangedValue = statusChanged.value

        // 複数の値をオブジェクトにまとめる
        const requestData = {
            newComment: commentValue,
            newProcessTime: processTimeValue,
            newProgressPercent: progressPercentValue,
            //newStatus: statusChangedValue
        };

        // サーバーに更新リクエストを送る（AJAX）
        fetch(`/restProgress/updateRecord/${id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                // 成功時は新しい値を表示
                    //status.textContent = data.progress.status;
                    comment.textContent = data.progress.comment;
                    progressPercent.textContent = data.progress.progressPercent;
                    processTime.textContent = data.progress.processTime;

                    actualTime.textContent = data.sumTime;

                    enableEdit(null); // 編集モードを解除
                } else {
                    alert('更新に失敗しました');
                }
            });
    }
}


function deleteProgress(button) {
    const row = button.closest('#progressList');
    const id = row.getAttribute('data-id');

    // サーバーに更新リクエストを送る（AJAX）
    fetch(`/restProgress/deleteRecord/${id}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'},
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
            // 削除成功時はページ更新（実績時間なども変わるため）
                location.reload();
            } else {
                alert('削除に失敗しました');
            }
        })
        .catch(error => {
            // 通信エラー時の処理
            console.error('削除通信中のエラー:', error);
            alert('削除リクエスト中にエラーが発生しました');
        });
}

function adjustTextareaHeight(textarea) {
    textarea.style.height = 'auto'; // 高さをリセット
    textarea.style.height = `${textarea.scrollHeight}px`; // 必要な高さを再設定
}