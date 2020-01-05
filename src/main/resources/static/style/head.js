//config的设置是全局的
layui.config({
    base: '/layui/js/extends/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    js_tools: 'js_tools',
    obr: 'observer'
});


function ScreenSaver(settings) {
    var m = this;
    settings = settings || '';
    // 超时时间
    this.saverTime = settings.timeout || 2000;
    // 超时回调函数
    this.stopCall = settings.stop || function () {
        alert((m.saverTime / 1000) + '秒未操作');
    };

    //结束监听
    var stop = function () {
        console.log('stop()')
        window.clearTimeout(m.taskID);
        m.taskID = null;
    }

    // 开始监听
    var start = function () {
        console.log("start()");
        //设定超时
        m.taskID = window.setTimeout(function () {
            console.log("window.setTimeout()");
            stop();
            m.stopCall();
        }, m.saverTime);
    }

    //键鼠触发重新计时
    var signal = function (e) {
        if (m.taskID) {
            console.log("signal()");
            stop();
            start();
        }
    }

    // 键鼠触发
    document.body.onmousemove = signal;
    document.body.onmousedown = signal;
    document.body.onkeydown = signal;
    document.body.onkeypress = signal;

    // 给 外部暴露调用接口
    this.run = start;
}


layui.use(['layer', 'form', 'obr', 'jquery', 'element', 'js_tools'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        js_tools = layui.js_tools,
        form = layui.form;

    var obr = layui.obr;
    /*  var screenSaver = new ScreenSaver({
          timeout: 2000, stop: function () {
              layer.alert('检测到用户无动作,已经暂停', function (i) {
                  layer.close(i);
                  layer.msg('点击,恢复检测')
                  screenSaver.run();
              })
          }
      });
      screenSaver.run();*/
    var wa = obr.watchUser({
        timeout: 20000, stop: function (d) {
            layer.alert('检测到用户无动作,从' + d + '到' + new Date() + ',已经暂停', function (i) {
                layer.close(i);
                layer.msg('点击,恢复检测')
                wa.run();
            })
        }
    });

    //wa.run();
    form.on('submit(submit-btn)', function (data) {
        js_tools.quick_post("/login", data.field, function (res) {
            if (res.code == js_tools.successCode) {
                layer.alert('登录成功', function () {
                    location.reload();
                })
            } else {
                layer.alert(res.msg);
            }
        });
    })
    // 事件监听
    // 搜索
    $('#x_search').on('focus', function () {
        var arr = js_tools.localGet("search");
        if (arr) {

            var html = [];
            if (arr.constructor == Array) {
                $.each(arr, function (i, e) {
                    html.push("<li><span>" + e.key + "</span><a>" + e.type + "</a></li>");
                });
            } else {
                html.push("<li><span>" + arr.key + "</span><a>" + arr.type + "</a></li>");
            }
            $('#search_history').html(html.join(''));
        }
        $('#search_list').show();
    }).on('input', function () {
        $('#search_kind li span').text($(this).val());
    }).on('click', function () {
        return false;
    });

    //隐藏
    $('body').on('click', function () {
        $('#search_list').hide();
    });
    //点击下面 记录并搜索
    $('#search_kind').on('click', 'li', function () {
        var param = {
            key: $(this).find('span').text(),
            type: $(this).find('a').text()
        }
        console.log(JSON.stringify(param));
        js_tools.localArrAdd("search", param,5);
        //分类型搜索
        if (param.type=='课程'){
            location.href='/course?title='+param.key;
        }else if (param.type=='资源') {
            location.href='/resource-list.html?name='+param.key;
        }else if (param.type=='作品') {
            location.href='/achv-list.html?name='+param.key;
        }else if (param.type=='资讯') {
            location.href='/sys-notify-list.html?typeId=2&title='+param.key;
        }else{
            alert('非法参数')
        }
    });

    $('#x_gongzuoshi').on("click", function () {
        $("#personalCenter").toggle();
    });
    // 点击头像
    $('.q_headPortrait').on("click", function () {
        $("#wn").toggle();
    });

    // 登录弹窗
    $('#btn_login2,#signin-close').on('click', function () {
        $('.q_login').toggle();
    });


    // 验证码
    $('.reset_bt,.code_img').on('click', function () {
        $('.code_img').attr('src', '/imageCodeServlet?d=' + new Date().getTime());
    });

    js_tools.quick_get('/loadLoginUser', null, function (res) {
        if (res.code == js_tools.successCode) {
            $('#user_id').val(res.body.id);
            $('#user_role').val(res.body.roleId);
            $('#nick_name').val(res.body.nickName);
            js_tools.loginUser = res.body;
        }
    })


    window.checkLogin = function(){
        if (js_tools.loginUser){
            return js_tools.loginUser;
        } else{
            layer.msg('请登录');
            $('.q_login').toggle();
        }
    }

});
