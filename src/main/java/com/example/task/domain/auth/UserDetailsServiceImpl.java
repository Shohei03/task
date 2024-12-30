package com.example.task.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    //DBからユーザ情報を検索するメソッドを実装したクラス
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return userMapper.findUser(userId)
                .map(
                        user -> new LoginUser(
                                user.getUserId(),
                                user.getPassword(),
                                toGrantedAuthority(user.getRole())
                        )
                )
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Given username is not found. (username = '" + userId + "')"
                        )
                );


    }

    private List<GrantedAuthority> toGrantedAuthority(UserEntity.Authority authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority.name()));
    }



}
