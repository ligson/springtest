<%--
  Created by IntelliJ IDEA.
  User: trq
  Date: 2016/8/26
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
    <table>
        <tr>
            <td><label>user</label></td>
            <td>
                <input type="text" name="j_username"/>
            </td>
        </tr>
        <tr>
            <td><label>pwd</label></td>
            <td>
                <input type="password" name="j_password"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="login"/></td>
            <td></td>
        </tr>
    </table>
</form>
</body>
</html>
