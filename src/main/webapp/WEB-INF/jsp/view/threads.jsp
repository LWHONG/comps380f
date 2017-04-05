<%-- 
    Document   : threads
    Created on : 2017年4月4日, 下午09:26:15
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

                    <!--
                    <p class="sidebarSectionTitle">You are</p>
                    <p class="sidebarUsername">Visitors</p>
                    -->				
                    <p class="sidebarSectionTitle">Login as:</p>
                    <c:if test="${user != null}">
                        <p class="sidebarUsername">${user.username}</p>
                    </c:if>
                </div>
            </div>
            <div class="view contentViewContainer">
                <div class="view contentView">

                    <div class="pageHeader">
                        <p>${category.name} Discussion</p>
                    </div>                  
                <security:authorize access="hasAnyRole('ADMIN', 'USER')">     
                    <div class="newPostBtn">
                        <a href="<c:url value="/${category.id}/post" />">New Post</a>
                    </div>
                </security:authorize>
                <div style="margin: 40px; font-size: 24pt;">Total : ${threads_size}</div>
                <c:forEach items="${threads}" var="thread">
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <a href="<c:url value="/${category.id}/${thread.id}" />"><p>${thread.title}</p></a>
                        </div>
                        <ul class="listView">
                            <li>
                                <div class="row">
                                    <div class="col colProfile">
                                        <div class="profileWrapper80">
                                            <img src="<c:url value="/resources/usersImages/cat.jpg" />" height="200" width="150" alt="">
                                        </div>
                                    </div>
                                    <div class="col colContent">
                                        <p class="cellTitle">${thread.username}</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </c:forEach>
                </div>
            </div>
            <nav>
                <ul>
                    <li class="navOptionHome"><a href="<c:url value="/index" />"><div class="navItemActiveIdicator navItemActive"></div><p>HOME</p></a></li>
                    <li class="navOptionProfile"><a href="#"><div class="navItemActiveIdicator"></div><p>PROFILE</p></a></li>
                    <li class="navOptionPoll"><a href="#"><div class="navItemActiveIdicator"></div><p>POLL</p></a></li>
                    <!--
                    <li class="navOptionLoginout"><a href="#"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li> -->
                    <c:choose>
                        <c:when test="${user != null}">
                            <li class="navOptionLoginout"><a href="javascript:logout('<c:url value="/logout" />', {'${_csrf.parameterName}': '${_csrf.token}'});"><div class="navItemActiveIdicator"></div><p>LOGOUT</p></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="navOptionLoginout"><a href="<c:url value="/login" />"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li>
                        </c:otherwise>
                    </c:choose>
                    <li class="navOptionBackend"><a href="#"><div class="navItemActiveIdicator"></div><p>BACKEND</p></a></li>
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
