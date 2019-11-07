<%--
  Created by IntelliJ IDEA.
  User: Arkenstone
  Date: 10.10.2019
  Time: 5:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Access denied</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
</head>
<body>
<div class="alert alert-danger text-center" role="alert">
    <h2>You don't have permission to access this page. Try to login!</h2>
</div>
<div class="text-center">
    <script type="text/javascript">
        var count = 4;

        function countDown() {
            var timer = document.getElementById("timer");
            if (count > 1) {
                count--;
                timer.innerHTML = "This page will redirect you to login page in <b>" + count + "</b> seconds.";
                setTimeout("countDown()", 1000);
            } else {
                window.location.href = "index.jsp"
            }
        }
    </script>
    <span id="timer">
<script type="text/javascript">countDown();</script>
</span>
</div>
</body>
</html>
