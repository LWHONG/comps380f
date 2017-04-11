<%-- 
    Document   : poll
    Created on : 2017年4月8日, 下午10:34:53
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
                        <p>POLL</p>
                    </div>
                <security:authorize access="hasRole('ADMIN')">
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Create Poll</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <div class="row">
                                    <div class="col colContent">
                                        <form class="pollForm" id="pollForm" action="<c:url value="/poll/create" />" method="POST" onsubmit="return validateForPoll();">
                                            <p class="cellTitle">Question</p>
                                            <input type="text" id="question" name="question" />
                                            <p class="cellTitle">Answer1</p>
                                            <input type="text" id="option_a" name="optionA" />
                                            <p class="cellTitle">Answer2</p>
                                            <input type="text" id="option_b" name="optionB" />
                                            <p class="cellTitle">Answer3</p>
                                            <input type="text" id="option_c" name="optionC" />
                                            <p class="cellTitle">Answer4</p>
                                            <input type="text" id="option_d" name="optionD" />                                       
                                            <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <ul class="btnListRight">
                                                <li><input type="submit" value="Submit" /></li>
                                            </ul>
                                        </form>
                                    </div>
                                </div>
                            </li>
                            <br class="clear">
                        </ul>
                    </div>
                </security:authorize>
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>History - Poll Results</p>
                        </div>
                        <ul class="listView">
                        <c:forEach items="${polls}" var="poll">
                            <li>
                                <div class="row">
                                    <div class="col colContent">
                                        <p class="cellTitle">${poll.question}</p>
                                        <ul class="btnListRight">
                                            <li>${poll.optionA}: ${poll.numberOfOptionA}</li>
                                            <li>${poll.optionB}: ${poll.numberOfOptionB}</li>
                                            <li>${poll.optionC}: ${poll.numberOfOptionC}</li>
                                            <li>${poll.optionD}: ${poll.numberOfOptionD}</li>                         
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </div>
                                <div class="row">
                                    <p class="cellContent">The number of users voted: ${poll.numberOfPollAnswer}</p>
                                    <br class="clear">
                                </div>
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
                    <li class="navOptionPoll"><a href="<c:url value="/poll" />"><div class="navItemActiveIdicator navItemActive"></div><p>POLL</p></a></li>
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
