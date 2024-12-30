'use strict'

document.addEventListener("DOMContentLoaded", function() {
// ボタン要素を取得
var button = document.querySelector('.actionButton');
console.log(button);
button.addEventListener('click', function() {
    console.log("Submitボタンが押されました！");
    var password1 = document.getElementById('password').value;
    var password2 = document.getElementById('password2').value;

    if(password1 !== password2) {
        event.preventDefault();
        var error_text = document.getElementById('error__text');
        error_text.style.display = 'block';
    } else {
        var error_text = document.getElementById('error__text');
        error_text.style.display = 'none';
    }
});
});