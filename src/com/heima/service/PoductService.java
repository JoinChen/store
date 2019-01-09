package com.heima.service;

import com.heima.domain.Product;
import com.heima.utils.PageModel;

import java.sql.SQLException;
import java.util.List;

public interface PoductService {

    List<Product> findHots() throws SQLException;

    List<Product> finNews() throws SQLException;

    Product findProByPid(String pid)  throws SQLException;

    PageModel findProductByCidWithPage(String cid, int num) throws SQLException;

    PageModel findAllProductsWithPage(int curNum) throws SQLException;

    void saveProduct(Product product) throws SQLException;
}
