package com.iqy.im.dao;

import com.iqy.im.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    List<User> findAllByIdIn(List<String> ids);
}
