<%-- 
    Document   : index
    Created on : 2017年4月3日, 下午06:56:57
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
                        <p>HOME</p>
                    </div>
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Welcome to COURSERV</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <div class="row">
                                    <div class="col colContent">
                                        <p class="cellIndexTitle">Select the Categories:</p>
                                        <ul class="btnList">
                                            <li><a href="<c:url value="/lecture" />">Lecture</a></li>
                                            <li><a href="<c:url value="/lab" />">Lab</a></li>
                                            <li><a href="<c:url value="/other" />">Other</a></li>
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
            <security:authorize access="hasAnyRole('ADMIN', 'USER')">
                <c:if test="${poll != null}">
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Poll</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <form id="pollForm" action="<c:url value="/poll" />" method="POST">
                                    <input type="hidden" name="pollId" value="${poll.id}" />
                                    <div class="row">
                                        <div class="col colContent">
                                            <p class="cellTitle">${poll.question}</p>
                                            <ul class="btnListRight" style="cursor:default">
                                        <c:choose>
                                            <c:when test="${pollAnswer != null}">
                                                <c:choose>
                                                    <c:when test="${pollAnswer.answer == 'option_a'}">
                                                <li><input type="radio" name="answer" value="option_a" checked disabled />${poll.optionA}</li>
                                                    </c:when>
                                                    <c:otherwise>
                                                <li><input type="radio" name="answer" value="option_a" disabled />${poll.optionA}</li>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${pollAnswer.answer == 'option_b'}">
                                                <li><input type="radio" name="answer" value="option_b" checked disabled />${poll.optionB}</li>
                                                    </c:when>
                                                    <c:otherwise>
                                                <li><input type="radio" name="answer" value="option_b" disabled />${poll.optionB}</li>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                      <c:when test="${pollAnswer.answer == 'option_c'}">
                                                <li><input type="radio" name="answer" value="option_c" checked disabled />${poll.optionC}</li>
                                                    </c:when>
                                                    <c:otherwise>
                                                <li><input type="radio" name="answer" value="option_c" disabled />${poll.optionC}</li>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${pollAnswer.answer == 'option_d'}">
                                                <li><input type="radio" name="answer" value="option_d" checked disabled />${poll.optionD}</li>
                                                    </c:when>
                                                    <c:otherwise>
                                                <li><input type="radio" name="answer" value="option_d" disabled />${poll.optionD}</li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <li><input type="radio" name="answer" value="option_a" />${poll.optionA}</li>
                                                <li><input type="radio" name="answer" value="option_b" />${poll.optionB}</li> 
                                                <li><input type="radio" name="answer" value="option_c" />${poll.optionC}</li>
                                                <li><input type="radio" name="answer" value="option_d" />${poll.optionD}</li>
                                            </c:otherwise>
                                        </c:choose>
                                            </ul>
                                        </div>
                                    </div>
                                    <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="row">
                                        <ul class="btnListRight">
                                    <c:choose>
                                        <c:when test="${pollAnswer != null}">
                                            <li><input type="submit" value="Submit" disabled /></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><input type="submit" value="Submit" /></li>
                                        </c:otherwise>
                                    </c:choose>
                                        </ul>
                                        <br class="clear">
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                </c:if>
            </security:authorize>
                <c:if test="${poll != null and (user == null or (user != null and pollAnswer != null))}">
                    <div class="contentBlock contentMainBlock">
                        <div class="contentBlockHeader">
                            <p>Poll Result</p>
                        </div>
                        <ul class="listView">
                            <li>
                                <div class="row">
                                    <div class="col colContent">
                                        <p class="cellTitle">${poll.question}</p>
                                        <ul class="btnListRight" style="cursor:default">
                                            <li>${poll.optionA}: ${poll.numberOfOptionA}</li>
                                            <li>${poll.optionB}: ${poll.numberOfOptionB}</li> 
                                            <li>${poll.optionC}: ${poll.numberOfOptionC}</li>
                                            <li>${poll.optionD}: ${poll.numberOfOptionD}</li>
                                        </ul>
                                        <br class="clear">
                                        <p class="cellContent">The number of users voted: ${poll.numberOfPollAnswer}</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </c:if>
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
