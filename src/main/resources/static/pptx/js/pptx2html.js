$(document).ready(function () {
    function getParam() {
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
    }

    function resize() {
        var h = window.innerHeight;
        var w = window.innerWidth;
        var sw = $('section').eq(0).width();
        var sh = $('section').eq(0).height();
        $('section').width(w).height(h);
        if (!sw) {
            return;
        }

        $('section *').each(function (i,e) {
            if ($(e).width()==0){
                console.log('qian'+e.tagName);
            }
            $(e).width(parseInt($(e).width()*w/sw));
            $(e).height(parseInt($(e).height()*h/sh));
            if ($(e).width()==0){
                console.log('hou'+e.tagName);
            }
            $(e).css('top',parseInt($(e).css('top').split('px')[0]*h/sh)+'px')
            $(e).css('left',parseInt($(e).css('left').split('px')[0]*w/sw)+'px')

        });

    }

    if (window.Worker) {

        var $result = $("#result");
        var isDone = false;
        var blob = [];
        var url = getParam().url;
        if (!url || url.indexOf('ppt') < 0) {
            $('#result').html('<h2>错误格式文件</h2>');
            return false;
        }
        window.onresize = function (ev) {
            console.log('宽:' + window.innerWidth);
            console.log('高:' + window.innerWidth);
        }

        function createHtml(blob) {
            isDone = false;
            $("#load-progress").text("0%").attr("aria-valuenow", 0).css("width", "0%");
            var fileName = new File(blob, url.substring(1));
            // Read the file
            var reader = new FileReader();
            reader.onload = (function (theFile) {
                return function (e) {
                    // Web Worker
                    var worker = new Worker('/pptx/js/worker.js');
                    worker.addEventListener('message', function (e) {
                        var msg = e.data;
                        switch (msg.type) {
                            case "progress-update":
                                $("#load-progress").text(msg.data.toFixed(2) + "%")
                                    .attr("aria-valuenow", msg.data.toFixed(2))
                                    .css("width", msg.data.toFixed(2) + "%");
                                break;
                            case "slide":
                                $result.append(msg.data);
                                break;
                            case "processMsgQueue":
                                processMsgQueue(msg.data);
                                break;
                            case "pptx-thumb":
                                $("#pptx-thumb").attr("src", "data:image/jpeg;base64," + msg.data);
                                break;
                            case "slideSize":
                                if (localStorage) {
                                    localStorage.setItem("slideWidth", msg.data.width);
                                    localStorage.setItem("slideHeight", msg.data.height);
                                } else {
                                    alert("Browser don't support Web Storage!");
                                }
                                break;
                            case "globalCSS":
                                $result.append("<style>" + msg.data + "</style>");
                                break;
                            case "ExecutionTime":
                                $("#info_block").html("Execution Time: " + msg.data + " (ms)");
                                isDone = true;
                                worker.postMessage({
                                    "type": "getMsgQueue"
                                });
                                break;
                            case "WARN":
                                console.warn('Worker: ', msg.data);
                                break;
                            case "ERROR":
                                console.error('Worker: ', msg.data);
                                $("#error_block").text(msg.data);
                                break;
                            case "DEBUG":
                                console.debug('Worker: ', msg.data);
                                break;
                            case "INFO":
                            default:
                                console.info('Worker: ', msg.data);
                        }
                    }, false);

                    worker.postMessage({
                        "type": "processPPTX",
                        "data": e.target.result
                    });

                }
            })(fileName);
            reader.readAsArrayBuffer(fileName);

            setTimeout(function () {
                $('section').addClass('item').eq(0).addClass('active');
            //    resize();
            }, 2000);
            $(window).resize(function() {
              //  resize();
            });
        }

        //获取Blob值
        function getImageBlob(url) {
            var xhr = new XMLHttpRequest();

            xhr.open("get", url, true);

            xhr.responseType = "blob";
            xhr.onload = function () {
                if (this.status == 200) {
                    blob.push(this.response);
                    createHtml(blob);
                } else {
                    $('#result').html('<h2>加载失败</h2>')
                }
            };
            xhr.send();
        }

        getImageBlob(url);


        $("#slideContentModel").on("show.bs.modal", function (e) {
            if (!isDone) {
                return;
            }
            $("#slideContentModel .modal-body textarea").text($result.html());
        });

        $("#download-btn").click(function () {
            if (!isDone) {
                return;
            }
            var cssText = "";
            $.get("css/pptx2html.css", function (data) {
                cssText = data;
            }).done(function () {
                var headHtml = "<style>" + cssText + "</style>";
                var bodyHtml = $result.html();
                var html = "<!DOCTYPE html><html><head>" + headHtml + "</head><body>" + bodyHtml + "</body></html>";
                var blob = new Blob([html], {type: "text/html;charset=utf-8"});
                saveAs(blob, "slides_p.html");
            });
        });

        $("#download-reveal-btn").click(function () {
            if (!isDone) {
                return;
            }
            var cssText = "";
            $.get("css/pptx2html.css", function (data) {
                cssText = data;
            }).done(function () {
                var revealPrefix =
                    "<script type='text/javascript'>\
                    Reveal.initialize({\
                        controls: true,\
                        progress: true,\
                        history: true,\
                        center: true,\
                        keyboard: true,\
                        slideNumber: true,\
                        \
                        theme: Reveal.getQueryHash().theme,\
                        transition: Reveal.getQueryHash().transition || 'default',\
                        \
                        dependencies: [\
                            { src: 'lib/js/classList.js', condition: function() { return !document.body.classList; } },\
                            { src: 'plugin/markdown/marked.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },\
                            { src: 'plugin/markdown/markdown.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },\
                            { src: 'plugin/highlight/highlight.js', async: true, callback: function() { hljs.initHighlightingOnLoad(); } },\
                            { src: 'plugin/zoom-js/zoom.js', async: true, condition: function() { return !!document.body.classList; } },\
                            { src: 'plugin/notes/notes.js', async: true, condition: function() { return !!document.body.classList; } }\
                        ]\
                    });\
                    </script>";
                var headHtml = "<style>" + cssText + "</style>";
                var bodyHtml = "<div id='slides' class='slides'>" + $result.html() + "</div>";
                var html = revealPrefix + headHtml + bodyHtml;
                var blob = new Blob([html], {type: "text/html;charset=utf-8"});
                saveAs(blob, "slides.html");
            });
        });

        $("#to-reveal-btn").click(function () {
            if (localStorage) {
                localStorage.setItem("slides", LZString.compressToUTF16($result.html()));
                window.open("./reveal/demo.html", "_blank");
            } else {
                alert("Browser don't support Web Storage!");
            }
        });

    } else {

        alert("Browser don't support Web Worker!");

    }

});

