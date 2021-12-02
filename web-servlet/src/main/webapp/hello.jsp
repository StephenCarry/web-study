<%--
  Created by IntelliJ IDEA.
  User: Yaoo
  Date: 2021/3/8
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello-JSP</title>
</head>
<body>
    <h1>Hello World!</h1>
    <p>
        <% out.println("Ip Address Is "); %>
        <span>
            <%=request.getRemoteAddr()%>
        </span>
    </p>
</body>
</html>
