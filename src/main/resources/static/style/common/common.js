// ########################################  公用方法  #########################
var validateRegExp = {
    decmal:"^([+-]?)\\d*\\.\\d+$", //浮点数
    decmal1:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", //正浮点数
    decmal2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", //负浮点数
    decmal3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$", //浮点数
    decmal4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$", //非负浮点数（正浮点数 + 0）
    decmal5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$", //非正浮点数（负浮点数 + 0）
    intege:"^-?[1-9]\\d*$", //整数
    intege1:"^[1-9]\\d*$", //正整数
    intege2:"^-[1-9]\\d*$", //负整数
    num:"^([+-]?)\\d*\\.?\\d+$", //数字
    num1:"^[1-9]\\d*|0$", //正数（正整数 + 0）
    num2:"^-[1-9]\\d*|0$", //负数（负整数 + 0）
    ascii:"^[\\x00-\\xFF]+$", //仅ACSII字符
    chinese:"^[\\u4e00-\\u9fa5]+$", //仅中文
    color:"^[a-fA-F0-9]{6}$", //颜色
    date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$", //日期
    email:"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
    idcard:"^[1-9]([0-9]{14}|[0-9]{17})$", //身份证
    ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$", //ip地址
    letter:"^[A-Za-z]+$", //字母
    letter_l:"^[a-z]+$", //小写字母
    letter_u:"^[A-Z]+$", //大写字母
    mobile:"^0?(13|15|18)[0-9]{9}$", //手机
    notempty:"^\\S+$", //非空
    password:"^.*[A-Za-z0-9\\w_-]+.*$", //密码
    fullNumber:"^[0-9]+$", //数字
    picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$", //图片
    qq:"^[1-9]*[1-9][0-9]*$", //QQ号码
    rar:"(.*)\\.(rar|zip|7zip|tgz)$", //压缩文件
    tel:"^[0-9\-()（）]{7,18}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
    url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", //url
    username:"^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$", //用户名
    deptname:"^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$", //单位名
    zipcode:"^\\d{6}$", //邮编
    realname:"^[A-Za-z\\u4e00-\\u9fa5]+$", // 真实姓名
    companyname:"^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    companyaddr:"^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
    companysite:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$"
};

//添加字符串startWith方法
String.prototype.startWith=function(str){  

	if(str==null||str==""||this.length==0||str.length>this.length)  
		return false;  

	if(this.substr(0,str.length)==str) 
	  return true;  
	else 
	   return false;  

	 return true;
};


//添加字符串endWith方法
String.prototype.endWith = function(str){  

	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;  

	if(this.substring(this.length-str.length)==str)  
	   return true; 
	else 
	   return false;  
	return true;  
};  

//添加字符串replaceAll方法
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};

//时间格式化函数
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

Date.prototype.pattern = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, //月份     
        "d+" : this.getDate(), //日     
        "h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时     
        "H+" : this.getHours(), //小时     
        "m+" : this.getMinutes(), //分     
        "s+" : this.getSeconds(), //秒     
        "q+" : Math.floor((this.getMonth() + 3) / 3), //季度     
        "S" : this.getMilliseconds()
    //毫秒     
    };
    var week = {
        "0" : "日",
        "1" : "一",
        "2" : "二",
        "3" : "三",
        "4" : "四",
        "5" : "五",
        "6" : "六"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1,
                ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "星期" : "周")
                        : "")
                        + week[this.getDay() + ""]);
    }
    if (/(e+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1,
                ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "星期" : "周")
                        : "")
                        + this.getDay());
    }
    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                    : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

//计算返回文件相应的大小
function getFileSize(size, length) {
	
	var bit = ["B", "KB", "MB", "GB", "TB", "PB"];
	
	var time = 0;
	while(size/1024 > 1) {
		
		size = size/1024; 
		time++;
	}
	size = size/1;	
	size = size.toFixed(length);
	size = size.replace(".00", "");
	
	return size + bit[time];
}

// 返回历史页面
function goBack(v){

	window.history.go(v);
}

//去除空格
function trim(str) {
	
	return str.replace(/(^\s+)|(\s+$)/g,"");
}
//去除空格
String.prototype.trim = function() {
   var matches = this.match(/^[ \t\n\r]+/);
   var prefixLength = (matches == null) ? 0:matches[0].length;
   matches = this.match(/[ \t\r\n]+$/);
   var suffixLength = (matches == null) ? 0:matches[0].length;
   return this.slice(prefixLength,this.length-suffixLength);
}; 
//是否网子
function isURL(str_url){
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
    + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
    + "|" // 允许IP和DOMAIN（域名）
    + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
    + "[a-z]{2,6})" // first level domain- .com or .museum
    + "(:[0-9]{1,4})?" // 端口- :80
    + "((/?)|" // a slash isn't required if there is no file name
    + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re=new RegExp(strRegex);
    //re.test()
    if (re.test(str_url)){
        return (true);
    }else{
        return (false);
    }
}

