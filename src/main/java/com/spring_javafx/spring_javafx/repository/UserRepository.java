package com.spring_javafx.spring_javafx.repository;

import com.spring_javafx.spring_javafx.models.user.UserVo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserVo, Integer> {

    UserVo findByUsername(String username);

    @Override
    List<UserVo> findAll();
}
