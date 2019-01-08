package com.heima.servlet;

import com.alipay.api.domain.PreOrderResult;
import com.heima.base.BaseServlet;
import com.heima.domain.Category;
import com.heima.service.CategoryService;
import com.heima.service.PoductService;
import com.heima.service.serviceImp.CategoryServiceImp;
import com.heima.service.serviceImp.ProductServiceImpl;
import com.heima.utils.PageModel;
import com.heima.utils.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminProductServlet",urlPatterns = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

    public String findAllProductsWithPage(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //获取当前页
        int curNum = Integer.parseInt(request.getParameter("num"));
        //调用业务层查询全部商品信息返回pageModel
        PoductService service = new ProductServiceImpl();
        PageModel pageModel = service.findAllProductsWithPage(curNum);
        //将pagemodel放入request
        request.setAttribute("page",pageModel);
        //转发页面到admin/product/list.jsp
        return "admin/product/list.jsp";
    }


    public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        CategoryService service = new CategoryServiceImp();
        //获取全部分类信息
        List<Category> allCats = service.getAllCats();
        //存到request
        req.setAttribute("allCats",allCats);
        //转发到页面
        return "/admin/product/add.jsp";
    }

    public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Map<String,String> map = new HashMap<String, String>();
        try {
            //内部原理:利用req.getInputstream;获取到请求提中全部数据,进行拆分和封装
            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            List<FileItem> list = upload.parseRequest(req);
            //遍历集合
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()){//普通项
                    map.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
                }else {
                    //获取要保存文件名称
                    //获取原始文件名称
                    String oldFileName = fileItem.getName();
                    String newFileName = UploadUtils.getUUIDName(oldFileName);

                    InputStream inputStream = fileItem.getInputStream();
                    String realPath = getServletContext().getRealPath("/products/3");
                    System.out.println("realpath:"+realPath);
                    String dir = UploadUtils.getDir(newFileName);
                    //生成/f/d/e/a/b/c/d/4
                    String path = realPath + dir;
                    System.out.println("path:"+path);
                    //内存中声明一个目录
                    File newDir = new File(path);
                    if (!newDir.exists()){
                        newDir.mkdirs();
                    }
                    //服务器创建一个空文件(后缀必须和上传服务器的文件后缀一致
                    //在newdir目录下创建名称为newFileName的文件
                    File finalFile = new File(newDir,newFileName);
                    if (!newDir.exists()){
                        newDir.createNewFile();
                    }
                    //创建空文件对应的输出流
                    OutputStream outputStream = new FileOutputStream(finalFile);
                    //输入流文件刷到输出流中
                    IOUtils.copy(inputStream,outputStream);
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                    //向map中存入一个键值对map
                    System.out.println("最终:"+"/products/3"+dir+"/"+finalFile);
                    map.put("pimage",dir+"/"+finalFile);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //转发到页面
        return null;
    }
}
