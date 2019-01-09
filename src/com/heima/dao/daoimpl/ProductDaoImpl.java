package com.heima.dao.daoimpl;

import com.heima.dao.ProductDao;
import com.heima.domain.Product;
import com.heima.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import sun.security.provider.certpath.CertId;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> findHots() throws SQLException {
        String sql = "SELECT * FROM product WHERE  is_hot=0 ORDER BY pdate DESC LIMIT 0,9;";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findNews() throws SQLException {
        String sql = "SELECT * FROM product WHERE  pflag=0 ORDER BY pdate DESC LIMIT 0,9;";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Product>(Product.class));
    }

    @Override
    public Product findProByPid(String pid) throws SQLException {
        String sql = "SELECT * FROM product WHERE  pid =?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(Product.class),pid);
    }

    @Override
    public int findTotalPage(String cid) throws SQLException {
        String sql = "select count(*) from product where cid = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Long aLong = (Long) runner.query(sql, new ScalarHandler(),cid);
        return aLong.intValue();
    }

    @Override
    public List findProducts(int startIndex, int pageSize,int cid) throws SQLException {
        String sql = "select * from product where cid = ? limit  ?,?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
    }

    @Override
    public int findTotalRecords() throws SQLException {
        String sql = "select count(*) from product";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Long aLong = (Long) runner.query(sql, new ScalarHandler());
        return aLong.intValue();
    }

    @Override
    public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException {
        String sql = "select * from product order by pdate desc limit  ?,?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Product>(Product.class),startIndex,pageSize);
    }

    @Override
    public void saveProduct(Product product) throws SQLException {
        String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Object [] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
                product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
        runner.update(sql,params);
    }
}
