layui.use(['layer', 'form', 'jquery', 'laytpl', 'element', 'js_tools'], function () {

    var $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl,
        js_tools = layui.js_tools,
        form = layui.form;

    js_tools.tieID = null;
    js_tools.tieCmtID = null;

    window.addTargetLink = function (d) {
        return d;
        if (/\[.+\]/.test(d)){
            var c = /\[.+\]/.exec(d)[0];
            var id = c.replace(/[^0-9]/ig,"");
            var fi = d.replace(/\[.+\]/,"<a href='#"+id+"'>"+c.replace("[\[\]]","")+"</a>");
            console.log("["+d+"]==>["+c+"]==>["+id+"]==>["+fi+"]");
            return fi;
        }else {
            return d;
        }

    }
    
    function refreshCmt(tieId, index,role) {
        if (role=='1'){
            //帖子回复
            js_tools.loadData({
                url: '/admin/api/tieCmt/page',
                param: {tieId: tieId, pageIndex: index, pageSize: 5},
                model: '#dy_cmt_page_model',
                list: '#dy_cmt_page',
                page: 'dy_cmt_pages',
            });
        }else{
            //帖子回复
            js_tools.loadData({
                url: '/admin/api/tieCmt/page',
                param: {tieId: tieId, pageIndex: index, pageSize: 5},
                model: '#tie_cmt_page_model',
                list: '#tie_cmt_page',
//            append:true,
                page: 'cmt_pages',
                /*
                            layout: ['next'],
                            next: '载入更多'
                */
            });
        }
    }

    // 渲染帖子列表 #tie_list
    function refreshTie(index,role) {
        if (role=='1'){
            js_tools.loadData({
                url: '/admin/api/tie/page',
                param: {pageSize: 10, pageIndex: index,role:'1'},
                model: '#tie_model',
                list: '#dy_list',
                page: 'dy_p_taolun_left_more',
                layout: ['next'],
                next: '载入更多'
            });
        } else{
            js_tools.loadData({
                url: '/admin/api/tie/page',
                param: {pageSize: 10, pageIndex: index,role:'0'},
                model: '#tie_model',
                list: '#tie_list',
                page: 'tie_p_taolun_left_more',
                layout: ['next'],
                next: '载入更多'
            });
        }
    }

    //交流贴
    refreshTie(1,"0");
    //答疑贴
    refreshTie(1,"1");

    // 点击帖子查看详情
    $('#tie_list').on('click', 'a', function () {
        //点击效果
        $('#tie_list>li').removeClass('discussLiClick').find('a').removeClass('discussAClick');
        $(this).addClass('discussAClick').parent('li').addClass('discussLiClick');
        $('.q_ask').hide();
        //帖子
        var tie = JSON.parse(unescape($(this).attr('value')));
        js_tools.tieID = tie.id;
        js_tools.tieCmtID = null;
        laytpl($('#tie_detail_model').html()).render(tie, function (html) {
            $('#tie_detail').html(html);
        });
        //帖子回复
        refreshCmt(tie.id, 1,"0");
    });

    // 答疑帖
    $('#dy_list').on('click', 'a', function () {
        //点击效果
        $('#dy_list>li').removeClass('discussLiClick').find('a').removeClass('discussAClick');
        $(this).addClass('discussAClick').parent('li').addClass('discussLiClick');
        //帖子
        var tie = JSON.parse(unescape($(this).attr('value')));
        js_tools.tieID = tie.id;
        js_tools.tieCmtID = null;
        laytpl($('#tie_detail_model').html()).render(tie, function (html) {
            $('#dy_detail').html(html);
        });
        //帖子回复
        refreshCmt(tie.id, 1,"1");
    });

    //回复其他人
    $('#tie_cmt_page').on('click', '.p_huifuqu .p_huifuqu_time a', function () {
        tieCmtID = $(this).attr('data');
        $('#cmt_content').val('[@' + $(this).attr('userId') + ']');
    });
    // 答疑 回复他人
    $('#dy_cmt_page').on('click', '.p_huifuqu .p_huifuqu_time a', function () {
        tieCmtID = $(this).attr('data');
        $('#dy_cmt_content').val('[@' + $(this).attr('userId') + ']');
    });

    // 添加帖子
    $('.p_taolun_nav_question').on('click', function () {
        $('.q_ask').show();
        $('.p_taolun_right').hide();
    });

    // 提交帖子
    form.on('submit(ask-btn)', function (data) {
        //新建帖子
        js_tools.sync_post('/admin/api/tie/add', data.field, function (res) {
            layer.alert(res.msg);
        }, null);
        return false;
    })

    //提交回复
    form.on('submit(tie-cmt-btn)', function (data) {
        if (js_tools.tieID) {
            var p = {
                tieId: js_tools.tieID,
                userId:data.field.userId,
                content: data.field.content
            };
            //贴子回复
            if (js_tools.tieCmtID) {
                //回复他人
                p.cmtId = js_tools.tieCmtID;
            }
            js_tools.sync_post('/admin/api/tieCmt/add', p, function (res) {
                if (res.code == js_tools.successCode) {
                    refreshCmt(js_tools.tieID, 1);
                    //刷新当前回复
                }
                layer.alert(res.msg);
            })
        }
    });


    //////////////////////////////////////////////////////////
    //////////////答疑
    ///////////////////////////////



});