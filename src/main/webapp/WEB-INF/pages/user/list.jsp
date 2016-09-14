<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trq
  Date: 2016/8/26
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <c:forEach items="${users}" var="user">
        <li>${user.username}--------${user.id}</li>
    </c:forEach>
</ul>

<a href="${pageContext.request.contextPath}/j_spring_security_logout">logout</a>

</body>
</html>
