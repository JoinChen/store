package com.heima.service.serviceImp;

import com.heima.dao.UserDao;
import com.heima.dao.daoimpl.UserDaoImpl;
import com.heima.domain.User;
import com.heima.service.UserService;

import java.sql.SQLException;

public class UserServiceImp implements UserService {

    @Override
    public void useRegist(User user) throws SQLException {
        //实现注册功能
        UserDao userDao = new UserDaoImpl();
        userDao.userRegist(user);
    }

    @Override
    public boolean userActive(String code) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.userActive(code);
        if (null!=user){
            //改变激活状态和激活码置空
            user.setState(1);
            user.setCode(null);
            //对数据库进行更新
            userDao.updateUser(user);
            return true;
        }else {
            return  false;
        }
    }

    @Override
    public User userLogin(User user) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        User uu = userDao.userLogin(user);
        if (null==uu){
           throw  new RuntimeException("密码错误");
        }else if (uu.getState() == 0){
            throw new RuntimeException("用户未激活!");
        }else {
            return uu;
        }
    }
}
