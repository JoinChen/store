package com.heima.servlet;

import com.alipay.api.domain.PreOrderResult;
import com.heima.base.BaseServlet;
import com.heima.service.PoductService;
import com.heima.service.serviceImp.ProductServiceImpl;
import com.heima.utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminProductServlet",urlPatterns = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

    public String findAllProductsWithPage(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //获取当前页
        int curNum = Integer.parseInt(request.getParameter("num"));
        //调用业务层查询全部商品信息返回pageModel
        PoductService service = new ProductServiceImpl();
        PageModel pageModel = service.findAllProductsWithPage(curNum);
        //将pagemodel放入request
        request.setAttribute("page",pageModel);
        //转发页面到admin/product/list.jsp
        return "admin/product/list.jsp";
    }
}
