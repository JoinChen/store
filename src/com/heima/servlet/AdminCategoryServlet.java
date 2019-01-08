package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.Category;
import com.heima.service.CategoryService;
import com.heima.service.serviceImp.CategoryServiceImp;
import com.heima.utils.UUIDUtils;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet",urlPatterns = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    public String findAllCats(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //获取全部类信息
        CategoryService service = new CategoryServiceImp();
        List<Category> list = service.getAllCats();
        //request放置信息
        request.setAttribute("allCats",list);
        //转发页面
        return "/admin/category/list.jsp";
    }
    //点击添加按钮实现页面跳转
    public String addCategoryUI(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        return "/admin/category/add.jsp";
    }

    //添加分类
    public String addCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //获取分类名称
        String cname = request.getParameter("cname");
        //创建分类id
        String id = UUIDUtils.getId();
        //调用业务层添加分类
        Category category = new Category();
        category.setCid(id);
        category.setCname(cname);
        CategoryService service = new CategoryServiceImp();
        service.addCategory(category);
        //重定向到查询全部分类信息
        response.sendRedirect("/AdminCategoryServlet?method=findAllCats");
        return null;
    }
}
