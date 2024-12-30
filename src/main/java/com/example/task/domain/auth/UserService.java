package com.example.task.domain.auth;

import com.example.task.web.user.UserForm;
import com.example.task.web.user.UserNewRegistForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    /* ユーザ一覧用*/
    Page<UserEntity> findAllUser(Pageable pageable);

    /* ユーザ作成 */
    void registUser(UserEntity user);

    /* 自分のユーザ情報編集 */
    void modifyMyInfo(UserForm userForm);

    /* 対象のユーザ情報を削除 */
    void deleteUser(String userId);

    /* 管理者がユーザ情報編集 */
    void modifyUserInfo(UserNewRegistForm userNewRegistForm);

    /* ユーザテーブルで最大のユーザIDの値を取得*/
    String getMaxUserId();

    /* トークンよりユーザ情報を取得 */
    UserEntity getUserByToken(String token);

    /* ユーザIDをもとにユーザ情報取得*/
    UserEntity getUserById(String id);

//    /* メール送信 */
//    void sendEmail(UserEntity user);

    /* token削除、パスワード設定 */
    void deleteToken(String userId,String password);

    /* 曖昧なユーザ名によりユーザ取得 */
    List<UserEntity> getUserByAmbiguousName(String userName);
}

