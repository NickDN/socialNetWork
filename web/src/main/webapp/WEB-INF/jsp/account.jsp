<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Account</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../../css/account.css">
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
   <%-- <link rel="stylesheet" href="../../bootstrap/css/bootstrap-theme.min.css">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../../bootstrap/js/bootstrap.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp">
    <jsp:param name="account" value="${account}"/>
</jsp:include>
<p class="nsw">Page</p>
</body>
</html>