package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findByEmail(String email);
    void save(User user);
}
