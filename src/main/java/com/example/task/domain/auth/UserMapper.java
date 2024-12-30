package com.example.task.domain.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    /*　ユーザ情報を取得 */
    List<UserEntity> getAllUser(RowBounds rowBounds);

    /* ユーザ数カウント */
    int getCount();

    /* ユーザ情報作成 */
    void registUser(UserEntity user);

    /* 自分の情報を更新 */
    void modifyMyInfo(UserEntity user);

    /* 対象のユーザ情報を削除 */
    void deleteUser(String userId);

    /* 管理者がユーザー情報を更新 */
    void modifyUserInfo(UserEntity user);

    /* ユーザID(max)取得*/
    String getMaxUserId();

    /* ユーザIDをもとにユーザ情報取得*/
    UserEntity getUserById(String id);

    /* token情報を更新 */
    void updateToken(UserEntity user);

    /* tokenよりユーザ情報を取得 */
    UserEntity getUserByToken(String token);

    /* token削除 */
    void deleteToken(String userId, String password);

    Optional<UserEntity> findUser(String userId);

    /* 曖昧なユーザ名によりヒットするユーザ名リスト取得 */
    List<UserEntity> getUserByAmbiguousName(String userName);

}