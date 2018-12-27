package com.heima.servlet;

import com.heima.base.BaseServlet;
import com.heima.domain.Category;
import com.heima.service.CategoryService;
import com.heima.service.serviceImp.CategoryServiceImp;
import com.heima.utils.JedisUtils;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Jedis jedis = JedisUtils.getJedis();
        String jsonStr = jedis.get("allCats");
        if (null==jsonStr || "".equals(jsonStr)){//jedis中没有数据
            CategoryService categoryService = new CategoryServiceImp();
            List<Category> allCats = categoryService.getAllCats();
            jsonStr = JSONArray.fromObject(allCats).toString();
            jedis.set("allCats",jsonStr);
            System.out.println("jedis缓存中没有数据");
        }
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(jsonStr);
        JedisUtils.closeRedis(jedis);
        return null;
    }
}
