<%--
  Created by IntelliJ IDEA.
  User: trq
  Date: 2016/8/30
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/reg.do" method="post">
    <table>
        <tr>
            <td>name</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>password</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="reg"></td>
        </tr>
    </table>
</form>
</body>
</html>
