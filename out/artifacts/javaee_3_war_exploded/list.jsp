<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--TODO protection -->
<jsp:useBean id="listOfUsers" scope="session" type="java.util.ArrayList"/>
<jsp:useBean id="listOfUsersDB" scope="session" type="java.util.ArrayList"/>
<%@ page import="model.User" %>
<%@ page import="model.Users, java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Arkenstone
  Date: 10.10.2019
  Time: 5:53
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>List</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <script src="resources/jquery.js"></script>
    <c:if test="${error != null}">
        ${error = null}
    </c:if>
    <c:if test="${login == null}">
        <script>window.location.href = 'access-denied.jsp'</script>
    </c:if>
    <c:if test="${login != null}">
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!-- TODO ????????????????????????????? -->
    </c:if>

</head>
<body>
<div style="text-align: center;">
    <nav class="navbar sticky-top navbar-light bg-light" style="background-color: #e3f2fd; height: 50px" size="auto">
        <form class="form-inline">
                <a class="btn btn-outline-success btn-sm" href="<c:url value="/mylist"/>">Refresh list</a>
        </form>
        <form class="form-inline">
            <a class="navbar-brand" style="font-size: 15px">You have logged in as user</a>
            <button class="btn btn-outline-success btn-sm" type="button" style="margin-left: 5px">Log out</button>
        </form>
    </nav>
    <table class="table table-striped">
        <thead>
        <tr class="table-success">
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Surname</th>
            <th scope="col">Phone number</th>
            <th scope="col">Email</th>
            <th scope="col">Registration</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listOfUsersDB}" var="user">
            <tr>
                <th scope="row">
                    <c:out value="${user.id}"></c:out>
                </th> <!-- ID -->
                <td>
                    <c:out value="${user.name}"></c:out>
                </td>
                <td>
                    <c:out value="${user.surname}"></c:out>
                </td>
                <td>
                    <c:out value="${user.phoneNumber}"></c:out>
                </td>
                <td>
                    <c:out value="${user.email}"></c:out>
                </td>
                <td>
                    <c:out value="${user.registration}"></c:out>
                </td>
                <td>
                    <c:url value="EditUser" var="C_EditUser">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a class="btn btn-outline-info btn-sm" href="${C_EditUser}" role="button">Edit</a>
                    <c:url value="DeleteUser" var="C_DeleteUser">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a class="btn btn-outline-danger btn-sm" onclick="return confirm('Are you sure?')"
                       role="button" href="${C_DeleteUser}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="AddUser" method="post">
        <input type="submit" class="btn btn-success" value="Add user"/>
    </form>
</div>
</body>
</html>
