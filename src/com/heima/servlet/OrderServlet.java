package com.heima.servlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.heima.base.BaseServlet;
import com.heima.domain.*;
import com.heima.service.OrderService;
import com.heima.service.serviceImp.OrderServiceImp;
import com.heima.utils.AlipayConfig;
import com.heima.utils.PageModel;
import com.heima.utils.PaymentUtil;
import com.heima.utils.UUIDUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "OrderServlet",urlPatterns ="/OrderServlet" )
public class OrderServlet extends BaseServlet {
    //购物车信息以订单形式保存
    public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //确认用户登录状态
        User user = (User) req.getSession().getAttribute("loginUser");
        if (null == user) {
            req.setAttribute("msg", "请登录之后再下单");
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
        for (CartItem item : cart.getCartItems()) {
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
        //订单放入req
        req.setAttribute("order", order);
        //转发jsp/order_info.jsp
        return "/jsp/order_info.jsp";
    }


    public String findMyOrdersWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        int curNum = Integer.parseInt(req.getParameter("num"));
        OrderService orderService = new OrderServiceImp();
        //select * from orders where uid = ? limit ?,?;
        //PageModel 1.分页参数 2.url 3.当前用户的当前页的订单(集合),每笔订单上的订单项,以及订单项对应商品信息
        PageModel pageModel = orderService.findMyOrdersWithPage(user, curNum);
        req.setAttribute("page", pageModel);
        return "/jsp/order_list.jsp";
    }

    public String findOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String oid = req.getParameter("oid");
        OrderService orderService = new OrderServiceImp();
        Order order = orderService.findOrderByOid(oid);
        req.setAttribute("order", order);
        return "/jsp/order_info.jsp";
    }

    public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String oid = req.getParameter("oid");
        String address = req.getParameter("address");
        String name = req.getParameter("name");
        String telephone = req.getParameter("telephone");
//        String pd_FrpId = req.getParameter("pd_FrpId");
        //更新订单上收货人信息
        OrderService orderService = new OrderServiceImp();
        Order order = orderService.findOrderByOid(oid);
        order.setName(name);
        order.setAddress(address);
        order.setTelephone(telephone);
        orderService.updateOrder(order);
        //向易宝支付传递参数
        // 把付款所需要的参数准备好:

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = oid;
        //付款金额，必填
        String total_amount = "0.01";
        //订单名称，必填
        String subject = "支付宝PC测试";
        //商品描述，可空
        String body = "商品描述";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println("支付结果?" + result);
            resp.setContentType("text/html;charset=" + AlipayConfig.charset);
            resp.getWriter().write(result);//直接将完整的表单html输出到页面
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            resp.getWriter().write("捕获异常出错");
            resp.getWriter().flush();
            resp.getWriter().close();
        }
        return null;
    }

    public String callBack(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(req.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            OrderService orderService = new OrderServiceImp();
            Order order = orderService.findOrderByOid(out_trade_no);
            order.setState(2);
            orderService.updateOrder(order);
            // 浏览器重定向
            req.setAttribute("msg","支付成功"+"<br/>"+"trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
            req.getRequestDispatcher("/jsp/info.jsp").forward(req,resp);

        }else {
            req.setAttribute("msg","验签失败");
            req.getRequestDispatcher("/jsp/info.jsp").forward(req,resp);
        }
        return null;
    }
}
