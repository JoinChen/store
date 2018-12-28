package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.User;
import com.heima.service.UserService;
import com.heima.service.serviceImp.UserServiceImp;
import com.heima.utils.CookUtils;
import com.heima.utils.MailUtils;
import com.heima.utils.MyBeanUtils;
import com.heima.utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/jsp/register.jsp";
    }

    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/login.jsp";
    }

    public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        MyBeanUtils.populate(user, parameterMap);
        System.out.println(user);
        //为用户其他属性赋值
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());
        //业务层注册功能
        UserService userService = new UserServiceImp();
        try {
            userService.useRegist(user);
            request.setAttribute("msg", "用户注册成功,请激活!");
            MailUtils.sendMail(user.getEmail(), user.getCode());
            return "/jsp/info.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "用户注册失败,请重新注册!");
        }
        return "/jsp/info.jsp";
    }

    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String code = request.getParameter("code");
        UserService userService = new UserServiceImp();
        boolean flag = userService.userActive(code);
        if (flag) {
            request.setAttribute("msg", "用户激活成功,请登录!");
            return "/jsp/login.jsp";
        } else {
            request.setAttribute("msg", "用户激活失败,请重新激活!");
            return "/jsp/info.jsp";
        }
    }

    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //获取用户数据
        User user = new User();
        MyBeanUtils.populate(user, request.getParameterMap());
        //调用业务层登录
        UserService userService = new UserServiceImp();
        User user2 = null;
        try {
            //select * from user where username = ? and  password = ?
            user2 = userService.userLogin(user);
            System.out.println(user2);
            request.getSession().setAttribute("loginUser", user2);
            response.sendRedirect("index.jsp");
            return null;//不做重定向转发
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
            request.setAttribute("msg", message);
            return "/jsp/login.jsp";
        }
    }

    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
       //清除session
        request.getSession().invalidate();
        //重定向
        response.sendRedirect("/index.jsp");
        return null;
    }

}
