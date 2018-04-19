<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewpoint" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="../../css/registration.css">
    <link rel="stylesheet" href="../../css/glyphicons.css">
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../../bootstrap/js/bootstrap.js"></script>
</head>
<body>

<div class="container">
    <br/>
    <hr/>
    <div class="card bg-light">
        <article class="card-body mx-auto" style="max-width: 400px;">
            <h4 class="card-title mt-3 text-center nsw-font">Registration</h4>
            <form action="${pageContext.request.contextPath}/registration" method="post">
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="glyphicon glyphicon-user"></i> </span>
                    </div>
                    <input name="firstName" class="form-control nsw-tnr" placeholder="First name" type="text"
                           value="${account.firstName}">
                    <input name="secondName" class="form-control nsw-tnr" placeholder="Second name" type="text"
                           value="${account.secondName}">
                </div>

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="glyphicon glyphicon-gift"></i> </span>
                    </div>
                    <input name="dateOfBirth" class="form-control nsw-tnr" placeholder="Birthday" type="date"
                           value="${account.dateOfBirth}">
                </div>
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="glyphicon glyphicon-envelope"></i> </span>
                    </div>
                    <input name="email" class="form-control nsw-tnr" placeholder="Email address" type="email"
                           value="${account.email}">
                </div><!-- form-group// -->

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="glyphicon glyphicon-lock"></i> </span>
                    </div>
                    <input name="password" class="form-control nsw-tnr" placeholder="Password" type="password">
                    <input name="conPassword" class="form-control nsw-tnr" placeholder="Confirm password"
                           type="password">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block nsw-tnr">Create account</button>
                </div> <!-- form-group// -->
                <c:if test="${fn:length(errMsg)!=0}">
                    <c:set var="errorMsg" value="${errMsg}"/>
                    <c:forEach var="msg" items="${fn:split(errorMsg,'!')}">
                        <small class="errMsg">
                            <span class="glyphicon glyphicon-remove"></span>
                                ${msg}<br/>
                        </small>
                    </c:forEach>
                </c:if>
                <p class="text-center nsw-tnr-little">Have an account? <a
                        href="${pageContext.request.contextPath}/login">Login</a></p>
            </form>
        </article>
    </div>
</div>
<br/><br/>
</body>
</html>
