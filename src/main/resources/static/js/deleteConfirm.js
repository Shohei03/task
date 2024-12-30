
function confirmDelete(event, element) {
    // 確認メッセージを表示
    const confirmation = confirm("本当に削除しますか？");
    if (!confirmation) {
        // キャンセル時はデフォルトの動作を無効化
        event.preventDefault();
        return false;
    }
    // OK の場合はデフォルト動作を続行
    return true;
}