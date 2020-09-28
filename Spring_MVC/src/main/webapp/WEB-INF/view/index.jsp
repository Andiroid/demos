<%--
  Created by IntelliJ IDEA.
  User: andiroid
  Date: 5/12/20
  Time: 12:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="" method="post">
    <input type="text" name="foo">
    <input type="submit">
</form>

<%= request.getAttribute("result")%>
</body>
</html>
