package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {

    /*
    *소셜가입자 리스트 조회
    * */
    List<User> listUser();

    /*
    * 소셜가입 유저인지 확인
    * */
    User findByEmail(String email);

    /*
    * 소셜회원가입 처리
    * */
    void save(User user);
}
