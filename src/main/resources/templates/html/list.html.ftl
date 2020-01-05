<#assign lowEntity="${table.entityName?uncap_first}"/>
<!DOCTYPE html>
<html>
<head>
	<!--${author}-${date}-${version!}-->
    <meta charset="UTF-8">
    <title>${table.comment!}列表-后台管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layui/css/font.css">
    <link rel="stylesheet" href="/layui/css/weadmin.css">
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="weadmin-nav">
			<span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">${table.comment!}管理</a>
        <a>
          <cite>${table.comment!}列表</cite></a>
      </span>
    <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon layui-icon-refresh-3 " style="line-height:30px"></i></a>
</div>
<div class="weadmin-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 we-search">
            <#-- ----------  BEGIN 字段循环遍历  ---------->
            <#list table.fields as field>
                <#if field.keyFlag>
					<#assign keyPropertyName="${field.propertyName}"/>
			    </#if>
            <#--字段注释-->
                <#if field.comment!?length gt 0>
                    <!--  ${field.comment} 筛选-->
                </#if>
                <#if field.propertyType=='Date'>
                <#-- 日期字段-->
                    <div class="layui-inline">
                        <input class="layui-input" placeholder="${field.comment}开始日" name="${field.propertyName}Start"
                               id="${field.propertyName}Start">
                    </div>
                    <div class="layui-inline">
                        <input class="layui-input" placeholder="${field.comment}截止日" name="${field.propertyName}End"
                               id="${field.propertyName}End">
                    </div>
                <#else>
                <#-- 普通字段 -->
                    <div class="layui-inline">
                        <input type="text" name="${field.propertyName}" placeholder="请输入${field.comment}"
                               autocomplete="off" class="layui-input">
                    </div>
                </#if>
            </#list>
            <#------------  END 字段循环遍历  ---------->
            <button class="layui-btn" lay-submit="" lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
        </form>
    </div>
    <div class="weadmin-block">
        <button class="layui-btn" onclick="WeAdminShow('添加','./${lowEntity}Add.html')">
            <i class="layui-icon"></i>添加
        </button>
    </div>
    <!--列表对象-->
    <table class="layui-table" id="list" lay-filter="list"></table>
    <script type="text/html" id="list_btn">
        <a class="layui-btn  layui-btn-xs layui-btn-normal" lay-event="edit">
            <i class="layui-icon layui-icon-edit"></i>修改
        </a>
        <a class="layui-btn  layui-btn-xs layui-btn-danger" lay-event="del">
            <i class="layui-icon layui-icon-delete"></i>删除
        </a>
    </script>

</div>

<script src="/webjars/layui/2.5.5/layui.js" charset="utf-8"></script>
<script src="./${lowEntity}List.js" type="text/javascript" charset="utf-8"></script>
</body>

</html>