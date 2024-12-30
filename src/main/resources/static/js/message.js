
document.addEventListener('DOMContentLoaded', function () {
    const messageElement = document.getElementById('message');

    if (messageElement) {
        // 5秒後にメッセージを消す
        setTimeout(function () {
            if (messageElement) {
                messageElement.remove();
            }
        }, 3000); // 5000ミリ秒（5秒）

        // スクロールしたとき
        window.addEventListener('scroll', function () {
            messageElement.remove();
        });

        // マウスが動いたとき
        /*
        window.addEventListener('mousemove', function () {
            messageElement.remove();
        });
        */

        // 画面をリサイズしたとき
        window.addEventListener('resize', function () {
            messageElement.remove();
        });
    }
});