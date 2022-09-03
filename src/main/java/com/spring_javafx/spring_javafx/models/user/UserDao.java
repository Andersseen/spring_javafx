package com.spring_javafx.spring_javafx.models.user;

import java.util.List;

public interface UserDao {
    List<UserVo> getUsers();

    UserVo getUser(String username);

}
