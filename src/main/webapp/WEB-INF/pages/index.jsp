<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>111</title>
    <script type="text/javascript">
        var sse = new EventSource("/springtest/pull3.do");
        sse.onmessage = function (e) {
            console.log("收到消息" + e.data);
        };
        sse.onopen = function (e) {
            console.log("open connection");
        };
        sse.onerror = function (e) {
            console.log("error-" + e);
        };
        new Date("")
    </script>
</head>
<body>
<h1>welcome</h1>
<a href="${pageContext.request.contextPath}/login.html">login</a><br/>
<a href="${pageContext.request.contextPath}/reg.html">reg</a>
</body>
</html>