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
				<p class="sidebarUsername">${username}</p>
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
									<li><a href="">Lecture</a></li>
									<li><a href="">Lab</a></li>
									<li><a href="">Other</a></li>
								</ul>
								<br class="clear">
								</div>
							</div>
						</li>
					</ul>
				</div>



				<div class="contentBlock contentMainBlock">
					<div class="contentBlockHeader">
						<p>Poll</p>
					</div>
					<ul class="listView">
						<li>
							<div class="row">
								<div class="col colContent">
									<p class="cellTitle">What kind of IT jobs do you like to do?</p>
									<ul class="btnListRight">
										<li><input type="radio" name="ans" value="1">Programer</li>
										<li><input type="radio" name="ans" value="2">Web Designer</li> 
										<li><input type="radio" name="ans" value="3">Database Administrator</li>
										<li><input type="radio" name="ans" value="4">IT Security Officer</li>
									</ul>
								</div>
							</div>
							<div class="row">
								<ul class="btnListRight">
									<li><a href="">Submit</a></li>
								</ul>
								<br class="clear">
							</div>
						</li>
					</ul>
				</div>

				<div class="contentBlock contentMainBlock">
					<div class="contentBlockHeader">
						<p>Poll Result</p>
					</div>
					<ul class="listView">
						<li>
							<div class="row">
								<div class="col colContent">
									<p class="cellTitle">What kind of IT jobs do you like to do?</p>
									<ul class="btnListRight">
										<li>Programer: 20</li>
										<li>Web Designer: 15</li> 
										<li>Database Administrator: 8</li>
										<li>IT Security Officer: 5</li>
									</ul>
									<br class="clear">
									<p class="cellContent">The number of users voted: 48</p>

								</div>
							</div>
						</li>
					</ul>
				</div>

			</div>
		</div>
		<nav>
			<ul>
				<li class="navOptionHome"><a href="#"><div class="navItemActiveIdicator navItemActive"></div><p>HOME</p></a></li>
				<li class="navOptionProfile"><a href="#"><div class="navItemActiveIdicator"></div><p>PROFILE</p></a></li>
				<li class="navOptionPoll"><a href="#"><div class="navItemActiveIdicator"></div><p>POLL</p></a></li>
				<!--
				<li class="navOptionLoginout"><a href="#"><div class="navItemActiveIdicator"></div><p>LOGIN</p></a></li> -->
                                <li class="navOptionLoginout"><a href="javascript:logout('<c:url value="/logout" />', {'${_csrf.parameterName}': '${_csrf.token}'});"><div class="navItemActiveIdicator"></div><p>LOGOUT</p></a></li>
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
