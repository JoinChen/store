package com.heima.dao;

import com.heima.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> getAllCats() throws SQLException;
}
