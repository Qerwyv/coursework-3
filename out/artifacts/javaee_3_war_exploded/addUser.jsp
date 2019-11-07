<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Arkenstone
  Date: 16.10.2019
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<c:if test="${login== null}">
    <script>window.location.href = 'access-denied.jsp'</script>
</c:if>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add user</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
</head>
<body>
<form action="AddUser">
    <div class="justify-content-center">
        <div class="col-md-4 mb-3">
            <label for="validationServer01">Name</label>
            <input autofocus type="text" class="form-control" name="name" id="validationServer01" placeholder="Name"
                   value="${newUser.name}" required>
        </div>
        <br>
        <div class="col-md-4 mb-3">
            <label for="validationServer03">Surname</label>
            <input autofocus type="text" class="form-control" name="surname" id="validationServer03"
                   placeholder="Surname"
                   value="${newUser.surname}" required>
        </div>
        <br>
        <div class="col-md-4 mb-3">
            <label for="validationServer02">Phone number</label>
            <c:if test="${phoneNumberExists == null}">
                <input type="text" class="form-control" name="phoneNumber" pattern="[+][0-9]{7,15}"
                       id="validationServer02"
                       oninvalid="this.setCustomValidity('Your input does not match field requirements')"
                       oninput="this.setCustomValidity('')"
                       placeholder="Phone number (e.g. +380951234567)" value="${newUser.phoneNumber}" required>
            </c:if>
            <c:if test="${phoneNumberExists != null}">
                <input autofocus type="text" class="form-control is-invalid" name="phoneNumber" pattern="[+][0-9]{7,15}"
                       id="validationServer02"
                       placeholder="Phone number (e.g. +380951234567)" value="${phoneNumberExists}"
                       required> <!--incorrect email -->
                <div class="invalid-feedback">
                    This phone number is already registered
                </div>
            </c:if>
        </div>
        <br>
        <div class="col-md-4 mb-3">
            <label for="validationServer04">Email</label>
            <c:if test="${emailExists == null}">
                <input type="email" class="form-control" name="email"
                       id="validationServer04"
                       oninvalid="this.setCustomValidity('Your input does not match field requirements')"
                       oninput="this.setCustomValidity('')"
                       placeholder="Email address (e. g. sample@mail.com)" value="${newUser.email}" required>
            </c:if>
            <c:if test="${emailExists != null}">
                <input autofocus type="email" class="form-control is-invalid" name="email"
                       id="validationServer04"
                       placeholder="Email address (e. g. sample@mail.com)" value="${emailExists}" required>
                <div class="invalid-feedback">
                    This email address is already registered
                </div>
            </c:if>
        </div>
        <br>
        <div style="text-align: center;">
            <input type="submit" class="btn btn-outline-primary" formmethod="post" value="Add">
        </div>
    </div>
</form>
</body>
</html>
