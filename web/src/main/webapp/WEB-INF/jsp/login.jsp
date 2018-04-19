<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewpoint" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="../../css/login.css">
</head>
<body>
<div class="grandParentContainer">
    <div class="parentContainer">
        <fieldset class="legend form">
            <legend><h1 class="legend-text">Social Network</h1></legend>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <label for="email">Email</label><br/>
                <input type="email" name="email" id="email" value="${email}"/><br/>
                <label for="password">Password</label><br/>
                <input type="password" name="password" id="password" value="${password}"/><br/>
                <label>
                    <input type="checkbox" name="remember" checked value="true"${remember}> Remember me
                </label>
                <button class="btn">Login</button>
                <br/>
            </form>
            <form action="${pageContext.request.contextPath}/registration">
                <button class="btn">Registration</button>
            </form>
            <br/>
            <p class="errMsg">${errMsg}</p>
        </fieldset>
    </div>
</div>
</body>
</html>