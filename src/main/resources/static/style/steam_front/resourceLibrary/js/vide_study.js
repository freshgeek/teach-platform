// JavaScript Document
define(function(require) {
    var $ = require("jquery1.7");
    var utils = require("utils");
    //var Map=utils.Map;
    var BlackBox = require("blackbox");
    var steamBaseServicee = require("bean/steamBaseService");
    var focusFromService = require("bean/focusFromService");
    var worksReplyService = require("bean/worksReplyService");
    var focusCourseService = require("bean/focusCourseService");
    var userService = require("bean/userService");
    var loginUserId = $("#loginUserId").val();
    var identityId = $("#identityId").val();
    var loginNickName = $("#loginNickName").val();
    var courseInfoId = $("#courseId").attr("data");	// 课件ID
    var resId = $("#resId").attr("data");			// 视频ID
    var showdisscuss = $("#showdisscuss").val();	// 问题ID
    var showworks = $("#showworks").val();	// 作品ID
    var showworksView = $("#showworksView").val();	// 作品ID
    var Map=utils.Map;
    var discussMap = new Map();
    var worksMap = new Map();
    var blackBox = new BlackBox();
    var closeFlag = 0;
    (function(){

        var url=window.location.href;
        var download=url.substring(url.lastIndexOf("/")+1,url.length);
        // 为1则表示是下载资料操作
        if(download==1){
            $(".q_mainUl").css("margin-left","-4708px");

            $("#q_ask").hide();
            $(".p_taolun_nav_question").hide();
            $(".p_taolun_right").hide();
            $(".p_taolun_nav_ul li a").removeClass("p_current");
            $(".p_taolun_nav_ul a:last").addClass("p_current");
            $("#q_discuss").hide();
            $(".p_taolun_nav_question").hide();
            $(".q_nav_works_add").hide();
        }
        if(loginUserId==2 || !(parseInt(identityId)>0)){
            window.location.reload();
        }

        var focusCourse = {
            studentId:loginUserId,
            courseId:courseInfoId
        }
        focusCourseService.checkFocusCourse(focusCourse,function(requestData){
            if(requestData.isError){
                return;
            }
            var data = requestData.result;
            closeFlag=1;
            if(showdisscuss != "" ) {
                $(".q_mainUl").css("margin-left","-2354px");
                $("#q_ask").hide();
                $(".p_taolun_nav_question").show();
                $(".p_taolun_right").show();
                $(".p_taolun_nav_ul li a").removeClass("p_current");
                $("#disscuss").addClass("p_current");
                $(".q_comment").hide();
                $("#q_discuss").show();
            }
            if(showworksView != "") {
                $(".q_mainUl").css("margin-left","-3531px");
                $(".q_works .q_ask").hide();
                $(".p_taolun_nav_question").hide();
                $(".q_works .p_taolun_right").show();
                $(".p_taolun_nav_ul li a").removeClass("p_current");

                $("#q_discuss").hide();
                $(".q_nav_works_add").show();
                $("#showworks").val(showworksView);
                showworks = $("#showworks").val();

            }
            try {
                fnLoadVoido();	// 加载视频和相关数据
                getRecord(); // 获取用户的播放记录
                fnDiscuss(); // 加载课程讨论列表
                fnWorks(1);	// 加载作品列表
            } catch (e) {
                // TODO: handle exception
            }

        })


    })();

    $(document).on("click",".x_zhang",function(){
        var data = $(this).attr("data");
        location.href = "/videoStudy/"+courseInfoId+"/"+data+"/0";
    })

    $(document).on("click",".q_disdiscus .p_taolun_left_more a",function(){
        var pageNo = parseInt($("#discuss_pageNo").val());
        var discuss_pageCount = parseInt($("#discuss_pageCount").val());
        pageNo = pageNo+1;
        if(pageNo<=discuss_pageCount){
            fnDiscuss(pageNo);
            $("#discuss_pageNo").val(pageNo);
        }
    });

    $(document).on("click",".q_works .p_taolun_left_more a",function(){
        var pageNo = parseInt($("#works_pageNo").val());
        var discuss_pageCount = parseInt($("#works_pageCount").val());
        pageNo = pageNo+1;
        if(pageNo<=discuss_pageCount){
            fnWorks(pageNo);
            $("#works_pageNo").val(pageNo);
        }
    });
    // 加载视频和相关数据
    function fnLoadVoido(){
        steamBaseServicee.findCourseContent(courseInfoId,function(requestData){
            if(requestData.isError){
                return;
            }
            var map = new Map();
            var data = requestData.result;
            var courseInfo = data.CourseInfo;
            var resInfoList = data.ResInfoList;
            var otherList = data.OtherList;
            var user = data.User;
            $("#authorId").val(user.id);
            $("#q_courseName").text(courseInfo.name);
            var state = courseInfo.state;
            if(state==2){
                $(".q_comment").show();
                $("#state").val(state);
            }
            var model=$("#q_navModel ul").html();
            var rsHtml = "";
            for(var i in resInfoList){
                var h=model;
                h = h.replaceAll("{name}",resInfoList[i].name);
                h = h.replaceAll("{id}",resInfoList[i].id);
                rsHtml+=h;
                map.put(resInfoList[i].id,resInfoList[i]);

            }
            $("#q_nav").html(rsHtml);
            if(resId+"" != ""){
                $('#q_nav li[data='+resId+']').addClass("clickLi");
                var data = map.get(resId);
                var thisFileUrl = data.fileUrl;
                // var fileUrl = "http://www.steamaker.cn"+thisFileUrl;	// 视频文件路径
                var fileUrl = "https://cdn.makeymonkey.com/classes-room"+thisFileUrl;	// 视频文件路径

                flashvars.f =	fileUrl;
                flashvars.p = 0;
                video[0]=fileUrl+"->video/mp4"
                //video[0]="http://movie.ks.js.cn/flv/other/1_0.mp4->video/mp4"
                CKobject.embed('/style/ckpayler6_6/ckplayer/ckplayer.swf','fileContent','ckplayer_a1','100%','100%',false,flashvars,video,params);
            }


            // 加载课件资源
            var otherHtm = ""
            for(var i in otherList) {
                otherHtm += '<li><a>'+otherList[i].name+'</a><div class="p_span"><span class="p_span1" data="'+otherList[i].fileUrl+'">下载资源</span></div></li>'
            }

            $(".f_select_file ul").html(otherHtm);
        });
    }
    // 下载资源
    $(document).on('click', '.p_span1', function(){
        var url = $(this).attr("data");
        window.open(url, "_blank");
    })

    // 加载课程讨论列表
    function fnDiscuss(pageNo){

        try {
            var model = $(".q_disdiscus .q_discuss_Ul_model ul").html();
            var pageSize = 10;
            if(pageNo==undefined){
                pageNo = 1;
            }
            focusFromService.loadDiscuss(resId,pageNo,pageSize,function(requestData){
                if(requestData.isError){
                    return;
                }
                var result=requestData.result;
                var replyCount = result.replyCount;
                if(result.resultList!=null){

                    $(".q_disdiscus .p_taolun_rightContent").show();
                    $(".q_disdiscus .p_taolun_rightError").hide();


                    var data = result.resultList.data;	// 讨论列表

                    var pageCount = result.resultList.pageCount;

                    var htmls = "";
                    for(var i in data){
                        var mod = model;
                        mod = mod.replaceAll("{id}",data[i].id);
                        mod = mod.replaceAll("{title}",data[i].title);
                        htmls += mod;
                        discussMap.put(data[i].id,data[i]);
                    }
                    $("#discuss_pageCount").val(pageCount);
                    if(pageCount==pageNo){
// $(".q_disdiscus .p_taolun_left_more").hide();
                    }
                    $(".q_disdiscus .q_discuss_Ul ul").append(htmls);
                    fnDiscussContent();
                    for(var i in replyCount){	// 回复数
                        $('.q_disdiscus .p_taolun_left_list li a[data="'+replyCount[i][0]+'"]').siblings(".p_floater").find("span").text(replyCount[i][1]);
                    }
                }else{
                    $(".q_disdiscus .p_taolun_left_more").hide();
                    $(".q_disdiscus .p_taolun_rightContent").hide();
                    $(".q_disdiscus .p_taolun_rightError").show();
                }

            });
        } catch (e) {
            // TODO: handle exception
        }
    }



    $(this).on("click",".q_disdiscus .p_taolun_left_list li a",function(){
        $(".q_disdiscus .q_ask").hide();
        $(".p_taolun_right").show();
        var id = $(this).attr("data");
        $("#showdisscuss").val(id);
        if(id>0){
            fnDiscussContent();
        }
    })
    // 加载讨论的内容
    function fnDiscussContent(){
        var discussId =  "";
        discussId =  $("#showdisscuss").val();
        var content  = "";
        if(discussId == ""){
            var id=$(".q_disdiscus .q_discuss_Ul ul li a").eq(0).attr("data");
            content = discussMap.get(id);
        }else{
            content = discussMap.get(discussId);
        }
        if(content!=""){
            $(".q_disdiscus .p_wentiqu_title").attr("title",content.title);
            $(".q_disdiscus .p_wentiqu_title").attr("data",content.id);
            $(".q_disdiscus .p_wentiqu_title").text(content.title);
            $(".q_disdiscus .p_wentiqu_intro").find("font").text(content.updateTime.format("yyyy-MM-dd"));
            $(".q_disdiscus .p_wentiqu_intro").find("span").text(content.userName);
            $(".q_disdiscus .p_wentiqu_intro").find("span").attr("data",content.userId);
            $(".q_disdiscus .p_wentiqu_p").html(content.content);
            $('.q_disdiscus .q_discuss_Ul ul li a').removeClass("discussAClick");
            $('.q_disdiscus .q_discuss_Ul ul li').removeClass("discussLiClick");
            $('.q_disdiscus .q_discuss_Ul ul li a[data='+content.id+']').addClass("discussAClick");
            $('.q_disdiscus .q_discuss_Ul ul li a[data='+content.id+']').parent().addClass("discussLiClick");
            if(showdisscuss == "" ) {
                $("#showdisscuss").val(content.id);
            }
            fnReply();
        }else{
            var pageNo = parseInt($("#discuss_pageNo").val());
            var discuss_pageCount = parseInt($("#discuss_pageCount").val());
            pageNo = pageNo+1;
            if(pageNo<=discuss_pageCount){
                fnDiscuss(pageNo);
                $("#discuss_pageNo").val(pageNo);
            }
        }
    }

    // 加载回复区内容
    function fnReply(pageNo){
        try {
            var discussId = $("#showdisscuss").val();
            var pageSize = 5;
            var pageCount = 0;
            var totalCount = 0;
            if(pageNo==undefined){
                pageNo = 1;
            }
            focusFromService.loadReply(discussId,pageNo,pageSize,function(requestData){
                if(requestData.isError){
                    return;
                }
                var data = requestData.result;
                if(data != null){
                    var ipage = data.ipage.data;
                    var userMap = data.userMap;
                    var userDetailMap = data.userDetailMap;
                    var model = $(".q_disdiscus .q_hfModel ul").html();
                    var imgHtml = '<img src="images/img1_03.gif" style="width:83px;height:83px;">';
                    var htmls = "";
                    for(var i in ipage){
                        var mod = model;
                        var imgh = imgHtml;
                        var identityId = userMap[ipage[i].userId].identityId;	// 身份
                        // 1：老师，2：学生
                        mod = mod.replaceAll("{id}",ipage[i].id);
                        mod = mod.replaceAll("{content}",ipage[i].content);
                        mod = mod.replaceAll("{updateTime}",ipage[i].updateTime.format("yyyy-MM-dd hh:mm"));
                        mod = mod.replaceAll("{nickname}",userMap[ipage[i].userId].nickname);
                        imgh = imgh.replaceAll('images/img1_03.gif',userMap[ipage[i].userId].imageUrl);
                        mod = mod.replaceAll("{img}",imgh);
                        if(identityId==2){
                            mod = mod.replaceAll("{position}","学生");
                        }else if(identityId==1){
                            if(userDetailMap == null || userDetailMap[ipage[i].userId] == null) {
                                mod = mod.replaceAll("{position}","");
                            } else {
                                mod = mod.replaceAll("{position}",userDetailMap[ipage[i].userId].position);
                            }
                        }else{
                            mod = mod.replaceAll("{position}","");
                        }

                        htmls += mod;
                    }
                    $(".q_disdiscus .q_hf ul").html(htmls);
                    pageCount = data.ipage.pageCount;
                    totalCount = data.ipage.totalCount;
                    $("#totalCount").text(totalCount);

                    var pageHtml = "";
                    $("#q_taolun_page .pageCount").val(pageCount);
                    $("#q_taolun_page .pageNo").val(pageNo);
                    $("#q_taolun_page .totalCount").val(totalCount);
                    var pageHtml = "";
                    if(pageCount>10){
                        for(var i=1;i<8;i++){
                            var p = "";
                            if(i==pageNo){
                                p='<a class="pageClick">'+i+'</a>';
                            }else{
                                p = '<a>'+i+'</a>';
                            }
                            pageHtml += p;
                        }
                        pageHtml+='<a>...</a>';
                        pageHtml+='<a>'+pageCount+'</a>';

                    }else{
                        for(var i=1;i<pageCount+1;i++){
                            var p = "";
                            if(i==pageNo){
                                p='<a class="pageClick">'+i+'</a>';
                            }else{
                                p = '<a>'+i+'</a>';
                            }
                            pageHtml += p;
                        }
                    }
                    $(".q_disdiscus .p_taolun_yema").show();
                    $("#q_taolun_page").show();
                    $("#q_taolun_page .page_number").html(pageHtml);
                }else{
                    $(".q_disdiscus .p_taolun_yema").hide();
                    $("#totalCount").text(0);
                    $(".q_disdiscus .q_hf ul").html("");
                }

            });
        } catch (e) {
            // TODO: handle exception
        }
    }

    $(document).on("click",".p_huifuqu_time a",function(){
        var userName = $(this).parents(".p_huifuqu").find("span").text();
        editor_reply.setContent('回复 '+userName+':');
        editor_reply.focus(true);
    });


    $(document).on("click","#q_taolun_page .page_number a",function(){
        var pageNo = parseInt($(this).text());
        if(pageNo>0){
            var id = $(".discussAClick").attr("data");
            $("#showdisscuss").val(id);
            fnReply(pageNo);
        }
    });

    $(document).on("click","#q_taolun_page .page_action",function(){
        var pageNo = parseInt($("#q_taolun_page .page_input").val());
        var pageCount = parseInt($("#q_taolun_page .pageCount").val());
        if(pageNo<=pageCount){
            var id = $(".discussAClick").attr("data");
            $("#showdisscuss").val(id);
            fnReply(pageNo);
        }
    });

    $(document).on("click",".q_disdiscus .p_fawen",function(){	// 我要发问
        var noticTitle = $(".q_disdiscus .noticTitle").val();
        var content = 	editor.getContent();
        var authorId= $("#authorId").val();
        var error = "";
        if(loginUserId==2){
            error="请先登录!";
        }
        if(noticTitle==""){
            error="讨论标题不能为空!";
        }
        if(noticTitle.length>30){
            error="标题字数不能超过30个";
        }
        if(content==""){
            error="内容不能为空!";
        }
        if(error!=""){
            blackBox.alert(error, {
                title: '提示',
                value: '确认'
            });
            return;
        }

        var discuss = {
            userId : loginUserId,
            userName : loginNickName,
            courseId : courseInfoId,
            resId : resId,
            teacherId : authorId,
            title : noticTitle,
            content : content
        }
        focusFromService.addDiscuss(discuss,function(){
            $("#showdisscuss").val("");
            $(".q_disdiscus .q_discuss_Ul ul").text("");
            $("#discuss_pageNo").val(1);
            $("#noticTitle").val("");
            editor.setContent("");
            fnDiscuss();
            $(".q_disdiscus .p_taolun_right").show();
            $(".q_disdiscus .q_ask").hide();
        });
        document.getElementById("wj").style.display="";
    });



    // 提交 回复
    function fnAddReply(){
        var userId = $(".p_wentiqu_intro").find("span").attr("data");
        var discussId = $(".p_wentiqu_title").attr("data");
        if(discussId==undefined){
            blackBox.alert("未选定讨论", {
                title: '提示',
                value: '确认'
            });
            return;
        }
        var content = editor_reply.getContent();	// 内容 ps:
        // editor_reply是页面定义的一个编辑器实现对象
        if(content.length>255){
            blackBox.alert("内容过多", {
                title: '提示',
                value: '确认'
            });
        }
        if(content.length>0){
            var reply = {
                userId : loginUserId,

                discussId : discussId,	// 提问ID

                content : content
            }
            focusFromService.addReply(reply);
            $("#showdisscuss").val(discussId);
            fnDiscussContent();
            $(".q_disdiscus .q_discuss_Ul ul").text("");
            $(".q_disdiscus #discuss_pageNo").val(1);
            fnDiscuss();
            editor_reply.setContent("");
        }else{
            blackBox.alert("内容不能为空", {
                title: '提示',
                value: '确认'
            });
        }
    }

    $(document).on("click",".q_disdiscus .p_ueditor a",function(){
        if(loginUserId==2){
            blackBox.alert("请先登陆", {
                title: '提示',
                value: '确认'
            });
            return;
        }
        fnAddReply();
    });

    $(document).on("click",".q_wd_huifu a",function(){
        if(loginUserId==2){
            blackBox.alert("请先登陆", {
                title: '提示',
                value: '确认'
            });
            return;
        }
        fnAddWorksReply();
    });

    // 模块切换
    $(document).on("click",".p_taolun_nav_ul li a,.p_taolun_nav_question a,.q_nav_works_add a",function(){
        var data = $(this).text();
        var state = $("#state").val();
        switch (data) {
            case "课件":
                $(".q_mainUl").css("margin-left","0");
                $(".p_taolun_nav_question").hide();
                $(".q_nav_works_add").hide();
                $(".p_taolun_nav_ul li a").removeClass("p_current");
                $(this).addClass("p_current");
                $("#q_discuss").hide();
                break;
            case "边玩边学":
                window.open("/scratch/index.html","_blank");
                break;
            case "讨论区":
                $(".q_mainUl").css("margin-left","-2354px");
                CKobject.getObjectById('ckplayer_a1').videoPause();
                $(".q_disdiscus .q_ask").hide();
                $(".q_nav_works_add").hide();
                $(".p_taolun_nav_question").show();
                $(".q_disdiscus .p_taolun_right").show();
                $(".p_taolun_nav_ul li a").removeClass("p_current");
                $(this).addClass("p_current");
                $("#q_discuss").show();
                break;
            case "作品区":
                $(".q_mainUl").css("margin-left","-3531px");
                CKobject.getObjectById('ckplayer_a1').videoPause();
                $(".q_works .q_ask").hide();
                $(".p_taolun_nav_question").hide();
                $(".q_works .p_taolun_right").show();
                $(".p_taolun_nav_ul li a").removeClass("p_current");
                $(this).addClass("p_current");
                $("#q_discuss").hide();
                $(".q_nav_works_add").show();
                break;
            case "资源下载":
                $(".q_mainUl").css("margin-left","-4708px");
                CKobject.getObjectById('ckplayer_a1').videoPause();
                $("#q_ask").hide();
                $(".p_taolun_nav_question").hide();
                $(".p_taolun_right").hide();
                $(".p_taolun_nav_ul li a").removeClass("p_current");
                $(this).addClass("p_current");
                $("#q_discuss").hide();
                $(".p_taolun_nav_question").hide();
                $(".q_nav_works_add").hide();
                break;
            case "我要发问":
                $(".q_disdiscus .p_taolun_right").hide();
                $(".q_disdiscus .q_nav_works_add").hide();
                $(".q_disdiscus .q_ask").show();
                $(".p_taolun_nav_question").show();
                $(".q_comment").hide();
                break;
            case "上传作品":
                $(".q_works .p_taolun_right").hide();
                $(".q_works .q_nav_works_add").hide();
                $(".q_works .q_ask").show();
                $(".q_works .p_taolun_nav_question").show();
                $(".q_comment").hide();
                loadTeamUserList();
                $(".q_teaml").text(loginNickName);
                break;
            default:
                break;
        }
    });

    if(closeFlag==1){
        $(window).bind('beforeunload',function(){	// 离开当前页面，保存视频播放时间
            var time = parseInt(CKobject.getObjectById('ckplayer_a1').getStatus().time);
            if(loginUserId!=2 && time>0){
                var videoRecord = {
                    userId : loginUserId,
                    resInfoId : resId,
                    recordTime : time
                }
                var id = $("#videoRecoudId").val();
                if(id!=undefined && id!=""){
                    videoRecord.id=id;
                }
                focusFromService.saveRecord(videoRecord);
            }
        });
    }

    $(document).on("click","#q_action",function(){
        var time = CKobject.getObjectById('ckplayer_a1').getStatus().time;

        CKobject.getObjectById('ckplayer_a1').videoSeek(20);
    })

    function getRecord(){	// 获取用户的播放记录
        if(loginUserId!=2){
            focusFromService.findRecord(loginUserId,resId,function(requestData){
                if(requestData.isError){
                    return;
                }
                var data = requestData.result;
                if(data.length>0){
                    var rs = data[0];
                    $("#videoRecoudId").val(rs.id);
                    $("#recordTime").val(rs.recordTime);
                    var time = rs.recordTime;
                    var h = parseInt(time/60);
                    if(h<10) h = "0"+h;
                    var m = time%60;
                    if(m<10) m = "0"+m;
                    $(".q_record span").text(h+":"+m);
                    $(".q_record").show();
                }
            });
        }
    }

    // 跳到上次播放时间
    $(document).on("click","#q_record_yes",function(){
        var time = $("#recordTime").val();
        CKobject.getObjectById('ckplayer_a1').videoSeek(time);
        $(".q_record").hide();
    });
    $(document).on("click","#q_record_no",function(){
        $(".q_record").hide();
    });

    $(document).on("click",".p_wentiqu_huifu a",function(){
        editor_reply.focus();
    });

    /**
     * 作品区 begin
     */
    // 加载作品列表
    function fnWorks(pageNo){

        try {
            var model = '<li><a data="{id}" title="{title}">{title}</a></li>';
            var pageSize = 10;
            if(pageNo==undefined){
                pageNo = 1;
            }
            focusFromService.loadDiyWorks(courseInfoId,pageNo,pageSize,function(requestData){
                if(requestData.isError){
                    return;
                }
                var result=requestData.result;
                var data = result.data;
                var replyCount = result.replyCount;
                var pageCount = result.pageCount;
                if(result.totalCount>0){

                    $(".q_works .p_taolun_rightContent").show();
                    $(".q_works .p_taolun_rightError").hide();

                    var htmls = "";
                    for(var i in data){
                        var mod = model;
                        mod = mod.replaceAll("{id}",data[i].id);
                        mod = mod.replaceAll("{title}",data[i].worksName);
                        htmls += mod;
                        worksMap.put(data[i].id,data[i]);
                    }
                    $("#works_pageCount").val(pageCount);
                    if(pageCount==pageNo){
// $(".q_works .p_taolun_left_more").hide();
                    }
                    $(".q_works .q_discuss_Ul ul").append(htmls);
                    fnWorksContent();
                    loadMyRecord();
                }else{
                    $(".q_works .p_taolun_left_more").hide();
                    $(".q_works .p_taolun_rightContent").hide();
                    $(".q_works .p_taolun_rightError").show();
                }
            });
        } catch (e) {
            // TODO: handle exception
        }
    }

    // 加载作品的内容
    function fnWorksContent(){
        var content  = "";
        var worksId = "";
        worksId = $("#showworks").val();
        if(worksId == ""){
            var id=$(".q_works .q_discuss_Ul ul li a").eq(0).attr("data");
            content = worksMap.get(id);
        }else{
            content = worksMap.get(worksId);
        }
        if(content!=""){
            $(".q_works .q_works_dianzhan").text(content.likeNum);
            $(".q_works .p_wentiqu_title").attr("title",content.worksName);
            $(".q_works .p_wentiqu_title").attr("data",content.id);
            $(".q_works .p_wentiqu_title").text(content.worksName);
            $(".q_works .q_works_time").find("span").text(content.updateTime.format("yyyy-MM-dd"));
            $(".q_works .p_wentiqu_p").html(content.content);
            $('.q_works .q_discuss_Ul ul li a').removeClass("discussAClick");
            $('.q_works .q_discuss_Ul ul li').removeClass("discussLiClick");
            $('.q_works .q_discuss_Ul ul li a[data='+content.id+']').addClass("discussAClick");
            $('.q_works .q_discuss_Ul ul li a[data='+content.id+']').parent().addClass("discussLiClick");
            $(".q_works_dianzhan").text(content.likeNum);
            $("#showworks").val(content.id);
            var team = content.team;
            if(team!=null && team!=""){
                var teamArray = team.split(",");
                userService.loadUserListInIdList(teamArray,function(requestData){
                    if(requestData.isError){
                        return;
                    }
                    var data = requestData;
                    if(data!=null){
                        data = requestData.result;
                        var str = "";
                        for(var i in data){
                            if(i==0){
                                str += data[i].nickname;
                            }else{
                                str += "、"+data[i].nickname;
                            }
                        }
                        $(".q_works_tuanyuan span").text(str);
                    }
                })
            }
            fnWorksReply();
        }else{
            /*
             * var pageNo = parseInt($("#discuss_pageNo").val()); var
             * discuss_pageCount = parseInt($("#discuss_pageCount").val());
             * pageNo = pageNo+1; if(pageNo<=discuss_pageCount){
             * fnDiscuss(pageNo); $("#discuss_pageNo").val(pageNo); }
             */
        }
        if(showworksView!=""){		// 如果dw不为空
            var id = $(".q_works .discussLiClick").find("a").attr("data");
            if(id!=showworksView){
                var pageNo = parseInt($("#works_pageNo").val());
                var discuss_pageCount = parseInt($("#works_pageCount").val());
                pageNo = pageNo+1;
                if(pageNo<=discuss_pageCount){
                    fnWorks(pageNo);
                    $("#works_pageNo").val(pageNo);
                }
            }
        }
    }

    // 加载作品回复区内容
    function fnWorksReply(pageNo){
        try {
            var worksId = $("#showworks").val();
            var pageSize = 5;
            var pageCount = 0;
            var totalCount = 0;
            if(pageNo==undefined){
                pageNo = 1;
            }
            focusFromService.loadWorksReply(worksId,pageNo,pageSize,function(requestData){
                if(requestData.isError){
                    return;
                }
                var data = requestData.result;

                if(data != null){
                    var ipage = data.ipage.data;
                    var userMap = data.userMap;
                    var userDetailMap = data.userDetailMap;
                    var model = $(".q_wfModel ul").html();
                    var imgHtml = '<img src="images/img1_03.gif" style="width:83px;height:83px;">';
                    var htmls = "";
                    for(var i in ipage){
                        var mod = model;
                        var imgh = imgHtml;
                        var identityId = userMap[ipage[i].userId].identityId;	// 身份
                        // 1：老师，2：学生
                        mod = mod.replaceAll("{id}",ipage[i].id);
                        mod = mod.replaceAll("{content}",ipage[i].content);
                        mod = mod.replaceAll("{updateTime}",ipage[i].updateTime.format("yyyy-MM-dd hh:mm"));
                        mod = mod.replaceAll("{nickname}",userMap[ipage[i].userId].nickname);
                        imgh = imgh.replaceAll('images/img1_03.gif',userMap[ipage[i].userId].imageUrl);
                        mod = mod.replaceAll("{img}",imgh);
                        if(identityId==2){
                            mod = mod.replaceAll("{position}","学生");
                        }else if(identityId==1){
                            if(userDetailMap == null || userDetailMap[ipage[i].userId] == null) {
                                mod = mod.replaceAll("{position}","");
                            } else {
                                mod = mod.replaceAll("{position}",userDetailMap[ipage[i].userId].position);
                            }
                        }else{
                            mod = mod.replaceAll("{position}","");
                        }

                        htmls += mod;
                    }
                    $(".q_works .q_hf ul").html(htmls);
                    pageCount = data.ipage.pageCount;
                    totalCount = data.ipage.totalCount;
                    $(".q_wdhuifu_number").show();
                    $(".q_wdhuifu_number font").text(totalCount);
                    var pageHtml = "";
                    $("#q_works_page .pageCount").val(pageCount);
                    $("#q_works_page .pageNo").val(pageNo);
                    $("#q_works_page .totalCount").val(totalCount);
                    var pageHtml = "";
                    if(pageCount>10){
                        for(var i=1;i<8;i++){
                            var p = "";
                            if(i==pageNo){
                                p='<a class="pageClick">'+i+'</a>';
                            }else{
                                p = '<a>'+i+'</a>';
                            }
                            pageHtml += p;
                        }
                        pageHtml+='<a>...</a>';
                        pageHtml+='<a>'+pageCount+'</a>';

                    }else{
                        for(var i=1;i<pageCount+1;i++){
                            var p = "";
                            if(i==pageNo){
                                p='<a class="pageClick">'+i+'</a>';
                            }else{
                                p = '<a>'+i+'</a>';
                            }
                            pageHtml += p;
                        }
                    }
                    $(".q_works .p_taolun_yema").show();
                    $("#q_works_page").show();
                    $("#q_works_page .page_number").html(pageHtml);
                }else{
                    $(".q_works .p_taolun_yema").hide();
                    $(".q_works .q_hf ul").html("");
                    $(".q_wdhuifu_number").hide();
                }

            });
        } catch (e) {
            // TODO: handle exception
        }
    }

    // 提交 回复
    function fnAddWorksReply(){
        var userId = $(".q_works .p_wentiqu_intro").find("span").attr("data");
        var worksId = $(".q_works .p_wentiqu_title").attr("data");
        if(worksId==undefined){
            blackBox.alert("未选定作品", {
                title: '提示',
                value: '确认'
            });
            return;
        }

        var content = $(".q_wd_huifu textarea").val();	// 内容
        if(content.length>70){
            blackBox.alert("评论字数不能超过70字", {
                title: '提示',
                value: '确认'
            });
            return;
        }
        if(content.length>0){
            var worksReply = {
                userId : loginUserId,

                worksId : worksId,	// 提问ID

                content : content
            }
            focusFromService.addWorksReply(worksReply);
            $(".q_works .q_discuss_Ul ul").text("");
            $("#works_pageNo").val(1);
            $("#showworks").val(worksId);
            fnWorks(1);
        }else{
            blackBox.alert("内容不能为空", {
                title: '提示',
                value: '确认'
            });
        }
    }

    $(this).on("click",".q_works .p_taolun_left_list li a",function(){
        $(".q_works .q_ask").hide();
        $(".q_works .p_taolun_right").show();
        var id = $(this).attr("data");
        if(id>0){
            $("#showworks").val(id);
            fnWorksContent();
            loadMyRecord();
        }
    })

    $(document).on("click","#q_works_page .page_number a",function(){
        var pageNo = parseInt($(this).text());
        if(pageNo>0){
            var id = $(".q_works .discussAClick").attr("data");
            $("#showworks").val(id);
            fnWorksReply(pageNo);
        }
    });

    $(document).on("click","#q_works_page .page_action",function(){
        var pageNo = parseInt($("#q_works_page .page_input").val());
        var pageCount = parseInt($("#q_works_page .pageCount").val());
        if(pageNo<=pageCount){
            var id = $(".q_works .discussAClick").attr("data");
            $("#showworks").val(id);
            fnWorksReply(pageNo);
        }
    });

    $(document).on("click",".q_works .p_fawen",function(){	// 上传作品
        var noticTitle = $(".q_works .noticTitle").val();
        var content = editor_worksAdd.getContent();
        var authorId= $("#authorId").val();
        var error = "";
        if(loginUserId==2){
            error="请先登录!";
        }
        if(noticTitle==""){
            error="作品标题不能为空!";
        }
        if(noticTitle.length>30){
            error="标题字数不能超过30个";
        }
        if(content==""){
            error="作品内容不能为空!";
        }
        if(error!=""){
            blackBox.alert(error, {
                title: '提示',
                value: '确认'
            });
            return;
        }
        var team = loginUserId;
        $(".q_teamTbody input:checkbox").each(function(i){
            if($(this).attr("checked")) {
                team += ","+$(this).attr("data");
            }
        });
        var diyWorks = {
            userId : loginUserId,
            userName : loginNickName,
            couseId : courseInfoId,
            teacherId : authorId,
            worksName : noticTitle,
            content : content,
            team : team
        }

        focusFromService.addDiyWorks(diyWorks,function(requestData){
            if(requestData.isError){
                return
            }
            var data = requestData.result;
            $("#showworks").val(data);
            $(".q_works .q_discuss_Ul ul").text("");
            $("#works_pageNo").val(1);
            $(".q_works .noticTitle").val("");
            editor_worksAdd.setContent("");
            fnWorks();
            $(".q_works .p_taolun_right").show();
            $(".q_works .q_ask").hide();
        });
    });

    function loadMyRecord(){	// 查询我的点赞记录
        var worksId = $("#showworks").val();
        $(".q_works_dianzhan").removeClass("q_works_dianzhanHover");
        $(".q_works_dianzhan").attr("data",1);
        worksReplyService.loadMyRecord(loginUserId,function(requestData){
            if(requestData.isError){
                return;
            }
            var data = requestData.result;
            if(data.length>0){
                var likeStr = data[0].likeNumList;
                var likeArrlr = new Array();
                if(likeStr != null && likeStr != "") {
                    likeArrlr = likeStr.split(",");
                }

                for(var i in likeArrlr){
                    if(likeArrlr[i]==worksId){
                        $(".q_works_dianzhan").addClass("q_works_dianzhanHover");
                        $(".q_works_dianzhan").attr("data",0);
                    }
                }
            }
        })
    }

    $(document).on("click",".q_works_dianzhan",function(){
        var flag = $(this).attr("data");
        var worksId = $("#showworks").val();

        worksReplyService.setMyLikeNumList(loginUserId,worksId,flag,function(){
            var num = parseInt($(".q_works_dianzhan").text());
            if(flag==1){
                num += 1;
            }else{
                num += -1;
            }
            if(num<0){
                num=0;
            }
            $(".q_works_dianzhan").text(num);
            var content = worksMap.get(worksId);
            content.likeNum=num;
            worksMap.put(worksId,content);

            loadMyRecord();
        });
    })

    function loadTeamUserList(){	// 获取关注了該课程的学生
        focusCourseService.getFocusCourseUser(courseInfoId,function(requestData){
            if(requestData.isError){
                return;
            }
            var data = requestData.result;
            var html = '';
            var tdH = "";

            var sum = data.length-1;	// 除了自己外一共有多少人

            if(sum>0){
                for(var i in data){
                    if(data[i].id!=loginUserId){
                        var h = '<td><input type="checkbox" data="'+data[i].id+'"/><span>'+data[i].nickname+'</span></td>';
                        tdH += h;
                    }
                    if((i>0 && i%6==0) || i==sum){
                        html += '<tr>'+tdH+'</tr>';
                        tdH = "";
                    }
                }
            }
            $(".q_teamTbody").html(html);
        })
    }

    /*
     * 作品区 end
     */
})