<%-- 
    Document   : profile
    Created on : 2017年4月7日, 上午01:41:20
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

        <div class="view mainView">
            <div class="view sidebarViewContainer">
                <div class="view sidebar">
                    <div class="sitelogo"><img src="<c:url value="/resources/images/sideLogo.png" />" height="36" width="36" alt="">COURSERV</div>
                    <div class="profilePicture">
                        <div class="profilePictureWrapper">
                            <img src="<c:url value="/resources/usersImages/e.jpg" />" height="200" width="150" alt="">
                            <!-- 						<div class="changeProfilePictureDrop">
                                                                                    <p class="changeProfilePictureTitle">Change Profile</p>
                                                                                    <p class="changeProfilePictureMessage">Drop file here</p>
                                                                            </div> -->
                            <!-- 						<div class="changeProfilePictureUploading">
                                                                                    <p class="changeProfilePictureTitle">Processing</p>
                                                                                    <img src="images/loadingIcon.GIF" width="45" height="60" alt="">
                                                                                    <p class="changeProfilePictureMessage">Uploading</p>
                                                                            </div> -->
                            <!-- 						<div class="changeProfilePictureFailed">
                                                                                    <p class="changeProfilePictureTitle">Opps!</p>
                                                                                    <p class="changeProfilePictureMessage">Problems occurs.</p>
                                                                                    <button class="changeProfilePictureOk">OK</button>
                                                                            </div> -->
                        </div>

                    </div>

                    <p class="sidebarSectionTitle">Login as:</p>
                    <p class="sidebarUsername">${user.username}</p>
                </div>
            </div>
            <div class="view contentViewContainer">
                <div class="view contentView">

                    <div class="pageHeader">
                        <p>Profile</p>
                    </div>

                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Personal Information</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <div class="row">
                                    <div class="col colProfile">
                                        <div class="iconWrapper80">
                                            <img src="<c:url value="/resources/images/icon-info-username.png" />" height="512" width="512" alt="">
                                        </div>
                                    </div>
                                    <div class="col colContent">
                                        <p class="cellTitle">Name</p>
                                        <input type="text" value="${user.username}" readonly>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <form class="emailForm" id="emailForm" action="<c:url value="profile/edit/info" />" method="POST">
                                    <div class="row">
                                        <div class="col colProfile">
                                            <div class="iconWrapper80">
                                                <img src="<c:url value="/resources/images/icon-info-email.png" />" height="512" width="512" alt="">
                                            </div>
                                        </div>
                                        <div class="col colContent">
                                            <p class="cellTitle">Email Address</p>
                                            <input type="text" name="email" value="${user.email}" />
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-70">
                                            <div class="inlineMessage">
                                                <c:if test="${edit_info == 'success'}">
                                                    <p>Personal information updated.</p>
                                                </c:if>
                                                <c:if test="${edit_info == 'fail'}">
                                                    <p>Personal information update failed.</p>
                                                </c:if>
                                            </div>
                                        </div>
                                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="col col-30">
                                            <ul class="btnListRight">
                                                <li><input type="submit" value="Update" /><!--<a href="">Update</a>--></li>
                                            </ul>
                                            <br class="clear">
                                        </div>
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                <security:authorize access="hasRole('ADMIN')">
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Authority</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <form class="changePassword" id="changePassword" action="<c:url value="/profile/${user.username}/edit/authority" />" method="POST">
                                    <div class="row">
                                        <p class="cellTitle">Role</p>
                                        <center>
                                            <c:choose>
                                                <c:when test="${user.hasRole('ROLE_ADMIN')}">
                                                    <span style="margin: 100px;">Admin<input type="checkbox" name="roles" value="ROLE_ADMIN" checked /></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <span style="margin: 100px;">Admin<input type="checkbox" name="roles" value="ROLE_ADMIN" /></span>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${user.hasRole('ROLE_USER')}">
                                                    <span style="margin: 100px;">User<input type="checkbox" name="roles" value="ROLE_USER" checked /></span> 
                                                    </c:when>
                                                    <c:otherwise>
                                                    <span style="margin: 100px;">User<input type="checkbox" name="roles" value="ROLE_USER" /></span> 
                                                    </c:otherwise>
                                                </c:choose>
                                        </center>
                                    </div>
                                    <div class="col col-70">
                                        <div class="inlineMessage">
                                            <c:if test="${edit_authority == 'success'}">
                                                <p>Authority updated.</p>
                                            </c:if>
                                            <c:if test="${edit_authority == 'fail'}">
                                                <p>Authority update failed.</p>
                                            </c:if>
                                        </div>
                                    </div>
                                    <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="col col-30">
                                        <ul class="btnListRight">
                                            <li><input type="submit" value="Update" /><!--<a href="">Update</a>--></li>
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                </security:authorize>
                        <div class="contentBlock contentMainBlock">
                            <div class="contentBlockHeader">
                                <p>Password</p>
                            </div>
                            <ul class="listView">
                                <li>
                                    <form class="changePassword" id="changePassword" action="<c:url value="/profile/edit/password" />" method="POST" onSubmit="return validate();">
                                        <div class="row">
                                            <div class="col colProfile">
                                                <div class="iconWrapper80">
                                                    <img src="<c:url value="/resources/images/icon-info-password.png" />" height="512" width="512" alt="">
                                                </div>
                                            </div>
                                            <div class="col colContent">
                                                <p class="cellTitle">Current Password</p>
                                                <input type="password" id="current_password" name="currentPassword" />
                                                <p class="cellTitle">New Password</p>
                                                <input type="password" id="password" name="password" placeholder="New Password" />
                                                <input type="password" id="password_confirm" placeholder="Again" />
                                            </div>
                                        </div>
                                        <div class="col col-70">
                                            <div class="inlineMessage">
                                                <c:if test="${edit_password == 'success'}">
                                                    <p>Password updated.</p>
                                                </c:if>
                                                <c:if test="${edit_password == 'fail'}">
                                                    <p>Password update failed.</p>
                                                </c:if>
                                            </div>
                                        </div>
                                        <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="col col-30">
                                            <ul class="btnListRight">
                                                <li><input type="submit" value="Update" /><!--<a href="">Update</a>--></li>
                                            </ul>
                                            <br class="clear">
                                        </div>
                                    </form>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>
                <nav>
                    <ul>
                    <li class="navOptionHome"><a href="<c:url value="/index" />"><div class="navItemActiveIdicator"></div><p>HOME</p></a></li>                    
                <security:authorize access="hasAnyRole('ADMIN', 'USER')">
                    <li class="navOptionProfile"><a href="<c:url value="/profile" />"><div class="navItemActiveIdicator navItemActive"></div><p>PROFILE</p></a></li> 
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    <li class="navOptionPoll"><a href="#"><div class="navItemActiveIdicator"></div><p>POLL</p></a></li>
                </security:authorize>                      
                    <!--<li class="navOptionLoginout"><a href="#"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li> -->
            <c:choose>
                <c:when test="${user != null}">
                    <li class="navOptionLoginout"><a href="javascript:logout('<c:url value="/logout" />', {'${_csrf.parameterName}': '${_csrf.token}'});"><div class="navItemActiveIdicator"></div><p>LOGOUT</p></a></li>
                </c:when>
                <c:otherwise>
                    <li class="navOptionLoginout"><a href="<c:url value="/login" />"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li>
                </c:otherwise>
            </c:choose>
                <security:authorize access="hasRole('ADMIN')">    
                    <li class="navOptionBackend"><a href="<c:url value="/admin" />"><div class="navItemActiveIdicator"></div><p>BACKEND</p></a></li>
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
