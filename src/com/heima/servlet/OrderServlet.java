package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.*;
import com.heima.service.OrderService;
import com.heima.service.serviceImp.OrderServiceImp;
import com.heima.utils.PageModel;
import com.heima.utils.UUIDUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "OrderServlet",urlPatterns ="/OrderServlet" )
public class OrderServlet extends BaseServlet {
    //购物车信息以订单形式保存
    public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //确认用户登录状态
        User user = (User) req.getSession().getAttribute("loginUser");
        if (null==user){
            req.setAttribute("msg","请登录之后再下单");
            return "/jsp/info.jsp";
        }
        Order order = new Order();
        //获取购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        order.setOid(UUIDUtils.getCode());
        order.setUser(user);
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setState(1);
        //创建订单对象,为订单对象复制
        for (CartItem item  :  cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemid(UUIDUtils.getCode());
            orderItem.setQuantity(item.getNum());
            orderItem.setTotal(item.getSubTotal());
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            order.getList().add(orderItem);
        }
        //调用业务层保存订单
        OrderService orderService = new OrderServiceImp();
        orderService.saveOrder(order);
        //清空购物车
        cart.clearCart();
        //订单放入request
        req.setAttribute("order",order);
        //转发jsp/order_info.jsp
        return "/jsp/order_info.jsp";
    }


    public String findMyOrdersWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        int curNum = Integer.parseInt(req.getParameter("num"));
        OrderService orderService = new OrderServiceImp();
        //select * from orders where uid = ? limit ?,?;
        //PageModel 1.分页参数 2.url 3.当前用户的当前页的订单(集合),每笔订单上的订单项,以及订单项对应商品信息
        PageModel pageModel = orderService.findMyOrdersWithPage(user,curNum);
        req.setAttribute("page",pageModel);
        return "/jsp/order_list.jsp";
    }
}
