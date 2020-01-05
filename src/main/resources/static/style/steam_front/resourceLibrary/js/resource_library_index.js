$(function() {
	/***************************************************************************
	 * 搜索课程类型,并动态加载到ul下 data属性是类型id
	 **************************************************************************/
	$.get("/resourcelibrary/searchLibSort", null, function(data) {
		if (null != data || "" != data) {
			$.each(data, function(i) {
				$("#typeUl").append(
						'<li><a data="' + data[i].id + '">' + data[i].name
								+ '</a></li>');
			});
		}
	});
	// 鼠标进入事件,判断隐藏的课程介绍p标签是否为空,不为空则把p标签展示
	$("body").on("mouseenter", "#typeChoose .showTypeDiv", function() {
		// 设置阴影效果
		$(this).css({
			"-moz-box-shadow" : "-1px 1px 22px #A8A8A8",
			"-webkit-box-shadow" : " -1px 1px 22px #A8A8A8",
			"box-shadow" : "-1px 1px 22px #A8A8A8"
		});

		// 若当前鼠标进入的课程介绍不为空的话,则把p标签介绍显示出来
//		if (!$(this).find("p[class='course-intro-p']").text() == "") {
//
//			$(this).find("p[class='course-intro-p']").slideToggle(300);
//			$(this).css({
//				"position" : "absolute",
//				"z-index" : "999",
//				"background" : "#fff"
//			});
//		}

	});
	// 鼠标划出事件,把p标签隐藏,并且去掉阴影效果
	$("body").on("mouseleave", "#typeChoose .showTypeDiv", function() {
		$(this).css({
			"-moz-box-shadow" : "",
			"-webkit-box-shadow" : "",
			"box-shadow" : ""
		});
//		if (!$(this).find("p[class='course-intro-p']").text() == "") {
//			$(this).css({
//				"position" : "absolute",
//				"z-index" : "0",
//				"background" : "#fff"
//			});
//			$(this).find("p[class='course-intro-p']").slideToggle(300);
//		}

	});

	// 加载默认课程
	ajaxLoadCourseInfo("全部", null, "最新", 1);

	// 分类类型点击事件,进行课程的查找
	$("body").on("click", "#typeUl li a", function() {
		getRequestParameter("#typeUl", this);

	});
	// 排序类型点击事件
	$("body").on("click", "#sortUl li a", function() {
		getRequestParameter("#sortUl", this);
	});

	/***************************************************************************
	 * @description:抽取出来的公共方法,避免重复代码,获取li的参数
	 * @param ulIdStr:ul的id字符串
	 *            clickObj:当前点击li对象
	 **************************************************************************/
	function getRequestParameter(ulIdStr, clickObj) {
		$(ulIdStr + " li a").removeClass("checked_a");// 移除其它a的class
		$(clickObj).addClass("checked_a");// 给当前点击的li下a添加class
		// 若为分类li点击
		var libsortName = "";// 获取text值
		var libsortId = "";// 获取分类id
		var sort = "";// 获取排序ul下li a的值
		if ("#typeUl" == ulIdStr) {
			libsortName = $(clickObj).text();
			libsortId = $(clickObj).attr("data");
			sort = $("#sortUl li a").filter(".checked_a").text();
			ajaxLoadCourseInfo(libsortName, libsortId, sort, 1);
			// 排序li点击
		} else {
			var obj = $("#typeUl li a").filter(".checked_a");
			libsortName = obj.text();
			libsortId = obj.attr("data");
			sort = $(clickObj).text();
			ajaxLoadCourseInfo(libsortName, libsortId, sort, 1);
		}
	}

	/***************************************************************************
	 * @description:ajax请求，请求课程信息
	 * @param :
	 *            libsortName:分类名称 libsortId:分类id sort:排序名称
	 **************************************************************************/
	function ajaxLoadCourseInfo(libsortName, libsortId, sort, pageNo) {
		$.post("/resourcelibrary/searchCourseInformation", {
			"libsortName" : libsortName,
			"libsortId" : libsortId,
			"sort" : sort,
			"pageNo" : pageNo
		}, function(data) {
			 
			// 先把之前ul下的所有li移除
			$("#typeChoose ul li").remove();
			if(0==data.data.length){
				$(".h_pp_no_data").show();	 
			}else{
				$(".h_pp_no_data").hide();
			}
			// 对ajax请求返回的结果进行遍历
			loadPage(data.count);
			dynamicLoadCourse(data.data);
		});
	}

	/***************************************************************************
	 * @description 动态的加载分页
	 * @param count:
	 *            数据总数
	 **************************************************************************/
	function loadPage(count) {
		// 把class 为page的div下的所有子元素删除
		$(".page").children().remove();
		var pageCount = count % 8;
		// 大于8才显示分页标签
		if (count > 8) {
			$(".page")
					.append(
							'<a id="index_page" class="disabled_page front_page">首页</a><a id="last_page" class="disabled_page front_page">上一页</a>');
			pageCount = pageCount == 0 ? count / 8 : (count / 8 + 1);
			for (var i = 1; i < pageCount; i++) {

				if (i == 1) {
					$(".page").append(
							'<a class="active text-page-tag">' + i + '</a>');
				} else {
					if (i == parseInt(pageCount)) {
						$(".page").append(
								'<a class="text-page-tag back_pageNo">' + i
										+ '</a>');
					} else {
						$(".page").append(
								'<a class="text-page-tag">' + i + '</a>');
					}

				}

			}

			$(".page").append(
					'<a id="next_page" class="back_page">下一页</a><a id="end_page" data="'
							+ parseInt(pageCount)
							+ '" class="back_page">尾页</a>');
		}
	}

	/***************************************************************************
	 * @description:抽取出来的方法。对ajax回调返回的数据进行拼接
	 * @param:courseData:ajax回调函数返回的数据
	 **************************************************************************/
	function dynamicLoadCourse(courseData) {
		$
				.each(
						courseData,
						function(i, object) {
							var appendHtml='';
							if(i==0 || i==4){
								appendHtml='<li class="typeChoose-outer-li">';
							}else{
								appendHtml='<li class="typeChoose-center-li">';
							}
							 
							appendHtml +='<a href="/courseDetails/'
									+ object.id
									+ '"><div class="course-big-div"><div class="showTypeDiv"><div class="showTypeTile"><span class="course-type-span">'
									+ object.nickName
									+ '</span></div><div class="course_content"><div style="text-align:center;padding-top:10px;"><img class="course-img" src="'
									+ object.coverUrl
									+ '"></img><h3 class="course-card-name">'
									+ object.name
									+ '</h3><p class="course-intro-p">'
									+ object.introduction
									+ '</p>'
									+ '<p class="course-visit-num"><span class="num-span">'
									+ object.visitTime
									+ '人在学</span><img src="style/steam_front/resourceLibrary/images/period.png"></img><span class="period-span">'
									+ object.integerUserId
									+ '课时</span></p></div></div></div></div></a></li>';
							$("#typeChoose ul").append(appendHtml);
						});

	}
	/***************************************************************************
	 * @description:分页点击触发的ajax请求
	 * @param:pageNo:页码
	 **************************************************************************/
	function pageAjaxLoad(pageNo) {
		var obj = $("#typeUl li a").filter(".checked_a");
		var libSortName = obj.text();
		var libSortId = obj.attr("data");
		obj = $("#sortUl li a").filter(".checked_a");
		var sort = obj.text();
		$.post("/resourcelibrary/searchCourseInformation", {
			"libsortName" : libSortName,
			"libsortId" : libSortId,
			"sort" : sort,
			"pageNo" : pageNo
		}, function(data) {
			// 先把之前ul下的所有li移除
			$("#typeChoose ul li").remove();
			dynamicLoadCourse(data.data);
		});
	}
	// 分页的页码点击事件。比如 1 2 3 4......点击的时候
	$(".page").on("click", ".text-page-tag", function() {
		// 获取当前点击对象的text值,即页码
		var pageNo = $(this).text();
		// ajax加载数据
		pageAjaxLoad(pageNo);
		// 移除其它a标签 active class
		$(this).siblings().removeClass("active");
		// 给当前对象添加 active class
		$(this).addClass("active");

		if (pageNo != 1) {
			$(".page .front_page").removeClass("disabled_page");
		} else {
			$(".page .front_page").addClass("disabled_page");
		}
		if ($(this).hasClass("back_pageNo")) {
			$(".page .back_page").addClass("disabled_page");
		} else {
			$(".page .back_page").removeClass("disabled_page");
		}
	});
	// 首页分页点击
	$(".page").on("click", "#index_page", function() {
		if (!$(this).hasClass("disabled_page")) {
			pageAjaxLoad(1);

			// 移除其它页码的active
			$(".page .text-page-tag").filter(".active").removeClass("active");
			// 给第一页的页码加上active
			$(".page .text-page-tag").eq(0).addClass("active");
			// 上一页 首页加上disabled_page
			$(".page .front_page").addClass("disabled_page");

			if ($(".page .back_page").hasClass("disabled_page")) {
				$(".page .back_page").removeClass("disabled_page");
			}
		}
	});
	// 尾页点击
	$(".page").on("click", "#end_page", function() {
		if (!$(this).hasClass("disabled_page")) {
			pageAjaxLoad($(this).attr("data"));
			if ($(".page .front_page").hasClass("disabled_page")) {
				$(".page .front_page").removeClass("disabled_page");
			}
			// 移除其它页码的active
			$(".page .text-page-tag").filter(".active").removeClass("active");
			// 给最后一页的页码加上active
			$(".page .back_pageNo").addClass("active");
			// 下一页 尾页加上disabled_page
			$(".page .back_page").addClass("disabled_page");
		}
	});

	// 上一页点击
	$(".page").on("click", "#last_page", function() {
		if (!$(this).hasClass("disabled_page")) {
			var activeAObj = $(this).siblings().filter(".active");
			var pageNo = activeAObj.text();
			pageAjaxLoad(parseInt(pageNo)-1);
			activeAObj.removeClass("active");
			activeAObj.prev(".text-page-tag").addClass("active");
			if (pageNo - 1 == 1) {
				$(".page .front_page").addClass("disabled_page");
			}
			if (activeAObj.hasClass("back_pageNo")) {
				$(".page .back_page").removeClass("disabled_page");
			}
		}
	});
	// 下一页点击
	$(".page")
			.on(
					"click",
					"#next_page",
					function() {
						if (!$(this).hasClass("disabled_page")) {
							var activeAObj = $(this).siblings().filter(
									".active");
							var pageNo = activeAObj.text();
							pageAjaxLoad(parseInt(pageNo)+1);
							activeAObj.removeClass("active");
							activeAObj.next(".text-page-tag")
									.addClass("active");
							if (parseInt($(".page .back_pageNo").text()) == parseInt(pageNo) + 1) {
								$(".page .back_page").addClass("disabled_page");
							}
							if (pageNo >= 1) {
								$(".page .front_page").removeClass(
										"disabled_page");
							}
						}
					});
});