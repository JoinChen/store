<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\12\20 0020
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试三种请求方式</title>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript" ></script>
</head>
<body>
<h6 >form表单调用</h6>
<form action="ServletDemo?method=addStu" method="post">
    用户:<input type="text" name="username"><br>
    <button>提交</button>
</form>
<h6>超链接调用servlet</h6>
<a href="/ServletDemo?method=delStu">删除学生</a><br>
<h6>按钮的点击事件:ajax</h6>
<button onclick="fn()">按钮添加</button><br>
<script >
    function fn() {
        $.post("/ServletDemo",{"method":"checkStu","user":"tom"},function (data) {
            alert(data);
        });
    }
</script>
</body>
</html>