function changUrl(url, param, value) {
	
	url = url.replace("#","");
	
	if(url.indexOf("?") >= 0) {
		
		if(url.indexOf(param + "=") >= 0) {
			
			var tmpUrl = url.substring(url.indexOf("?") + 1, url.length);
			var paramNames = tmpUrl.split("&");
			for(var i=0; i < paramNames.length; i++) {
				
				var name = paramNames[i];
				if(name.indexOf(param + "=") >= 0) {
					
					url= url.replace(name, param + "=" + value);
				}
			}
		} else {
			url += "&" + param + "=" + value;
		}
	} else {
		
		url = url + "?" + param + "=" + value;
	}
	return url;
}

/********************map方法************************/
function Map() {
	this.container = new Object();
}

Map.prototype.put = function(key, value) {
	this.container[key] = value;
}

Map.prototype.get = function(key) {
	
	var v = this.container[key];
	if(typeof v == "undefined") {
		return "";
	}
	if(v == null) {
		return "";
	}
	return v;
}

Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
}

Map.prototype.size = function() {
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		count++;
	}
	return count;
}

Map.prototype.remove = function(key) {
	delete this.container[key];
}

Map.prototype.toString = function() {
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
		str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
}

var setCookie = function(name,value,expiresHours){ 
	var cookieString=name+"="+escape(value); 
	//判断是否设置过期时间 
	if(expiresHours>0){ 
		var date=new Date(); 
		date.setTime(date.getTime() + expiresHours*3600*1000); 
		cookieString=cookieString+"; expires="+date.toUTCString(); 
	} 
	document.cookie=cookieString; 
} 

var setGlobalCookie = function(name,value,expiresHours){ 
	var cookieString=name+"="+escape(value); 
	//判断是否设置过期时间 
	if(expiresHours>0){ 
		var date=new Date(); 
		date.setTime(date.getTime() + expiresHours*3600*1000); 
		cookieString=cookieString+"; expires="+date.toUTCString() + "; path=/"; 
	} 
	document.cookie=cookieString; 
} 

var getCookieValue = function(name){
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split("; "); 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		if(arr[0]==name)return unescape(arr[1]); 
	} 
	return ""; 
} 


//删除cookie
function deleteCookie(name,path){
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
}


//过滤Ueditor 修改时产生的前后空行
var contentFilter = function(content) {
	var index = content.indexOf('<p>')
	var firstP = content.substring(index+3,content.indexOf('</p>',index));
	firstP = firstP.trim(); 
	if(firstP == ""){
		content = content.substring(content.indexOf('</p>',index+4),content.length);
	}
	
	var lastIndex = content.lastIndexOf('<p>');
	var lastP = content.substring(lastIndex+3,content.indexOf('</p>',lastIndex));
	lastP = lastP.trim();
	if(lastP == ''){
		content = content.substring(0,lastIndex);
	}
	
	return content ;
}

//阻止事件(事件冒泡、浏览器默认行为)
var stopEvent = function(e){
    e = e || window.event;
    if(e.preventDefault) {//!ie
      e.preventDefault();
      e.stopPropagation();
    } else {//ie
      e.returnValue = false;
      e.cancelBubble = true;
    }
}

//阻止事件冒泡
var stopBubbleEvent = function(e){
	e = e || window.event;
	if(e.preventDefault) {//!ie
		e.stopPropagation();
	} else {
		e.cancelBubble = true;
	}
}


//限制图片宽度和表格
function resetArticleContent(dom, width){
	var w = 0;
	var h = 0;
	var temW = 0;
	$(dom).find("img").each(function(i){
		$(this).load(function(){
			w = $(this).width();
			temW = w;
			if(w > width){
				h = $(this).height();
				while(w > width){
					w = width;
					h = h*w/temW;
				}
				$(this).width(w);
				$(this).height(h);
			}
		});

	});
	
	$(dom).find("table").each(function(i){
		
		$(this).load(function(){
			w = $(this).width();
			temW = w;
			if(w > width){
				h = $(this).height();
				while(w > width){
					w = width;
					h = h*w/temW;
				}
				$(this).width(w);
				$(this).height(h);
			}
		});
	});
}

