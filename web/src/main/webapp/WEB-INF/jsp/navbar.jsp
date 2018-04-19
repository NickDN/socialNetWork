<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Account</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../../css/account.css">
    <link rel="stylesheet" href="../../css/glyphicons.css">
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <p class="navbar-brand align-middle nsw-font" style="color: #42a5f5;">Social network</p>
    <ul class="nav justify-content-end">
        <li class="nav-item"><a class="nav-link tnr nsw-right-padding" href="${pageContext.request.contextPath}/update">
            <span class="glyphicon glyphicon-wrench nsw-color nsw-padding"></span>Edit
        </a></li>
        <li class="nav-item"><a class="nav-link tnr nsw-right-padding" href="${pageContext.request.contextPath}/account">
            <span class="glyphicon glyphicon-user nsw-color nsw-padding"></span>${account.firstName}
        </a></li>
        <li class="nav-item"><a class="nav-link tnr nsw-right-padding" href="${pageContext.request.contextPath}/logout">
            <span class="glyphicon glyphicon-log-out nsw-color nsw-padding"></span>Logout
        </a></li>
    </ul>
</nav>
</body>
</html>
