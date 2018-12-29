package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.Product;
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
import java.util.List;

@WebServlet(name = "ProductServlet",urlPatterns = "/ProductServlet")
public class ProductServlet extends BaseServlet {

    public String findProByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String pid = request.getParameter("pid");
        PoductService poductService = new ProductServiceImpl();
        Product product = poductService.findProByPid(pid);
        request.setAttribute("product",product);
//        System.out.println(list.toString());
        return "/jsp/product_info.jsp";
    }

    public String findProductByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String cid = request.getParameter("cid");
        String num = request.getParameter("num");
        int currentNum = Integer.parseInt(num);
        PoductService poductService=new ProductServiceImpl();
        //调用业务层查询当前页功能,返回PageModel对象(1_当前页数据2_分页参数)
        PageModel pm=null;
        try {
            pm = poductService.findProductByCidWithPage(cid,currentNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将PageModel对象放入request
        request.setAttribute("page", pm);
        return "/jsp/product_list.jsp";
    }

}
