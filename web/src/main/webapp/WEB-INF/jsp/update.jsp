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
    <link rel="stylesheet" href="../../css/socicon.css">
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
        <article class="card-body mx-auto" style="max-width: 95%;">
            <h4 class="card-title mt-3 text-center nsw-font">Edit Profile</h4>
            <div align="center" style="padding-bottom: 20px ">
                <c:if test="${fn:length(successMsg)!=0}">
                    <div class="alert alert-success nsw-tnr">
                        <strong>Success!</strong> Your profile has been updated successfully
                    </div>
                </c:if>
            </div>
            <div align="center" style="padding-bottom: 20px ">
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
            <form action="${pageContext.request.contextPath}/update" method="post">
                <input type="hidden" name="id" value="${account.id}">
                <input type="hidden" name="password" value="${account.password}">
                <div class="row">
                    <div class="col-6">
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
                        <div align="center" style="padding-bottom: 20px ">
                            <a href="${pageContext.request.contextPath}/update?action=password"
                               class="btn btn-primary btn-block nsw-tnr" style="max-width: 80%" role="button">Change
                                password</a>
                        </div>
                    </div>

                    <div class="col-6">
                        <div class="row">
                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"> <i class="glyphicon glyphicon-user"></i> </span>
                                </div>
                                <input name="middleName" class="form-control nsw-tnr" placeholder="Middle name"
                                       type="text"
                                       value="${account.middleName}">
                            </div>
                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text nsw-tnr">Gender</span>
                                </div>
                                <label for="gender"></label>
                                <select name="gender" id="gender" class="form-control nsw-tnr">
                                    <option value="male" ${account.gender=='MALE'?'selected':''}>Male</option>
                                    <option value="female" ${account.gender=='FEMALE'?'selected':''}>Female</option>
                                    <option value="unknown" ${account.gender=='UNKNOWN'?'selected':''}>Unknown</option>
                                </select>
                            </div>

                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"> <i class="glyphicon glyphicon-home"></i> </span>
                                </div>
                                <input name="homeAddress" class="form-control nsw-tnr" placeholder="Home address"
                                       type="text"
                                       value="${account.homeAddress}">
                            </div>

                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"> <i
                                            class="glyphicon glyphicon-briefcase"></i> </span>
                                </div>
                                <input name="workAddress" class="form-control nsw-tnr" placeholder="Work address"
                                       type="text"
                                       value="${account.workAddress}">
                            </div>

                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text nsw-tnr"> <i class="socicon-icq"></i></span>
                                </div>
                                <input name="icq" class="form-control nsw-tnr" placeholder="ICQ number"
                                       type="text"
                                       value="${account.icq}">
                            </div>

                            <div class="form-group input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text nsw-tnr"><i class="socicon-skype"></i></span>
                                </div>
                                <input name="skype" class="form-control nsw-tnr" placeholder="Skype number"
                                       type="text"
                                       value="${account.skype}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group" align="center">
                    <input name="additionalInfo" class="form-control nsw-tnr" placeholder="Additional information..."
                           type="text" maxlength="45" value="${account.additionalInfo}">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block nsw-tnr">Save</button>
                </div>
            </form>
        </article>
    </div>
</div>
<br/><br/>
</body>
</html>
