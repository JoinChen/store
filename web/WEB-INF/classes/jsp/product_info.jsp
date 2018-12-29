﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品详情信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>

<%@ include file="/jsp/header.jsp" %>

<div class="container">
    <form  action="${pageContext.request.contextPath}/CartServlet?method=addCartItemToCart" method="post" id="myForm">
        <div class="row">
            <div style="border: 1px solid #e4e4e4;width:930px;margin-bottom:10px;margin:0 auto;padding:10px;margin-bottom:10px;">
                <a href="${pageContext.request.contextPath}/">首页</a>
            </div>
            <%--<c:forEach items="${product}" var="pro" >--%>
                <div style="margin:0 auto;width:950px;">

                    <div class="col-md-6">
                        <img src="${pageContext.request.contextPath}/${product.pimage}" style="width:400px;height:350px;" class="medium">
                    </div>

                    <div class="col-md-6">
                        <div><strong>${pro.pid}</strong></div>
                        <div style="border-bottom: 1px dotted #dddddd;width:350px;margin:10px 0 10px 0;">
                            <div>编号：${pro.pid}</div>
                        </div>

                        <div style="margin:10px 0 10px 0;">商城价: <strong style="color:#ef0101;">￥：${product.shop_price}元/份</strong> 市场价： <del>￥${product.market_price}元/份</del>
                        </div>

                        <div style="margin:10px 0 10px 0;">促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)" style="background-color: #f07373;">限时抢购</a> </div>

                        <div style="padding:10px;border:1px solid #e7dbb1;width:330px;margin:15px 0 10px 0;;background-color: #fffee6;">
                            <div style="margin:5px 0 10px 0;">白色</div>

                            <div style="border-bottom: 1px solid #faeac7;margin-top:20px;padding-left: 10px;">购买数量:

                                <input id="quantity" name="num" value="1" maxlength="4" size="10" type="text">
                                <input name="pid" value="${product.pid}" type="hidden">
                                <input name="ranStr" value="${ranStr}" type="hidden">

                            </div>

                            <div style="margin:20px 0 10px 0;;text-align: center;">
                                    <%--加入到购物车 --%>
                                <a href="javascript:void(0)">
                                    <input id="btnId" style="background: url('${pageContext.request.contextPath}/img/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0);height:36px;width:127px;" value="加入购物车" type="button">
                                </a> &nbsp;收藏商品</div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
                <div style="width:950px;margin:0 auto;">
                    <div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
                        <strong>商品介绍</strong>
                        <span>${product.pdesc}</span>
                    </div>
                </div>
            <%--</c:forEach>--%>

        </div>
    </form>
</div>

<%@include file="footer.jsp"%>

</body>
<
<script>
    $(function () {
        $("#btnId").click(function () {
            var formObj = document.getElementById("myForm");
            //不设置一下内容,还是按照原来表单的路径提交
            // formObj.action="/CartServlet";
            // formObj.method="get";
            formObj.submit();
        });
    });
</script>
</html>