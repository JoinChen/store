package com.heima.service;

import com.heima.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCats() throws SQLException;

    void addCategory(Category category) throws SQLException;
}
