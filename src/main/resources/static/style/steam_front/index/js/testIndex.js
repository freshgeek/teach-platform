$(function() {
	$.get("/loadLoginUser", function(data) {
		if (null != data && data.id != null && data.imageUrl != null) {
			$("#btn_login2").remove();
			$(".homework").remove();
			$(".personal").remove();
			$("#x_gongzuoshi").append('<a class="personal_p">个人中心</a>');

			$("#x_homeworkss").append('<a class="p_homework">作业</a>');
			$(".p_head_self_photo").append('<a class="q_headPortrait"><img src="'+data.imageUrl+'" width=30 height=30/></a>');
			var str='<li><a href="/logout">退&nbsp;&nbsp;出</a></li>';
			if(data.identityId==1){
				$("#wn").append('<li><a href="/teacher/view/userdatum">个人资料</a></li><li><a href="/back/index">后台登录</a></li><li><a href="/teacher/view/cpttea">赛事分析</a></li>'+str);
				$(".p_homework").attr("href","/teacher/view/index/homework");
				$("#personalCenter").append('<li><a href="/teacher/view/userworks/userworks">我的作品</a></li><li><a href="/teacher/view/index/homework">我的作业</a></li><li><a href="/teacher/view/userdatum">个人信息</a></li>');

			}else if(data.identityId==4){
				$("#wn").append('<li><a href="/teacher/view/userdatum">个人资料</a></li><li><a href="/back/index">后台登录</a></li>'+str);
				$(".p_homework").attr("href","/teacher/view/index/homework");
				$("#personalCenter").append('<li><a href="/teacher/view/userworks/userworks">我的作品</a></li><li><a href="/teacher/view/index/homework">我的作业</a></li><li><a href="/teacher/view/userdatum">个人信息</a></li>');

			}else if(data.identityId == 2){
				$("#wn").append('<li><a href="/student/view/userdatum">个人资料</a></li>'+str);
				$(".p_homework").attr("href","/student/view/index/homework");
				$("#personalCenter").append('<li><a href="/student/view/userworks/userworks">我的作品</a></li><li><a href="/student/view/index/homework">我的作业</a></li><li><a href="/student/view/userdatum">个人信息</a></li>');

			}else if(data.identityId == 6){
				$("#wn").append('<li><a href="/teacher/view/cpttea">赛事分析</a></li>'+str);
				$("#personalCenter").append('<li><a href="/teacher/view/userworks/userworks">我的作品</a></li><li><a href="/teacher/view/index/homework">我的作业</a></li><li><a href="/teacher/view/userdatum">个人信息</a></li>');

			}else{
				$("#wn").append(str);
			}
		}
	});
	$(".q_curriculum_nav li a").click(
			function() {
				$(".q_curriculum_nav ul li").removeClass(
						"q_curriculum_navHover");
				$(this).parent("li").addClass("q_curriculum_navHover");
				var typeId = $(this).attr("data");
				$.get("/indexShowCourseInfo/" + typeId, function(data) {
					$(".q_bag ul li").remove();
					$.each(data, function(i) {
						var htm = '<li data="' + data[i].id + '" userId="'
								+ data[i].integerUserId + '}"><div class="q_div">'
								+ '<a>' + '<img class="q_cover" src="'
								+ data[i].coverUrl + '" />' + '</a>'
								+ '<p class="q_coverName">' + data[i].name
								+ '</p>' + '<div class="q_worksCn">'
								+ '<img class="q_head" src="'
								+ data[i].fileUrl + '" />'
								+ '<a class="q_works_user">' + data[i].nickName
								+ '</a>' + '<span></span>'
								+ '<div class="clear"></div>' + '</div>'
								+ '</div></li>';
						$(".q_bag ul").append(htm);
					});
					$(".q_bag a[class='p_lookMore']").attr('href',
							'/resourcelibrary?sort=' + typeId);
				});
			});
	// $(".q_bag1 li").click(function() {
	//		
	// window.location.href = "/diyworksdetail/" + id;
	// });

	$(document).on('click', '.q_bag1 ul li', function() {
		var id = $(this).attr("data");
		window.open("/courseDetails/" + id);
	});

	$(document).on('click', ".p_bag1 li", function() {
		var id = $(this).attr("data");
		window.open("/worksdetail/" + id);

	});

	$(document).on('click', "#percpt li", function() {
		var id = $(this).attr("data");
		window.open("/worksdetail/");

	});

	$(document).on('click', ".p_diyWorks li", function() {
		var id = $(this).attr("data");
		window.open("/diyworksdetail/" + id);

	});

	$(document).on('click', "#diycpt li", function() {
		var id = $(this).attr("data");
		window.open("/diyworksdetail/" + id);

	});

});