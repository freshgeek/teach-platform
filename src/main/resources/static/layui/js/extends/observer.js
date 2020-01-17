layui.define(['layer', 'jquery'], function (exports) {
    var layer = layui.layer;
    var $ = layui.jquery;


    function ScreenSaver(settings) {
        var m = this;
        // 超时时间
        this.saverTime = settings.timeout || 30000;
        // 超时回调函数
        this.stopCall = settings.stop || function () {
            layer.msg((m.saverTime / 1000) + '秒未操作');
        };

        //结束监听
        var stop = function () {
            console.log('stop()')
            clearTimeout(m.taskID);
            m.taskID = null;
        }

        // 开始监听
        var start = function () {
            console.log("start()");
            m.startDate = new Date();
            //设定超时
            m.taskID = setTimeout(function () {
                console.log("window.setTimeout()");
                stop();
                m.stopCall(m.startDate);
            }, m.saverTime);
        }

        //键鼠触发重新计时
        var events = 'mousemove mousedown keydown keypress';
        var evfn = function () {
            console.log("signal()");
            if (m.taskID) {
                stop();
                start();
            }
        };
        // 键鼠触发
        $('body').on(events, evfn);

        var i = setInterval(function () {
            if ($('iframe').length > 0) {
                $($('iframe')[0].contentDocument).on(events, evfn);
                clearInterval(i);
            }
        }, 1000);
        // 给 外部暴露调用接口
        this.run = start;
    }

    exports('obr', {
        watchUser: function (sets) {
            return new ScreenSaver(sets);
        }
    });

});
