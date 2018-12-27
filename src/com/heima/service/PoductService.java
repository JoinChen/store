package com.heima.service;

import com.heima.domain.Product;
import com.heima.utils.PageModel;

import java.sql.SQLException;
import java.util.List;

public interface PoductService {

    List<Product> findHots() throws SQLException;

    List<Product> finNews() throws SQLException;

    List<Product> findProByPid(String pid)  throws SQLException;;

    PageModel findProductByCidWithPage(String cid, int num) throws SQLException;

}
