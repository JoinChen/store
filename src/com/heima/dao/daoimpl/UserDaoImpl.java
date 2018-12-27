package com.heima.dao.daoimpl;

import com.heima.dao.UserDao;
import com.heima.domain.User;
import com.heima.utils.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public void userRegist(User user) throws SQLException {
        String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?) ";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Object [] param = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
        user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
        runner.update(sql,param);
    }

    @Override
    public User userActive(String code) throws SQLException {
        String sql = "select * from user where code = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        User user = runner.query(sql, new BeanHandler<User>(User.class),code);
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "update user set username = ?,password =?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid =?";
        Object [] param = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
                user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql,param);
    }

    @Override
    public User userLogin(User uer) throws SQLException {
        String sql = "select * from user where username = ? and password =?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanHandler<User>(User.class),uer.getUsername(),uer.getPassword());
    }
}
