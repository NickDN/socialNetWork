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
<jsp:include page="navbar.jsp">
    <jsp:param name="account" value="${account}"/>
</jsp:include>

<div class="container">
    <br/>
    <hr/>
    <div class="card bg-light">
        <article class="card-body mx-auto" style="max-width: 400px;">
            <h4 class="card-title mt-3 text-center nsw-font">Change password</h4>
            <div align="center" style="padding-bottom: 20px ">
                <c:if test="${fn:length(successMsg)!=0}">
                    <div class="alert alert-success nsw-tnr">
                        <strong>Success!</strong> Password has been updated successfully
                    </div>
                </c:if>
            </div>
            <form action="${pageContext.request.contextPath}/update?action=password" method="post">
                <div class="form-group text-center">
                    <label class="align-content-center control-label nsw-tnr" for="newPassword">New password</label>
                    <input id="newPassword" name="password" class="form-control nsw-tnr" placeholder="Password..."
                           type="password">

                    <label class="control-label nsw-tnr" for="conPassword">Conferm password</label>
                    <input id="conPassword" name="conPassword" class="form-control nsw-tnr"
                           placeholder="Confirm password..." type="password">

                    <div class="row">
                        <div class="col-4">
                            <div align="center" style="padding-top: 20px ">
                                <a href="${pageContext.request.contextPath}/update"
                                   class="btn btn-primary btn-block nsw-tnr" role="button">Back</a>
                            </div>
                        </div>
                        <div class="col-8">
                            <div align="center" style="padding-top: 20px ">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block nsw-tnr">Save</button>
                                </div> <!-- form-group// -->
                            </div>
                        </div>
                    </div>
                    <c:if test="${fn:length(errMsg)!=0}">
                        <c:set var="errorMsg" value="${errMsg}"/>
                        <c:forEach var="msg" items="${fn:split(errorMsg,'!')}">
                            <small class="errMsg">
                                <span class="glyphicon glyphicon-remove"></span>
                                    ${msg}<br/>
                            </small>
                        </c:forEach>
                    </c:if>
                </div>
            </form>
        </article>
    </div>
</div>
<br/><br/>
</body>
</html>
