package com.heima.servlet;

import com.heima.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletDemo",urlPatterns = "/ServletDemo")
public class ServletDemo extends BaseServlet {

    public ServletDemo() {
        System.out.println("无参构造函数");
    }

    public String addStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("添加学生");
        return "/test.jsp";
    }

    public String delStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("删除学生");
        return "/test.jsp";
    }

    public String checkStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("检查学生");
        response.getWriter().print("测试");
        return null;
    }
}
