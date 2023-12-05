<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>

    <title>Hello, world!</title>
</head>
<body>
    <form action="/todo/register", method="post">
        <dic>
            Title: <input type="text" name="text">
        </dic><br>
        <dic>
            DueDate: <input type="date" name="dueDate" value="2023-12-25">
        </dic><br>
        <dic>
            Writer: <input type="text" name="writer">
        </dic><br>
        <dic>
            Finished: <input type="checkbox" name="finished">
        </dic><br>
        <dic>
            <button type="submit">Register</button>
        </dic><br>
    </form>
</body>
</html>
