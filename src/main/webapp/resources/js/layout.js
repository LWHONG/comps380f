$(document).ready(function () {
	responsive();

});

$( window ).load(function() {
	imageWrappers();
});

$(window).resize(function(){
	responsive();
});

function imageWrappers() {
	resizeProfilePicture(".iconWrapper80");
	resizeProfilePicture(".profileWrapper80");
	resizeProfilePicture(".profileWrapper40");
	resizeProfilePicture(".profilePictureWrapper");
}

function responsive() {
	mainViewSize();
	hVCenterWindow($(".mainView"));
	hVCenter($(".content"));
	mainViewElementSetup();

}

function horizontalCenter(horDiv) {

	parentWidth = horDiv.parent().innerWidth();
	
	divWidth = horDiv.width();
	
	divLeft = (parentWidth/2) - (divWidth/2);
	
	horDiv.css('left', divLeft);
	return divLeft;
	
}

function verticalCenter(verDiv) {

	if (verDiv.parent() == "body") {
		parentHeight = $(window).height();
	} else {
		parentHeight = verDiv.parent().innerHeight();
	}

	
	
	divHeight = verDiv.height();
	
	divTop = (parentHeight/2) - (divHeight/2);
	
	verDiv.css('top', divTop);
	
	return divTop;
}

function hVCenterWindow(hVCenterDiv) {
	
	parentHeight = $(window).height();
	
	divHeight = hVCenterDiv.height();
	
	divTop = (parentHeight/2) - (divHeight/2);


	divLeft = horizontalCenter(hVCenterDiv);
	
	hVCenterDiv.css({
	'top': divTop,
	'left': divLeft,	
	});
}

function hVCenter(hVCenterDiv) {
	
	divTop = verticalCenter(hVCenterDiv);
	divLeft = horizontalCenter(hVCenterDiv);
	
	hVCenterDiv.css({
	'top': divTop,
	'left': divLeft,	
	});
}

function mainViewSize() {
	windowHeight = $(window).height();
	windowWidth = $(window).width();

	containerHeight = windowHeight*0.95;
	containerWidth = windowWidth*0.8;

	$(".mainView").height(containerHeight);
	$(".mainView").width(containerWidth);

}

function mainViewElementSetup() {

	// set inside div same height
	mainViewHeight = $('.mainView').height();
	$('.sidebarViewContainer, .sidebar, .contentViewContainer, .contentView, nav').height(mainViewHeight);

	// set contectViewContainer width by  mainView - sidebar width
	mainViewWidth = $('.mainView').width();
	sidebarWidth = $('.sidebar').width();
	$('.contentViewContainer').width(mainViewWidth-sidebarWidth);

	// add 15px more to contentView to hide the scroll bar
	contentViewContainerWidth = $('.contentViewContainer').width();
	$('.contentView').width(contentViewContainerWidth-1);
}

function loginUi() {
	$('.loginFailed').hide();
	$('.loginProcess').css('top', '0px');
	
}

function registerUi() {
	$('.registerFailed').hide();
	$('.registerProcess').css('top', '0px');
	
}

function resizeProfilePicture(imageContainer) {

	$(imageContainer).each(function(index, el) {
		var image = $(this).children('img');
		var originalWidth = image.width();
		var originalHeight = image.height();
		var originalRatio = originalWidth/originalHeight;

		var container = $(this);
		var containerWidth = container.width();
		var containerHeight = container.height();

		container.css({
			"position": 'relative',
			"overflow": 'hidden'
		});

		image.css('position', 'absolute');

		if (originalRatio > 1) {

			image.height(containerHeight);

			image.width(containerHeight*originalRatio);

		} else if (originalRatio < 1){

			image.width(containerWidth);

			image.height(containerWidth/originalRatio);

		} else if (originalRatio == 1){
			image.width(containerWidth);
			image.height(containerHeight);
		}

		hVCenter(image);

	});


	// console.log(image.attr("src") + originalRatio);
	console.log($(imageContainer).attr("class"));

}

function resizeProfilePicture1(imageContainer) {
	var image = imageContainer.children("img");
	var originalWidth = image.width();
	var originalHeight = image.height();
	var originalRatio = originalWidth/originalHeight;

	var container = imageContainer;
	var containerWidth = container.width();
	var containerHeight = container.height();

	// console.log(image.attr("src") + originalRatio);
	console.log($(imageContainer).attr("class"));

	container.css({
		"position": 'relative',
		"overflow": 'hidden'
	});

	image.css('position', 'absolute');

	if (originalRatio > 1) {

		image.height(containerHeight);

		image.width(containerHeight*originalRatio);

	} else if (originalRatio < 1){

		image.width(containerWidth);

		image.height(containerWidth/originalRatio);

	} else if (originalRatio == 1){
		image.width(containerWidth);
		image.height(containerHeight);
	}

	hVCenter($(imageContainer).attr("class") + ">img");

}