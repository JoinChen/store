package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.Category;
import com.heima.domain.Product;
import com.heima.domain.User;
import com.heima.service.CategoryService;
import com.heima.service.PoductService;
import com.heima.service.UserService;
import com.heima.service.serviceImp.CategoryServiceImp;
import com.heima.service.serviceImp.ProductServiceImpl;
import com.heima.service.serviceImp.UserServiceImp;
import com.heima.utils.MyBeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IndexServlet",urlPatterns = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
       //获取全部的分类信息
//        CategoryService categoryService = new CategoryServiceImp();
//        List<Category> list =  categoryService.getAllCats();
//        req.setAttribute("allcats",list);

        //查询最新商品和最热商品
        PoductService productService = new ProductServiceImpl();
        List<Product> list01 = productService.findHots();
        List<Product> list02 = productService.finNews();
        req.setAttribute("hots",list01);
        req.setAttribute("news",list02);
        return "/jsp/index.jsp";
    }
}
