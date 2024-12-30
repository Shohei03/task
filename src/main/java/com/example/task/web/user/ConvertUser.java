package com.example.task.web.user;

import com.example.task.domain.auth.UserEntity;

public class ConvertUser {

    public UserEntity formToEntity(UserForm userForm) {
        UserEntity user = new UserEntity();
        user.setUserId(userForm.getUserId());
        user.setUserName(namePlus(userForm.getUserNameSei(), userForm.getUserNameMei()));
        user.setEmail(userForm.getEmail());
        user.setRole(UserEntity.Authority.valueOf(userForm.getAuthority()));

        return user;
    }

    public UserEntity registFormToEntity(UserNewRegistForm userRegistForm) {
        UserEntity user = new UserEntity();
        user.setUserId(userRegistForm.getUserId());
        user.setUserName(namePlus(userRegistForm.getUserNameSei(), userRegistForm.getUserNameMei()));
        user.setEmail(userRegistForm.getEmail());
        user.setRole(UserEntity.Authority.valueOf(userRegistForm.getAuthority()));

        return user;
    }



    public UserEntity editFormToEntity(UserForm userForm, UserEntity user) {
        user.setUserId(userForm.getUserId());
        user.setUserName(namePlus(userForm.getUserNameSei(), userForm.getUserNameMei()));
        if(userForm.getPassword() != null) {
            user.setPassword(userForm.getPassword());
        }
        if(userForm.getEmail() != null) {
            user.setEmail(userForm.getEmail());
        }
        if(userForm.getAuthority() != null) {
            user.setRole(UserEntity.Authority.valueOf(userForm.getAuthority()));
        }
        return user;
    }

    public UserNewRegistForm entityToRegistForm(UserEntity user) {
        UserNewRegistForm userForm = new UserNewRegistForm();

        userForm.setUserId(user.getUserId());
        String[] parts = nameMinus(user.getUserName());
        userForm.setUserNameSei(parts[0]);
        userForm.setUserNameMei(parts[1]);
        userForm.setEmail(user.getEmail());
        userForm.setAuthority(user.getRole().name());

        return userForm;
    }

    public UserForm entityToForm(UserEntity user) {
        UserForm userForm = new UserForm();

        userForm.setUserId(user.getUserId());

        String[] parts = nameMinus(user.getUserName());
        userForm.setUserNameSei(parts[0]);
        userForm.setUserNameMei(parts[1]);
        userForm.setPassword(user.getPassword());

        if(user.getEmail() != null) {
            userForm.setEmail(user.getEmail());
        }

        userForm.setAuthority(user.getRole().name());

        return userForm;
    }

    public UserEditForm entityToEditForm(UserEntity user) {
        UserEditForm userEditForm = new UserEditForm();

        userEditForm.setUserId(user.getUserId());

        String[] parts = nameMinus(user.getUserName());
        userEditForm.setUserNameSei(parts[0]);
        userEditForm.setUserNameMei(parts[1]);
        userEditForm.setPassword(user.getPassword());

        userEditForm.setAuthority(user.getRole().name());

        return userEditForm;
    }



    private String namePlus(String sei, String mei) {
        return sei + "　" + mei;
    }

    private String[] nameMinus(String name) {
        String[] parts = name.split("\u3000");
        return parts;
    }
}
