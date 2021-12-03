<%--
  Created by IntelliJ IDEA.
  User: Yaoo
  Date: 2021/3/8
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%--jsp内置out,session,request,response--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello-JSP</title>
</head>
<body>
    <h1>Hello World!</h1>
    <p>
        <%@include file="component/date.jsp"%>
    </p>
    <p>
        <% out.println("Ip Address Is "); %>
        <span style="color:red">
            <%=request.getRemoteAddr()%>
        </span>
    </p>
</body>
</html>
