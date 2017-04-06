<%-- 
    Document   : login
    Created on : 2017å¹´4æ3æ¥, ä¸å06:41:16
    Author     : LAM
--%>

<!DOCTYPE html>
<html>
    <head>
        <title>COURSERV</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Global CSS -->
        <link rel="stylesheet" href="<c:url value="/resources/css/fonts.css" />" type="text/css" charset="utf-8">
        <link rel="stylesheet" href="<c:url value="/resources/css/reset.css" />" type="text/css" charset="utf-8">
        <link rel="stylesheet" href="<c:url value="/resources/css/layout.css" />" type="text/css" charset="utf-8">
    </head>

    <body>
        <c:if test="${param.error != null}">
            <p>Login failed.</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p>You have logged out.</p>
        </c:if>
        <c:if test="${register != null and register == 'success'}">
            <p>You have registered.</p>
        </c:if>
        <c:if test="${register != null and register == 'exist'}">
            <p>The username have existed.</p>
        </c:if>
        <div class="mainView">
            <div class="loginHeader">

            </div>
            <div class="content">
                <div class="loginView">
                    <form class="loginForm" id="loginForm" action="login" method="POST">
                        <p class="cellTitle">Username</p>
                        <input type="text" id="login_username" name="username">
                        <p class="cellTitle">Password</p>
                        <input type="password" id="login_password" name="password">
                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="fullWidthButton loginBtn" type="submit" value="Login" onclick="loginUi()" />
                    </form>
                    <!-- <button class="fullWidthButton loginBtn" onclick="loginUi()">Register</button> -->   
                    <!--<div class="loginProcess">
                            <div class="loginLoading">
                                <img src="<c:url value="/resources/images/loadingIcon.GIF" />" width="60" height="80" alt="">
                                    <p>Logining in</p>
                            </div>
                            <div class="loginFailed">
                                    <p class="loginFailedTitle">Cannot Login</p>
                                    <p class="loginFailedMessage">Check username & password.</p>
                                    <button>Back</button>
                            </div>
                    </div>-->
                </div>
                <div class="registerView">
                    <form class="registerForm" id="registerForm" action="register" method='POST' onSubmit="return validate();">
                        <p class="cellTitle">Username</p>
                        <input type="text" id="username" name="username" />
                        <p class="cellTitle">Password</p>
                        <input type="password" id="password" name="password" />
                        <p class="cellTitle">Password Again</p>
                        <input type="password" id="password_confirm" />
                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="fullWidthButton registerBtn" type="submit" value="Register" onclick="registerUi()" />                        
                    </form>
                    <!-- <button class="fullWidthButton registerBtn" onclick="registerUi()">Register</button> -->
                    <!--
                    <div class="registerProcess">
                            <div class="registerLoading">
                                    <img src="images/loadingIcon.GIF" width="60" height="80" alt="">
                                    <p>Loading</p>
                            </div>
                            <div class="registerFailed">
                                    <p class="registerFailedTitle">Cannot Register</p>
                                    <p class="registerFailedMessage">The user name already exit.</p>
                                    <button>Back</button>
                            </div>
                    </div>
                    -->
                </div>
            </div>
            <div class="loginFooter">

            </div>
        </div>

        <!------------ End of body ------------>
        <!-- Global JS -->
        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/js/layout.js" />"></script>
        <script src="<c:url value="/resources/js/js.js" />"></script>
    </body>
</html>
