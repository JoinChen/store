<%@ page import="javafx.scene.control.Alert" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.mail.Session" %>
<%@ page import="com.heima.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\12\24 0024
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("loginUser");
    String username = null;
    if (null != user) {
        username = user.getUsername();
    }
%>

<div class="container-fluid">
    <div class="col-md-4">
        <img src="${pageContext.request.contextPath}/img/logo2.png"/>
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png"/>
    </div>
    <div class="col-md-3" style="padding-top:20px">

        <ol class="list-inline">
            <% if (null == session.getAttribute("loginUser")) {
            %>
            <li><a href="${pageContext.request.contextPath}/jsp/login.jsp">登录</a></li>
            <li><a href="${pageContext.request.contextPath}/UserServlet?method=registUI">注册</a></li>
            <%
                }%>

            <% if (session.getAttribute("loginUser") != null) {
            %>
            <li>欢迎<%=username%>
            </li>
            <li><a href="${pageContext.request.contextPath}/UserServlet?method=logOut">退出</a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a></li>
            <li><a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrdersWithPage&num=1">我的订单</a></li>
            <%
                }%>
        </ol>
    </div>
</div>

<!--
   描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="myUL">
                    <%--<li class="active"><a--%>
                    <%--href="${pageContext.request.contextPath}/jsp/product_list.jsp">手机数码<span--%>
                    <%--class="sr-only">(current)</span></a></li>--%>
                    <%--<li><a href="#">电脑办公</a></li>--%>
                    <%--<li><a href="#">电脑办公</a></li>--%>
                    <%--<c:forEach items="${allcats}" var="c">--%>
                    <%--<li><a href="#">${c.cname}</a></li>--%>
                    <%--</c:forEach>--%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
</body>
<script>
    var url = "/CategoryServlet";
    var obj = {"method": "findAllCats"};
    $(function () {
        $.post(url, obj, function (data) {
            $.each(data, function (i, obj) {
                var li = "<li><a href='/ProductServlet?method=findProductByCidWithPage&num=1&cid=" + obj.cid + "'>" + obj.cname + "</a></li>";
                $("#myUL").append(li);
            });
        }, "json");
    });
</script>
</html>
