package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.Cart;
import com.heima.domain.CartItem;
import com.heima.domain.Category;
import com.heima.domain.Product;
import com.heima.service.CategoryService;
import com.heima.service.PoductService;
import com.heima.service.serviceImp.CategoryServiceImp;
import com.heima.service.serviceImp.ProductServiceImpl;
import com.heima.utils.JedisUtils;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {

    public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null==cart){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }

        String pid = req.getParameter("pid");
        int quantity = Integer.parseInt(req.getParameter("num"));

        //通过pid查询商品相关信息
        PoductService poductService = new ProductServiceImpl();
        Product product = poductService.findProByPid(pid);
        //获取待购买的购物项
        CartItem cartItem = new CartItem();
        cartItem.setNum(quantity);
        cartItem.setProduct(product);
        //调用购物车的方法
        cart.addCartItemToCar(cartItem);
        resp.sendRedirect("/jsp/cart.jsp");
        return null;
    }

    public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        //获取删除商品pid
        String pid = req.getParameter("id");
        //获取购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //调用购物车删除购物项方法
        cart.removeCartItem(pid);
        resp.sendRedirect("/jsp/cart.jsp");
        return null;
    }

    public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        cart.clearCart();
        resp.sendRedirect("jsp/cart.jsp");
        return null;
    }

}
