package com.heima.service.serviceImp;

import com.heima.dao.CategoryDao;
import com.heima.dao.daoimpl.CategoryDaoImpl;
import com.heima.domain.Category;
import com.heima.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImp implements CategoryService {
    @Override
    public List<Category> getAllCats() throws SQLException {
        CategoryDao categoryDao = new CategoryDaoImpl();
        return  categoryDao.getAllCats();
    }
}
