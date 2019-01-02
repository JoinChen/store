package com.heima.dao;

import com.heima.domain.Order;
import com.heima.domain.OrderItem;
import com.heima.domain.User;

import java.sql.Connection;
import java.util.List;

public interface OrderDao {
    int getTotalRecords(User user) throws Exception;

    void saveOrder(Connection connection, Order order) throws Exception;

    void saveOrderItem(Connection connection, OrderItem item) throws Exception;

    List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;
}
