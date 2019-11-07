<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Users" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Arkenstone
  Date: 10.10.2019
  Time: 3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login page</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <c:if test="${login== 'success'}">
        <script>window.location.href='mylist'</script>
    </c:if>
</head>
<body>
<div style="text-align: center;">
    <div class="login-container d-flex align-items-center justify-content-center">
        <form class="login-form text-center" method="post">
            <h1 class="mb-5 font-weight-light text-uppercase">Login</h1>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Username" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="Password" name="password">
            </div>
            <button type="submit" class="btn btn-custom btn-block" formaction="/mylist">Login</button>
        </form>
    </div>
    <script src="resources/js/bootstrap.min.js"></script>
</div>
</body>
</html>
