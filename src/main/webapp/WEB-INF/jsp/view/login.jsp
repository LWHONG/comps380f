<%-- 
    Document   : login
    Created on : 2017年4月3日, 下午06:41:16
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
        <c:if test="${register == 'success'}">
            <p>You have registered.</p>
        </c:if>
        <c:if test="${register == 'exist'}">
            <p>The username have existed.</p>
        </c:if>
        <div class="mainView">
            <div class="loginHeader">
            </div>
            <div class="content">
                <div class="loginView">
                    <form class="loginForm" id="loginForm" action="<c:url value="/login" />" method="POST">
                        <p class="cellTitle">Username</p>
                        <input type="text" id="login_username" name="username">
                        <p class="cellTitle">Password</p>
                        <input type="password" id="login_password" name="password">
                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="fullWidthButton loginBtn" type="submit" value="Login" onclick="loginUi()" />
                    </form>
                </div>
                <div class="registerView">
                    <form class="registerForm" id="registerForm" action="<c:url value="/register" />" method='POST' onSubmit="return validate();">
                        <p class="cellTitle">Username</p>
                        <input type="text" id="username" name="username" />
                        <p class="cellTitle">Password</p>
                        <input type="password" id="password" name="password" />
                        <p class="cellTitle">Password Again</p>
                        <input type="password" id="password_confirm" />
                        <p class="cellTitle">Ermail</p>
                        <input type="text" id="email" name="email" />
                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="fullWidthButton registerBtn" type="submit" value="Register" onclick="registerUi()" />                        
                    </form>
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
