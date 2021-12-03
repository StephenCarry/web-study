<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Yaoo
  Date: 2021/12/3
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>now date</title>
</head>
<body>
    <% out.print("Now Date Is "); %>
    <% out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); %>
</body>
</html>
