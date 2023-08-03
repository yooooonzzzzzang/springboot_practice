<%--
  Created by IntelliJ IDEA.
  User: yunji
  Date: 2023/08/01
  Time: 4:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--action: 어디로 method: 어떤 방식 전송--%>
<form action="/calc/makeResult" method="post">
    <input type="number" name="num1">
    <input type="number" name="num2">
    <button type="submit" name="num1">SEND</button>
</form>

</body>
</html>
