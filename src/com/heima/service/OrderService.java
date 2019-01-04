package com.heima.service;

import com.heima.domain.Order;
import com.heima.domain.User;
import com.heima.utils.PageModel;

public interface OrderService {

    void saveOrder(Order order) throws Exception;

    PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

    Order findOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;
}
