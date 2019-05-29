<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2019/4/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .imgCode
        {
            width:112px;
            height:28px;
            position: absolute;
            background:rgba(2,113,117,1);
            border:none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<img id="code_img" src="http://localhost:8080/login/getSecurityImage" class="imgCode" title="点击刷新验证码">
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
    /*切换验证码*/
    $("#code_img").click(function() {
        this.setAttribute('src', 'http://localhost:8080/login/getSecurityImage?' + Math.random());//此处需要随机数触发src的变化，否则不发送请求
    });
</script>
</body>
</html>
