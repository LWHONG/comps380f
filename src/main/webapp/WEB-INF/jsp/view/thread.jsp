<%-- 
    Document   : thread
    Created on : 2017年4月5日, 上午02:15:26
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
                        <p>${category.name} Discussion</p>
                    </div>
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>${thread.title}</p>
                        </div>
                        <ul class="listView">
                            <li class="fileCell">
                                <div class="row">
                                    <div class="col colProfile">
                                        <div class="iconWrapper80">
                                            <img src="<c:url value="/resources/usersImages/student.png" />" height="512" width="512" alt="">
                                        </div>
                                    </div>
                                    <div class="col colContent">
                                        <p class="cellTitle">${thread.username}</p>
                                <c:choose>
                                    <c:when test="${thread.author.hasRole('ROLE_BAN')}">
                                        <p class="cellContent">The user has already banned, so that the content is not available anymore.</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="cellContent">${thread.content}</p>
                                    </c:otherwise>
                                </c:choose>
                                    </div>
                                </div>
                        <security:authorize access="hasAnyRole('ADMIN', 'USER')">
                    <c:choose>
                        <c:when test="${thread.author.hasRole('ROLE_BAN')}">
                            <p class="cellContent">The user has already banned, so that the attachments are not available anymore.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${thread.attachments}" var="attachment">
                                <span style="margin-left: 60%; background: #ddd;">${attachment.name}</span>
                                <div class="row">
                                    <ul class="btnListRight">
                                        <li><a href="<c:url value="/${category.id}/${thread.id}/attachment/thread/${attachment.id}" />">Download</a></li>
                                    </ul>
                                    <br class="clear">
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                        </security:authorize>
                            <security:authorize access="hasRole('ADMIN')">  
                                <div class="row">
                                    <ul class="btnListRight">
                                        <li><a href="<c:url value="/${category.id}/${thread.id}/delete" />">Delete</a></li>
                                    </ul>
                                    <br class="clear">
                                </div>
                            </security:authorize>
                            </li>
                        <security:authorize access="hasAnyRole('ADMIN', 'USER')">  
                            <li>
                                <form id="messageForm" class="messageForm" action="<c:url value="/${category.id}/${thread.id}/reply" />"  method="POST" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="col colProfile">
                                            <div class="iconWrapper80">
                                                <img src="<c:url value="/resources/images/icon-info-message.png" />" height="512" width="512" alt="">
                                            </div>
                                        </div>
                                        <div class="col colContent">
                                            <p class="cellTitle">Reply Message</p>
                                            <input type="text" name="content" />
                                            <input class="" type="file" name="attachments" multiple="multiple" />
                                        </div>
                                    </div>
                                    <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="row">
                                        <ul class="btnListRight">
                                            <li><input type="submit" value="Reply" /></li>
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </form>
                            </li>
                        </security:authorize>        
                            <div class="contentBlockHeader">
                                <p>Comments (Total : ${thread.numberOfReply})</p>
                            </div>
                        <c:forEach items="${thread.replies}" var="reply">
                            <li>
                                <div class="row">
                                    <div class="col colProfile">
                                        <div class="iconWrapper80">
                                            <img src="<c:url value="/resources/images/icon-info-username.png" />" height="512" width="512" alt="">
                                        </div>
                                    </div>
                                    <div class="col colContent">
                                        <p class="cellTitle">${reply.username}</p>
                                <c:choose>
                                    <c:when test="${reply.author.hasRole('ROLE_BAN')}">
                                        <p class="cellContent">The user has already banned, so that the content is not available anymore.</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="cellContent">${reply.content}</p>
                                    </c:otherwise>
                                </c:choose>
                                    </div>
                                </div>
                        <security:authorize access="hasAnyRole('ADMIN', 'USER')">
                    <c:choose>
                        <c:when test="${reply.author.hasRole('ROLE_BAN')}">
                            <p class="cellContent">The user has already banned, so that the attachments are not available anymore.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${reply.attachments}" var="attachment">
                                <span style="margin-left: 60%; background: #ddd;">${attachment.name}</span>
                                <div class="row">
                                    <ul class="btnListRight">
                                        <li><a href="<c:url value="/${category.id}/${thread.id}/attachment/reply/${attachment.id}" />">Download</a></li>
                                    </ul>
                                    <br class="clear">
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                        </security:authorize>
                            <security:authorize access="hasRole('ADMIN')"> 
                                <div class="row">
                                    <ul class="btnListRight">
                                        <li><a href="<c:url value="/${category.id}/${thread.id}/delete/${reply.id}" />">Delete</a></li>
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
                    <li class="navOptionHome"><a href="<c:url value="/index" />"><div class="navItemActiveIdicator navItemActive"></div><p>HOME</p></a></li>                    
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
