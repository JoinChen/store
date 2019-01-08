package com.heima.filter;

import com.heima.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "PrivilegeFilter",urlPatterns = {"/jsp/cart.jsp","/jsp/order_info.jsp","/jsp/order_list.jsp"})
public class PrivilegeFilter implements Filter {

    public PrivilegeFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req1 = (HttpServletRequest) req;
        User user = (User) req1.getSession().getAttribute("loginUser");
        if (null!=user){
            chain.doFilter(req,resp);
        }else {
            req.setAttribute("msg","用户未登陆,请登录后重试");
            req.getRequestDispatcher("/jsp/info.jsp").forward(req,resp);
        }
//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
