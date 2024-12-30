window.addEventListener('load', function () {
    // durationクラスを持つすべての要素を取得
    const durationElements = document.querySelectorAll('.duration');

    durationElements.forEach(function (durationElement) {
        // テキストを取得
        const dateValue = durationElement.textContent;

        // 日付をDate型に変換
        const targetDate = new Date(dateValue);
        const currentDate = new Date();

        // 差を計算
        const diff = currentDate - targetDate;
        const diffInHours = Math.floor(diff / (1000 * 60 * 60)); // 時間
        const diffInDays = Math.floor(diff / (1000 * 60 * 60 * 24)); // 日数

        // 結果を更新
        if (diffInDays > 0) {
            durationElement.textContent = `${diffInDays}日前`;
        } else if (diffInHours > 0) {
            durationElement.textContent = `${diffInHours}時間前`;
        } else {
            durationElement.textContent = 'たった今';
        }
    });
});