package com.heima.service.serviceImp;

import com.heima.dao.OrderDao;
import com.heima.dao.daoimpl.OrderDaoImp;
import com.heima.domain.Order;
import com.heima.domain.OrderItem;
import com.heima.domain.User;
import com.heima.service.OrderService;
import com.heima.utils.JDBCUtils;
import com.heima.utils.PageModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImp implements OrderService {
    OrderDao orderDao = new OrderDaoImp();

    @Override
    public void saveOrder(Order order) throws Exception {
        //保存订单和订单下所有订单项(事物同时成功和失败)
//        try {
//            JDBCUtils.startTransaction();
//            OrderDao orderDao = new OrderDaoImp();
//            orderDao.saveOrder(order);
//            for (OrderItem item : order.getList() ) {
//                orderDao.saveOrder(item);
//            }
//        } catch (Exception e) {
//            JDBCUtils.rollbackAndClose();
//        }


        //第二种方式
        Connection connection = null;
        try {
            //获取链接
            connection = JDBCUtils.getConnection();
            //开启事务
            connection.setAutoCommit(false);
            //保存订单
            orderDao.saveOrder(connection, order);
            //保存订单项
            for (OrderItem item : order.getList()) {
                orderDao.saveOrderItem(connection, item);
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            System.out.println(e.getMessage().toString());
        }
    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
        //1.PageModel对象,计算并携带分页参数
        //2.关联集合
        //3.关联url
        int totalRecords = orderDao.getTotalRecords(user);
        PageModel pageModel = new PageModel(curNum, 3, totalRecords);
        List list = orderDao.findMyOrdersWithPage(user,pageModel.getStartIndex(),pageModel.getPageSize());
        pageModel.setList(list);
        pageModel.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pageModel;
    }
}
