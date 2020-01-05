$(function(){
	

//  var $ = require("jquery1.7");
//	
//	var BlackBox = require("blackbox");
//	var alertBox = new BlackBox();
//	var box = new BlackBox();
	var i=1;
	var flag = 1;
 
	
	$("#p_header_contain").remove();
	$(".q_login").css("top","42px");
	
	$(function() {				 
		/*页面轮播*/
		flag=1;	//控制轮播变量，1：轮播，0：暂停
		var times=5000;	//间隔时间
		var sum=4;		//总页数
		$(".p_fcon").eq(0).show();
		$(".p_fcon").eq(0).addClass("fconShow");
		$("#p_xiaoyuan li").eq(0).siblings().css("background-image","url(style/steam_front/index/images/huidian_05.png)");
		setInterval(function(){pageFun(sum);},times);
		initWindow();
	});
	
	
	$(document).on("mouseover mouseleave",".p_fcon",function(e){
		if(e.type=="mouseover"){	//鼠标进入图片区域，停止轮播
			i=$(this).index();
			flag=0
		}else if(e.type=="mouseleave"){	//鼠标退出图片区域，继续轮播
			flag=1;
			i=$(this).index();
		}
	});



	$("#p_xiaoyuan li").mousedown(function(){		//鼠标点击小圆圈，变化图片
		i=$(this).index();
		stypleFn();
	});
	

	
	//图片与小圆点变换
	function stypleFn(){
			//控制图片
			$(".p_fcon").eq(i).fadeIn();
			$(".p_fcon").eq(i).siblings().hide();
			$(".p_fcon").removeClass("fconShow");
			$(".p_fcon").eq(i).addClass("fconShow");
			//控制小圆点
			$("#p_xiaoyuan li").eq(i).fadeIn().css("background-image","url(style/steam_front/index/images/baidian_03.png)");
			$("#p_xiaoyuan li").eq(i).siblings().css("background-image","url(style/steam_front/index/images/huidian_05.png)");
	}
	
	//轮播实现
	function pageFun(sum){		
		if(flag==1){
			stypleFn();
			i++;
			if(i>=sum){
				i=0;
				}
		}
	}
	
//		(function(){
//			
//									
//			//加载视频
//			steamBaseService.loadResInfoIndex(function(requestData) {
//				if(requestData.isError) {
//					return;
//				}
//				
//				var result = requestData.result;
//				if (result.pageCount > 0) {
//					var data = result.data;
//					var htm = $("#h_index_video").html();
//					var str = "";
//					for (var i in data) {
//						var s = htm;
//						s = s.replace("{fileUrl}", data[i].fileUrl)
//							.replace("{name}", data[i].name)
//							.replace("{updateTime}", data[i].updateTime.format("yyyy-MM-dd"));
//						str += s;
//					}
//					$("#h_index_video").prev().html(str);
//					initWindow();
//				}
//			});
//			initWindow();
//		}())
		
		

	
	//加载视频
//	$(document).on("click", ".p_square_bottom_left li a", function() {
//		var url = $(this).attr("data");
//		var content = '<object pluginspage="http://www.macromedia.com/go/getflashplayer" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0" width="100%" height="100%" id="ckplayer_a1" name="ckplayer_a1" align="middle"><param name="allowScriptAccess" value="always"><param name="allowFullScreen" value="true"><param name="quality" value="high"><param name="bgcolor" value="#FFF"><param name="wmode" value="transparent"><param name="movie" value="/files/playplug/ckplayer/ckplayer.swf"><param name="flashvars" value="f={fileUrl}&a=&s=0&c=0&x=&i=&d=&u=&l=&r=&t=10|10&y=&z=&e=2&v=80&p=1&h=3&q=&m=&o=&w=&g=&j=&k=30|60&n=&wh=&lv=0&loaded=loadedHandler"><embed allowscriptaccess="always" allowfullscreen="true" quality="high" bgcolor="#FFF" wmode="transparent" src="/files/playplug/ckplayer/ckplayer.swf" flashvars="f={fileUrl}&a=&s=0&c=0&x=&i=&d=&u=&l=&r=&t=10|10&y=&z=&e=2&v=80&p=1&h=3&q=&m=&o=&w=&g=&j=&k=30|60&n=&wh=&lv=0&loaded=loadedHandler&my_url=http%3A%2F%2F192.168.150.24%3A8888%2Fcommon%2Fview%2FpLessonDetail%2F3" width="100%" height="100%" name="ckplayer_a1" id="ckplayer_a1" align="middle" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer"></object>';
//		content = content.replaceAll("{fileUrl}", url);
//		loadContentForWin(content);
//	});
//	
//	function loadContentForWin(content){
//		$.post("/base/from_templet", {
//			templet_url : "/common/resViewWindow"
//		}, function(data) {
//			
//			data = data.replace("{windowStyle}", "width: 1000px;margin-top: 75px;height: " + ($(window).height() - 40) + "px;");
//			data = data.replace("{contentStyle}", "width: 985px;height: " + ($(window).height() - 85) + "px;");
//			data = data.replace("{fileViewData}", content);
//			
//			box.popup(data, function(content) {
//				//关闭
//				content.find(".z_contain_close").click(function () {
//					box.boxClose();
//				});
//			});
//		});
//	}
	
	window.onresize = function(){
		initWindow();
	}
	
	function initWindow(){	//调整高度
		var imgHeight = $(".fconShow a").eq(0).height();
		$(".p_lunbo").height(imgHeight+"px");
		$("#p_lunbomain").height(imgHeight+"px");
		
	}
});
