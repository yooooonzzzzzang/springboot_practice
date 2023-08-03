<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Num1: ${param.num1}</h1>
    <h1>Num2: ${param.num2}</h1>
    <h1>Result: ${Integer.parseInt(param.num1) + Integer.parseInt(param.num2)}</h1>
</body>
</html>