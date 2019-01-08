package com.heima.dao.daoimpl;

import com.heima.dao.CategoryDao;
import com.heima.domain.Category;
import com.heima.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> getAllCats() throws SQLException {
        String sql = "select * from category ";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String sql =  "insert into category values (?,?)";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql,category.getCid(),category.getCname());
    }
}
