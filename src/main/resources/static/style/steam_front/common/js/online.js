$(document).ready(function() {
//	var menuYloc2 = $("#izl_rmenu").offset().top;
//	alert(menuYloc2);
//	$(window).scroll(function() {
//		var offsetTop2 = menuYloc2 + $(window).scrollTop() + "px";
//		$("#izl_rmenu").animate({
//			top : offsetTop2
//		}, {
//			duration : 600,
//			queue : false
//		});
//	});
	
	 
	 
	$("#izl_rmenu2").hover(function() {
		 $(this).fadeOut("slow");
		 $("#izl_rmenu").fadeIn("slow");	  
	}, function() {
		
	});
	
	$("#izl_rmenu").hover(function() {  
	}, function() {
		$(this).fadeOut(3000);
		$("#izl_rmenu2").fadeIn(3600);
	});
});