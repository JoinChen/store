package com.heima.service.serviceImp;

import com.heima.dao.CategoryDao;
import com.heima.dao.daoimpl.CategoryDaoImpl;
import com.heima.domain.Category;
import com.heima.service.CategoryService;
import com.heima.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImp implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> getAllCats() throws SQLException {
        return  categoryDao.getAllCats();
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        categoryDao.addCategory(category);
        //更新jedis
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeRedis(jedis);
    }
}
