package com.heima.dao;

import com.heima.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {


    List<Product> findHots() throws SQLException;

    List<Product> findNews() throws SQLException;

    Product findProByPid(String pid) throws SQLException;

    int findTotalPage(String cid) throws SQLException;

    List findProducts(int startIndex, int pageSize,int cid) throws SQLException;

    int findTotalRecords() throws SQLException;

    List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException;
}