//阿拉伯数字转中文
var _change = {
        ary0:["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
        ary1:["", "十", "百", "千"],
        ary2:["", "万", "亿", "兆"],
        init:function (name) {
            this.name = name;
        },
        strrev:function () {
            var ary = []
            for (var i = this.name.length; i >= 0; i--) {
                ary.push(this.name[i])
            }
            return ary.join("");
        }, //倒转字符串。
        pri_ary:function () {
            var $this = this
            var ary = this.strrev();
            var zero = ""
            var newary = ""
            var i4 = -1
            for (var i = 0; i < ary.length; i++) {
                if (i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
                    i4++;
                    newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
                    zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

                }
                //关于0的处理与判断。
                if (ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
                    switch (i % 4) {
                        case 0:
                            break;
                        //如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
                        case 1:
                        case 2:
                        case 3:
                            if (ary[i - 1] != '0') {
                                zero = "零"
                            }
                            ; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
                            break;

                    }

                    newary = zero + newary;
                    zero = '';
                }
                else { //如果不是“0”
                    newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
                }

            }
            if (newary.indexOf("零") == 0) {
                newary = newary.substr(1)
            }//处理前面的0
            
            if (newary.indexOf("一十") == 0) {
         	   newary = newary.substr(1)
            }
            return newary;
        }
    }

    //创建class类
    function change() {
        this.init.apply(this, arguments);
    }
    change.prototype = _change

    /**
     * 返回前一页（或关闭本页面）
     * <li>如果没有前一页历史，则直接关闭当前页面</li>
     */
    function windowGoBack(){
        if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
            if(history.length > 0){
                window.history.go( -1 );
            }else{
                window.opener=null;window.close();
            }
        }else{ //非IE浏览器
            if (navigator.userAgent.indexOf('Firefox') >= 0 ||
                navigator.userAgent.indexOf('Opera') >= 0 ||
                navigator.userAgent.indexOf('Safari') >= 0 ||
                navigator.userAgent.indexOf('Chrome') >= 0 ||
                navigator.userAgent.indexOf('WebKit') >= 0){

                if(window.history.length > 1){
                    window.history.go( -1 );
                }else{
                    window.opener=null;window.close();
                }
            }else{ //未知的浏览器
                window.history.go( -1 );
            }
        }
    }
    
	//处理文件路径，url为远程地址，fileUrl文件路径
	function dealWithFileUrl(url, fileUrl) {
    	
    	var values = ["/style/", "/taoke/"];
    	var values1 = ["/taoke/"];
//	    
    	if(fileUrl == null || fileUrl.replaceAll(" ", "") == "") {
    		return "";
    	}
    	
    	if(parseInt($('#isKaiFa').val()) == 1) {
			
			var isHas = 0;
	    	for(var i in values1) {
	    		if(fileUrl.indexOf(values1[i]) > -1) {
	    			isHas = 1;
	    			break;
	    		}
	    	}
	    	if(isHas == 1) {    
	    		var urlArr = fileUrl.split("|");
	    		var returnStr = "";
	    		for(var i in urlArr) {
	    			returnStr += "http://www.zhongjiaoyun.net" + urlArr[i] + "|"
	    		}
	    		return returnStr.substring(0, returnStr.length - 1);
	    	} else {
	    		var urlArr = fileUrl.split("|");
	    		var returnStr = "";
	    		for(var i in urlArr) {
	    			returnStr += url + urlArr[i] + "|"
	    		}
	    		return returnStr.substring(0, returnStr.length - 1);
	    	}
		}
    	var isHas = 0;
    	for(var i in values) {
    		if(fileUrl.indexOf(values[i]) > -1) {
    			isHas = 1;
    			break;
    		}
    	}
    	if(isHas == 0) { 
    		var urlArr = fileUrl.split("|");
    		var returnStr = "";
    		for(var i in urlArr) {
    			returnStr += url + urlArr[i] + "|"
    		}
    		return returnStr.substring(0, returnStr.length - 1);
    		
    	} else {
    		var urlArr = fileUrl.split("|");
    		var returnStr = "";
    		for(var i in urlArr) {
    			returnStr += "" + urlArr[i] + "|";
    		}
    		return returnStr.substring(0, returnStr.length - 1);
    	}
	}  
    
    //处理试题库里面内容图片路径问题
    function dealWithTestContent(content) {
    	if(typeof content == "undefined" || content == null || content == "") {
    		return "";
    	}
    	if(parseInt($('#isKaiFa').val()) == 1) {
    		content = content.replaceAll('src="/files/taoke/', 'src="' + "http://www.zhongjiaoyun.net" + '/files/taoke/');
    	}
		return content;
    }
    
    //处理ueditor路径问题
    function dealWithUeditorContent(serverUrl, content) {
    	if(typeof content == "undefined" || content == null || content == "") {
    		return "";
    	}
    	if(parseInt($('#isKaiFa').val()) == 1) {
    		content = content.replaceAll('src="/files/taoke/', 'src="' + "http://www.zhongjiaoyun.net" + '/files/taoke/');
    		return content;
		}
    	
    	if(parseInt($('#sys_isSelf').val()) == 1) {
    		return content;
    	} else {
    		content = content.replaceAll('src="/files/' , 'src="' + serverUrl + '/files/');
    	}
    	
		return content;
    }
    
    //去除html标签
    function noHtml(targerStr) {
    	//去除html标签,img除外
    	//str = str.replace(/<(?!img).*?>/g,"");
    	return targerStr.replace(/<[^>]+>/g,"");;
    }