function processMsgQueue(queue) {
    for (var i = 0; i < queue.length; i++) {
        processSingleMsg(queue[i].data);
    }
}

function processSingleMsg(d) {

    var chartID = d.chartID;
    var chartType = d.chartType;
    var chartData = d.chartData;

    var data = [];

    var chart = null;
    switch (chartType) {
        case "lineChart":
            data = chartData;
            chart = nv.models.lineChart()
                .useInteractiveGuideline(true);
            chart.xAxis.tickFormat(function (d) {
                return chartData[0].xlabels[d] || d;
            });
            break;
        case "barChart":
            data = chartData;
            chart = nv.models.multiBarChart();
            chart.xAxis.tickFormat(function (d) {
                return chartData[0].xlabels[d] || d;
            });
            break;
        case "pieChart":
        case "pie3DChart":
            data = chartData[0].values;
            chart = nv.models.pieChart();
            break;
        case "areaChart":
            data = chartData;
            chart = nv.models.stackedAreaChart()
                .clipEdge(true)
                .useInteractiveGuideline(true);
            chart.xAxis.tickFormat(function (d) {
                return chartData[0].xlabels[d] || d;
            });
            break;
        case "scatterChart":

            for (var i = 0; i < chartData.length; i++) {
                var arr = [];
                for (var j = 0; j < chartData[i].length; j++) {
                    arr.push({x: j, y: chartData[i][j]});
                }
                data.push({key: 'data' + (i + 1), values: arr});
            }

            //data = chartData;
            chart = nv.models.scatterChart()
                .showDistX(true)
                .showDistY(true)
                .color(d3.scale.category10().range());
            chart.xAxis.axisLabel('X').tickFormat(d3.format('.02f'));
            chart.yAxis.axisLabel('Y').tickFormat(d3.format('.02f'));
            break;
        default:
    }

    if (chart !== null) {

        d3.select("#" + chartID)
            .append("svg")
            .datum(data)
            .transition().duration(500)
            .call(chart);

        nv.utils.windowResize(chart.update);

    }

}
