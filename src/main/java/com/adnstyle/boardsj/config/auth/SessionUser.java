package com.adnstyle.boardsj.config.auth;

import com.adnstyle.boardsj.dto.User;
import lombok.Getter;

import java.io.Serializable;
/*
* 소셜로그인 또는 가입자에 대한 세션을 저장
* */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
