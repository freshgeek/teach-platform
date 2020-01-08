/*****
 *  js 工具类
 *
 */
layui.define(['layer', 'form', 'laytpl', 'laypage', 'jquery'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        form = layui.form;
    /**
     *对Date的扩展，将 Date 转化为指定格式的String
     *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     *例子：
     *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
     */
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    /****
     *
     * @param url
     * @param param
     * @param tkv tkv.elem  tkv.value  tkv.name
     */
    var ws = {
        successCode: '0000',
        socket: null,
        //开始学习
        startStudy: function () {
            ws.studentStudyKey = 'study'+ws.get('#user_id').val();
            // 获取学习监控数据
            var get = ws.localGet(ws.studentStudyKey);
            get = get || {
                lock:false,
                timespace: 0,
                currentDay:new Date().format('yyyy-MM-dd')
            };
            get = {
                lock:get.lock||false,
                timespace: get.timespace || 0,
                currentDay:get.currentDay||new Date().format('yyyy-MM-dd')
            }
            // 如果不为今天, 则重新检测
            if (get.currentDay!=new Date().format('yyyy-MM-dd')){
                get = {
                    lock:false,
                    timespace: 0,
                    currentDay:new Date().format('yyyy-MM-dd')
                }
            }
            // 保存进本地
            ws.localPut(ws.studentStudyKey,get);
        },
        checkStudy: function (s, func) {
            var i = setInterval(function () {
                var get = ws.localGet(ws.studentStudyKey);
                // 如果锁定 则说明 以及达标
                if (get.lock){//清除定时检测
                    clearInterval(i);
                    return;
                }
                // 每秒检测 加 1秒
                get.timespace += 1000;
                ws.localPut(ws.studentStudyKey,get);
                console.log('当前时间='+get.timespace);
                // 如果时间到 执行指定任务
                if (get.timespace >= s) {
                    func(get.timespace, i);
                }
            }, 1000);
        },
        pauseStudy: function () {
            var get = ws.localGet(ws.studentStudyKey);
            get.timespace += new Date().getTime() - get.time;
            get.time = null;
            ws.localPut(get);
        },
        stopStudy:function(){
            var get = ws.localGet(ws.studentStudyKey);
            get.lock=true;
            ws.localPut(ws.studentStudyKey,get);
        },
        localGet: function (k) {
            if (localStorage.getItem(k)) {
                var t = JSON.parse(localStorage.getItem(k));

                if (typeof t == 'string') {
                    return JSON.parse(t);
                } else {
                    return t;
                }
            }
        },
        addText: function (e, n) {
            var t = e.text();
            console.log(t);
            if (!isNaN(t)) {
                e.text(parseInt(t) + n);
            }
        },
        localPut: function (k, v) {
            localStorage.removeItem(k);
            localStorage.setItem(k, JSON.stringify(v));
        },
        localArrAdd: function (k, e, size) {
            if (ws.localGet(k)) {
                var arr = ws.localGet(k);
                arr.push(e);
                if (size > 0 && arr.length > size) {
                    arr.splice(0, arr.length - size);
                }
                ws.localPut(k, arr);
            } else {
                ws.localPut(k, JSON.stringify([e]));
            }
        },
        get: function (e) {
            return $(e);
        },
        isSucc: function (rs) {
            return rs.code == ws.successCode;
        },
        loadPage: {},
        /***
         * 抽象:
         *  url 不为空
         *  param  默认为空
         *  请求方法 默认get
         *  async 同步异步 默认同步
         *  model css选择器 默认为 id-> model
         *  list css选择器 默认为 id->list
         *  append 还是覆盖 默认覆盖
         *  page : id选择器 page
         *
         */
        loadData: function (sets) {
            /*  var set = {
                  url:sets.url,
                  param:sets.param||null,
                  async:sets.async||false,
                  type:sets.type||'get',
                  model:sets.model||'#model',
                  list:sets.list||'#list',
                  append:sets.append||false
              }*/
            var m = sets.model || '#model';
            var l = sets.list || '#list';
            var pageRender = function (t) {
                if ((sets.page && !ws.loadPage[l]) || sets.refresh) {
                    ws.loadPage[l] = {
                        elem: sets.page
                        , count: t //数据总数，从服务端得到
                        , limit: sets.param['pageSize'] || 10
                        , jump: function (obj, first) {
                            //obj包含了当前分页的所有参数，比如：
                            console.log(obj);
                            //首次不执行
                            if (!first) {
                                sets.param.pageIndex = obj.curr;
                                delete sets.refresh;
                                ws.loadData(sets);
                            }
                        }
                    }
                    if (sets.layout) {
                        ws.loadPage[l].layout = sets.layout;
                        ws.loadPage[l].next = sets.next;
                    }
                    laypage.render(ws.loadPage[l]);
                }
            }
            $.ajax({
                url: sets.url,
                async: sets.async || false,
                cache: false,
//                contentType:'',
                data: sets.param,
                type: sets.type || 'get',
                dataType: 'json',
                error: function (x, m, t) {
                    alert(m);
//                    XMLHttpRequest 对象、错误信息、（可选）捕获的异常对象。
                },
                complete: function () {
                    //      alert(m);
                },
                success: function (d) {
                    laytpl($(m).html()).render(d, function (h) {
                        var l = $(sets.list || '#list');
                        if (sets.append) {
                            l.append(h);
                        } else {
                            l.html(h);
                        }
                        pageRender(d.total);
                    });
                }
            });

        },
        verify: {
            qq: [
                /^[1-9][0-9]{4,11}$/gim
                , '请输入正确的QQ号码'
            ],
            len6_12: [
                /^[a-zA-Z0-9]{6,12}$/
                , '请输入6-12长度数字与字母组合'
            ],
            len6_16: [
                /^[a-zA-Z0-9]{6,16}$/
                , '请输入6-16长度数字与字母组合'
            ],
            len6_18: [
                /^[a-zA-Z0-9]{6,18}$/
                , '请输入6-18长度数字与字母组合'
            ],
            fix: [],
            hard: [],
            confirmPass: function (value) {
                if ($('#password').val() !== value) {
                    console.log('密码字段:' + $('#password').val() + ',确认密码:' + value);
                    return '两次密码输入不一致！';
                }
            }
        },
        quick_post_json: function (url, param, rs) {
            $.ajax({
                url: url,
                type: "POST",
                async: false,
                data: param,
                success: rs,
                contentType: "application/json;charset=utf-8",
                dataType: "json"

            });
        },
        tableTPL: {
            elem: '#list'
            , cellMinWidth: 80
            , url: ''
            , cols: []
            , method: "GET"
            // , contentType: 'application/json'
            , page: true //开启分页
            , response: {
                statusName: 'code' //规定数据状态的字段名称，默认：code
                , statusCode: this.successCode //规定成功的状态码，默认：0
                , msgName: 'msg' //规定状态信息的字段名称，默认：msg
                , countName: 'total'
                , dataName: 'body' //规定数据列表的字段名称，默认：data
            }, request: {
                pageName: 'pageIndex',
                limitName: 'pageSize'
            }
        },
        cloneObj: function (obj) {
            var newObj = {};
            if (obj instanceof Array) {
                newObj = [];
            }
            for (var key in obj) {
                var val = obj[key];
                //newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; //arguments.callee 在哪一个函数中运行，它就代表哪个函数, 一般用在匿名函数中。
                newObj[key] = typeof val === 'object' ? this.cloneObj(val) : val;
            }
            return newObj;
        },
        getTableTemplate: function () {
            return this.cloneObj(this.tableTPL);
        },
        fill_select: function (url, param, tkv) {
            this.quick_get(url, param, function (rs) {
                if (!tkv) {
                    tkv = {}
                }
                if (!tkv.elem) {
                    tkv.elem = 'select'
                }
                if (!tkv.value) {
                    tkv.value = 'id'
                }

                if (!tkv.name) {
                    tkv.name = 'name'
                }

                if (rs.code == ws.successCode) {
                    var ob = $(tkv.elem);
                    var data = rs[ws.tableTPL.response.dataName];
                    var html = ['<option value="">请选择</option>'];
                    if (ob) {
                        for (var i in data) {
                            html.push('<option value="' + data[i][tkv.value] + '">' + data[i][tkv.name] + '</option>')
                        }
                        ob.empty().append(html.join(''));
                    } else {
                        console.error('未找到对应的select 对象');
                    }
                }
            })
        },
        quick_get: function (url, param, rs) {
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                data: param,
                success: rs,
                dataType: "json"
            });
        },
        buildInfo: function () {
            var me = this;
            quick_get("/base/getUser", null, function (res) {
                me.user = res.data;
                var ms = "";
                if (me.user.user_type == 'a') {
                    ms = "管理员界面";
                } else if (me.user.user_type == 'm') {
                    ms = "商户界面";
                } else {
                    ms = "用户界面";
                }
                $('#logo').text(ms);
                $('#name_span').text(me.user.name || '未命名');
            });
        },
        quick_post: function (url, param, rs) {
            $.ajax({
                url: url,
                type: "POST",
                async: false,
                data: param,
                success: rs,
                dataType: "json"
            });
        },
        sync_post: function (url, param, succ, exception) {
            $.ajax({
                url: url,
                type: "POST",
                async: false,
                data: param,
                success: function (dd) {
                    if (dd.code == ws.successCode) {
                        succ(dd);
                    } else {
                        if (exception) {
                            exception(dd);
                        } else {
                            layer.alert(dd[ws.tableTPL.response.msgName]);
                        }
                    }
                },
                dataType: "json"
            });
        },
        closeWindows: function () {
            var userAgent = navigator.userAgent;
            if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
                window.location.href = "about:blank";
                window.close();
            } else {
                window.opener = null;
                window.open("", "_self");
                window.close();
            }
            window.opener = null;
            window.open('', '_self', '');
            window.close();//以上三行可关闭单个页面
            window.open('', '_top');
            window.top.close();
            window.location.href = 'about:blank ';
            window.close();//上面两次关闭适用于FireFox等浏览器
        },
        getParam: function () {
            //返回当前 URL 的查询部分（问号 ? 之后的部分）。
            var urlParameters = location.search;
            //声明并初始化接收请求参数的对象
            var requestParameters = new Object();
            //如果该求青中有请求的参数，则获取请求的参数，否则打印提示此请求没有请求的参数
            if (urlParameters.indexOf('?') != -1) {
                //获取请求参数的字符串
                var parameters = decodeURI(urlParameters.substr(1));
                //将请求的参数以&分割中字符串数组
                parameterArray = parameters.split('&');
                //循环遍历，将请求的参数封装到请求参数的对象之中
                for (var i = 0; i < parameterArray.length; i++) {
                    requestParameters[parameterArray[i].split('=')[0]] = (parameterArray[i].split('=')[1]);
                }
                console.info('the Request is =====', requestParameters);
            }
            return requestParameters;
        },
        fillForm: function (jq, data) {
            var base = $(jq);
            for (var i in data) {
                var d = data[i];
                base.find('[name="' + i + '"]').val(d);
            }
        },
        buildWebSocket: function () {
            var me = this;
            var socket = this.socket;
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
                layer.msg('您的浏览器不支持websocket 可能无法接受到实时消息提醒');
            } else {
                console.log("您的浏览器支持WebSocket");
                //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
                quick_get("/base/getUser", null, function (res) {
                    if (res.code == 0) {
                        me.user = res.data;
                        //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");
                        socket = new WebSocket("ws://" + location.host + "/websocket/" + me.user.id);
                        //打开事件
                        socket.onopen = function () {
                            console.log("Socket 已打开");
                            //socket.send("这是来自客户端的消息" + location.href + new Date());
                        };
                        //获得消息事件
                        socket.onmessage = function (msg) {
                            console.log(msg.data);
                            layer.alert(msg.data);
                            //发现消息进入
                            //开始处理前端触发逻辑
                        };
                        //关闭事件
                        socket.onclose = function () {
                            console.log("Socket已关闭");
                        };
                        //发生了错误事件
                        socket.onerror = function () {
                            alert("Socket发生了错误");
                        }
                    }
                });
            }
        }
    }
    form.verify(ws.verify);
    exports('js_tools', ws);
});