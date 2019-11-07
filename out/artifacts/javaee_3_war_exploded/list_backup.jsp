<%@ page import="model.*" %>
<%@ page import="servlet.*, java.util.*, java.io.*" %>
<%@ page import="javax.xml.bind.JAXBException" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput" %>
<%@ page import="org.w3c.dom.ls.LSOutput" %><%--
  Created by IntelliJ IDEA.
  User: Arkenstone
  Date: 10.10.2019
  Time: 5:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <script src="resources/jquery.js"></script>
    <script src="resources/bs-confirmation-master/bootstrap-confirmation.min.js"></script>

</head>
<body>
<div style="text-align: center;">
    <table class="table table-striped">
        <thead>
        <tr class="table-success">
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Phone number</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <%List<User> xmlUsers = Users.readFromXml(); %>
        <% if (xmlUsers != null) {
            ;
            for (User user : xmlUsers) { %>
        <tr>
            <th scope="row"><%=user.getId()%>
            </th> <!-- ID -->
            <td><%=user.getName()%>
            </td>
            <td><%=user.getPhoneNumber()%>
            </td>
            <td>
                <a class="btn btn-outline-info btn-sm" href="EditUser?id=<%=user.getId()%>" role="button">Edit</a>
                <!--TODO confirmation -->
                <a class="btn btn-outline-danger btn-sm" href="DeleteUser?id=<%=user.getId()%>"
                   onclick="return confirm('Are you sure?')" role="button">Delete</a>
            </td>
        </tr>
        <% }
        }%>
        </tbody>
    </table>
    <form action="AddUser" method="post">
        <input type="submit" class="btn btn-primary btn-block" value="Add user"/>
    </form>
</div>


</body>
</html>
