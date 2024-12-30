const nameInput = document.getElementById("user");
const userIdInput = document.getElementById("userId");
const autocompleteList = document.getElementById("autocompleteList");

nameInput.addEventListener("input", function() {
    const query = nameInput.value;

    if (query.length > 0) {
        fetch(`/autocomplete?query=${query}`)
            .then(response => response.json())
            .then(data => {
                // リストをクリア
                autocompleteList.innerHTML = "";

                // 候補リストを表示
                data.forEach(item => {
                    const div = document.createElement("div");
                    div.classList.add("list-group-item", "list-group-item-action");
                    div.textContent = item.userName;
                    div.dataset.userId = item.userId; // ユーザーIDをデータ属性に設定

                    // 候補をクリックしたら入力フィールドに反映
                    div.addEventListener("click", function() {
                        nameInput.value = item.userName;
                        userIdInput.value = item.userId;  // ユーザーIDを隠しフィールドに設定
                        autocompleteList.innerHTML = ""; // リストをクリア
                    });

                    autocompleteList.appendChild(div);
                });
            })
            .catch(error => console.error("Error:", error));
    } else {
        // クエリが空の場合、候補リストをクリア
        autocompleteList.innerHTML = "";
    }
});

// 入力フィールドから離れた時に候補リストを消す
document.addEventListener("click", function(e) {
    if (!autocompleteList.contains(e.target) && e.target !== nameInput) {
        autocompleteList.innerHTML = "";
    }
});