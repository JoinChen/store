package com.heima.dao.daoimpl;

import com.heima.dao.OrderDao;
import com.heima.domain.Order;
import com.heima.domain.OrderItem;
import com.heima.domain.Product;
import com.heima.domain.User;
import com.heima.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImp implements OrderDao {

    @Override
    public int getTotalRecords(User user) throws Exception {
        String sql = "select count(*) from orders where uid = ? ";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) runner.query(sql, new ScalarHandler(), user.getUid());
        return num.intValue();
    }

    @Override
    public void saveOrder(Connection connection, Order order) throws Exception {
        String sql = "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
                order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
        runner.update(connection,sql,params);
    }

    @Override
    public void saveOrderItem(Connection connection, OrderItem item) throws Exception {
        String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
        runner.update(connection,sql,params);
    }

    @Override
    public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
        String sql = "select * from orders where uid = ? limit ?,?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
        //遍历订单
        for (Order order : list) {
            //获取订单id 每笔订单下的订单项以及订单项对应的商品
            String oid = order.getOid();
            sql = "SELECT * FROM orderitem o,product p WHERE o.`pid`=p.`pid` AND o.`oid`=?";
            List<Map<String, Object>> list02 = runner.query(sql, new MapListHandler(), oid);
            //遍历list
            for (Map<String, Object> map : list02) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                //时间类型转换器
                DateConverter dt = new DateConverter();
                dt.setPattern("yyyy-MM-dd");
                ConvertUtils.register(dt, Date.class);
                //将map中属于orderItem的数据自动填充到Orderitem上
                BeanUtils.populate(orderItem,map);
                BeanUtils.populate(product,map);
                orderItem.setProduct(product);
                //将查询的订单项放入订单集合中
                order.getList().add(orderItem);
            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        String sql = "select * from orders where oid = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Order order =  runner.query(sql,new BeanHandler<>(Order.class),oid);
        //根据订单id查询id下所有商品及其商品信息
        sql = "SELECT * FROM orderitem o,product p WHERE o.`pid`=p.`pid` AND o.`oid`=?";
        List<Map<String, Object>> list03 = runner.query(sql, new MapListHandler(), oid);
        //遍历list
        for (Map<String, Object> map : list03) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            //时间类型转换器
            DateConverter dt = new DateConverter();
            dt.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dt, Date.class);
            //将map中属于orderItem的数据自动填充到Orderitem上
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);
            orderItem.setProduct(product);
            //将查询的订单项放入订单集合中
            order.getList().add(orderItem);
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        String sql = "update orders set ordertime = ?,total = ?,state = ?,address = ?,name = ?,telephone = ? where oid= ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
        runner.update(sql,params);
    }
}
