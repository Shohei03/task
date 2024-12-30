package com.example.task.domain.auth;

import com.example.task.web.user.ConvertUser;
import com.example.task.web.user.UserForm;
import com.example.task.web.user.UserNewRegistForm;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userRepository;

    //@Autowired
    //private MailSender mailSender;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    /* ユーザー一覧用　*/
    public Page<UserEntity> findAllUser(Pageable pageable){
        RowBounds rowBounds = new RowBounds(
                (int)pageable.getOffset(), pageable.getPageSize());
        List<UserEntity> userList = userRepository.getAllUser(rowBounds);

        int total = userRepository.getCount();
        return new PageImpl<>(userList, pageable, total);
    };

    /* ユーザ作成*/
    @Transactional
    public void registUser(UserEntity user){
        userRepository.registUser(user);
    }

    /* 自分のユーザ情報編集 */
    @Transactional
    public void modifyMyInfo(UserForm userForm){
        UserEntity user = new UserEntity();
        ConvertUser convertUser = new ConvertUser();

        if(userForm.getPassword() != null & userForm.getPassword() != "") {
            userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }
        user = convertUser.editFormToEntity(userForm, user);

        // 自分のユーザ情報更新
        userRepository.modifyMyInfo(user);

    };

    /* 対象のユーザ情報を削除 */
    @Transactional
    public void deleteUser(String userId){
        userRepository.deleteUser(userId);
    };

    /* 管理者がユーザ情報編集 */
    @Transactional
    public void modifyUserInfo(UserNewRegistForm userNewRegistForm){
        ConvertUser convertUser = new ConvertUser();

        UserEntity user = convertUser.registFormToEntity(userNewRegistForm);

        // 自分のユーザ情報更新
        userRepository.modifyUserInfo(user);
    };

    /* ユーザテーブルで最大のユーザIDの値を取得して1足す*/
    public String getMaxUserId(){
        String maxUserId = userRepository.getMaxUserId();

        //記号と数値を分ける
        String prefix = maxUserId.substring(0,2);
        String numberPart = maxUserId.substring(2);

        int number = Integer.parseInt(numberPart);
        number += 1;

        maxUserId = prefix + number;

        return maxUserId;
    };

    /* ユーザIDをもとにユーザ情報取得*/
    @Transactional
    public UserEntity getUserById(String id){
        return userRepository.getUserById(id);
    };

//    /* メール送信 */
//    public void sendEmail(UserEntity user){
//        if(user != null) {
//            String token = UUID.randomUUID().toString();
//            user.setToken(token);
//            userRepository.updateToken(user);
//
//            String registUrl = "http://localhost:8080/user/userRegist?token=" + token;
//
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(user.getEmail());
//            message.setSubject("登録依頼");
//            message.setText("click the Linked below:\n" + registUrl);
//
//            mailSender.send(message);
//        }
//    };

    /* トークンよりユーザ情報を取得 */
    public UserEntity getUserByToken(String token){
        return userRepository.getUserByToken(token);
    };

    /* パスワード設定後、トークンの値を削除*/
    @Transactional
    public void deleteToken(String userId, String password){
        userRepository.deleteToken(userId, password);
    }

    /* 曖昧なユーザ名によりユーザ取得 */
    public List<UserEntity> getUserByAmbiguousName(String userName){
        return userRepository.getUserByAmbiguousName(userName);
    }


}
