<%-- 
    Document   : backend
    Created on : 2017年4月5日, 上午12:25:30
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
        <c:if test="${register == 'success'}">
            <p>You have registered.</p>
        </c:if>
        <c:if test="${register == 'exist'}">
            <p>The username have existed.</p>
        </c:if>
        <div class="view mainView">
            <div class="view sidebarViewContainer">
                <div class="view sidebar">
                    <div class="sitelogo"><img src="<c:url value="/resources/images/sideLogo.png" />" height="36" width="36" alt="">COURSERV</div>
                    <div class="profilePicture">
                        <div class="profilePictureWrapper">
                            <img src="<c:url value="/resources/usersImages/icon.png" />" height="200" width="150" alt="">
                        </div>
                    </div>
            <c:choose>
                <c:when test="${user != null}">
                    <p class="sidebarSectionTitle">Login as:</p>
                    <p class="sidebarUsername">${user.username}</p>
                </c:when>
                <c:otherwise>
                    <p class="sidebarSectionTitle">&nbsp;</p>
                    <p class="sidebarUsername">Guest</p>
                </c:otherwise>
            </c:choose>
                </div>
            </div>
            <div class="view contentViewContainer">
                <div class="view contentView">
                    <div class="pageHeader">
                        <p>Edit Users</p>
                    </div>                 
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>User Registration</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <form class="registerForm" id="registerForm" action="<c:url value="/admin/register" />" method='POST' onsubmit="return validate();">
                                    <div class="row">
                                        <p class="cellTitle">Username</p>
                                        <input type="text" id="username" name="username" />
                                    </div>
                                    <div class="row">
                                        <p class="cellTitle">Password</p>
                                        <input type="password" id="password" name="password" />
                                    </div>
                                    <div class="row">
                                        <p class="cellTitle">Password Confirm</p>
                                        <input type="password" id="password_confirm" />
                                    </div>
                                    <div class="row">
                                        <p class="cellTitle">Email</p>
                                        <input type="text" id="email" name="email" />
                                    </div>
                                    <div class="row">
                                        <p class="cellTitle">Role</p>
                                        <center>
                                            <span style="margin: 100px;">Admin<input type="checkbox" name="roles" value="ROLE_ADMIN" /></span>
                                            <span style="margin: 100px;">User<input type="checkbox" name="roles" value="ROLE_USER" checked /></span>
                                        </center>
                                    </div>
                                    <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="row">
                                        <ul class="btnListRight">
                                            <li><input type="submit" value="Add" /></li>
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Users List</p>
                        </div>
                        <ul class="listView">
                            <c:forEach items="${users}" var="user">
                                <li>
                                    <div class="row">
                                        <div class="col colProfile">
                                            <div class="iconWrapper80">
                                                <img src="<c:url value="/resources/images/icon-info-username.png" />" height="512" width="512" alt="">
                                            </div>
                                        </div>
                                        <div class="col colContent">
                                            <p class="cellTitle"><span style="margin: 40px;">${user.username}</span><span style="margin:40px;">( <c:forEach items="${user.roles}" var="role" varStatus="loop">${role}<c:if test="${!loop.last}"> | </c:if></c:forEach> )</span></p>
                                        </div>
                                    </div>
                                    <security:authorize access="hasRole('ADMIN')">
                                        <div class="row">
                                            <ul class="btnListRight">
                                                <li><a href="<c:url value="/profile/${user.username}" />">Edit</a></li>
                                        <c:choose>
                                            <c:when test="${user.hasRole('ROLE_BAN')}">
                                                     <li><a href="<c:url value="/admin/unban/${user.username}" />">Unban</a></li>                   
                                            </c:when><c:otherwise>
                                                <li><a href="<c:url value="/admin/ban/${user.username}" />">Ban</a></li>       
                                            </c:otherwise>
                                        </c:choose>
                                                <li><a href="<c:url value="/admin/delete/${user.username}" />">Remove</a></li> 
                                            </ul>
                                            <br class="clear">
                                        </div>
                                    </security:authorize>
                                </li>         
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <nav>
                <ul>
                    <li class="navOptionHome"><a href="<c:url value="/index" />"><div class="navItemActiveIdicator"></div><p>HOME</p></a></li>                    
                <security:authorize access="hasAnyRole('ADMIN', 'USER')">
                    <li class="navOptionProfile"><a href="<c:url value="/profile" />"><div class="navItemActiveIdicator"></div><p>PROFILE</p></a></li> 
                </security:authorize>
                    <li class="navOptionPoll"><a href="<c:url value="/poll" />"><div class="navItemActiveIdicator"></div><p>POLL</p></a></li>
            <c:choose>
                <c:when test="${user != null}">
                    <li class="navOptionLoginout"><a href="javascript:logout('<c:url value="/logout" />', {'${_csrf.parameterName}': '${_csrf.token}'});"><div class="navItemActiveIdicator"></div><p>LOGOUT</p></a></li>
                </c:when>
                <c:otherwise>
                    <li class="navOptionLoginout"><a href="<c:url value="/login" />"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li>
                </c:otherwise>
            </c:choose>
                <security:authorize access="hasRole('ADMIN')">    
                    <li class="navOptionBackend"><a href="<c:url value="/admin" />"><div class="navItemActiveIdicator navItemActive"></div><p>BACKEND</p></a></li>
                </security:authorize>
                </ul>
            </nav>
        </div>
        <!------------ End of body ------------>
        <!-- Global JS -->
        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/js/layout.js" />"></script>
        <script src="<c:url value="/resources/js/js.js" />"></script>
    </body>
</html>
