package com.heima.service.serviceImp;

import com.heima.dao.ProductDao;
import com.heima.dao.daoimpl.ProductDaoImpl;
import com.heima.domain.Product;
import com.heima.service.PoductService;
import com.heima.utils.PageModel;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements PoductService {

    ProductDao productDao = new ProductDaoImpl();
    @Override
    public List<Product> findHots() throws SQLException {
        return productDao.findHots();
    }

    @Override
    public List<Product> finNews() throws SQLException {
        return productDao.findNews();
    }

    @Override
    public Product findProByPid(String pid) throws SQLException {
        return productDao.findProByPid(pid);
    }

    @Override
    public PageModel findProductByCidWithPage(String cid, int num) throws SQLException {
        int totalPage = productDao.findTotalPage(cid);
        PageModel pageModel = new PageModel(num,5,totalPage);
        List list = productDao.findProducts(pageModel.getStartIndex(),pageModel.getPageSize(),Integer.parseInt(cid));
        pageModel.setList(list);
        pageModel.setUrl("ProductServlet?method=findProductByCidWithPage&cid="+cid);
        return  pageModel;
    }
}
