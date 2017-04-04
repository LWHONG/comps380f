    <%-- 
    Document   : admin_register
    Created on : 2017年4月5日, 上午12:25:30
    Author     : LAM
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
        <c:if test="${register != null and register == 'success'}">
            <p>You have registered.</p>
        </c:if>
        <c:if test="${register != null and register == 'exist'}">
            <p>The username have existed.</p>
        </c:if>
        <h1>Admin Register</h1>
        <form id="registerForm" action="register" method='POST' onSubmit="return validate();">
            <label for="register_username">Username</label>
            <input type="text" id="register_username" name="username" /><br /><br />
            <label for="register_password">Password</label>
            <input type="password" id="register_password" name="password" /><br /><br />
            <label for="register_password_again">Password Conform</label>
            <input type="password" id="register_password_again" /><br /><br />
            <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Submit" />
        </form>
        <!------------ End of body ------------>
        <!-- Global JS -->
        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/js/js.js" />"></script>
    </body>
</html>
