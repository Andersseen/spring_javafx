package com.spring_javafx.spring_javafx.models.user;

import com.spring_javafx.spring_javafx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDaoImp implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserVo> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserVo getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
