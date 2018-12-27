package com.heima.service;

import com.heima.domain.User;

import java.sql.SQLException;

public interface UserService {

    void useRegist(User user) throws SQLException;

    boolean userActive(String code) throws SQLException;

    User userLogin(User user) throws SQLException;
}
