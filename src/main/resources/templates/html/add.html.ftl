<#assign lowEntity="${table.entityName?uncap_first}"/>
<#assign baseUrl="/admin/api"/>
<#if package.ModuleName??>
    <#assign baseUrl="${baseUrl+'/'+package.ModuleName}"/>
</#if>
<#if controllerMappingHyphenStyle??>
    <#assign baseUrl="${baseUrl+'/'+controllerMappingHyphen}"/>
<#else>
    <#assign baseUrl="${baseUrl+'/'+table.entityPath}"/>
</#if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${table.comment!}新增-后台管理</title>
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
<div class="weadmin-body">
    <form class="layui-form" lay-filter="form" id="form">
        <#-- ----------  BEGIN 字段循环遍历  ---------->
        <#list table.fields as field>
            <#if field.keyFlag>
                <#assign keyPropertyName="${field.propertyName}"/>
            <#-- 主键字段-->
            <#else>
            <#-- 普通字段 -->
                <div class="layui-form-item">
                    <label for="${field.propertyName}" class="layui-form-label">
                        <!--必填开启-->
                        <!--<span class="we-red">*</span> -->
                        ${field.comment!'未命名字段'}
                    </label>
                    <div class="layui-input-inline">
                        <!--必填开启-->
                        <!--lay-verify="required" -->
                        <input type="text" id="${field.propertyName}" name="${field.propertyName}"
                               lay-verify="" autocomplete="off" class="layui-input">
                    </div>
                    <!--
                        必填开启
                    <div class="layui-form-mid layui-word-aux">
                        <span class="we-red">*</span>后缀提示信息
                    </div>
                    -->
                </div>
            </#if>
        </#list>
        <#------------  END 字段循环遍历  ---------->
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <button class="layui-btn" lay-filter="submit-btn" lay-submit="">增加</button>
            <button class="layui-btn layui-btn-warm" type="reset"  >重置表单</button>
        </div>
    </form>
</div>

<script src="/webjars/layui/2.5.5/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.extend({
        admin: '/layui/js/extends/admin',
        js_tools: '/layui/js/extends/js_tools'
    });

    layui.use(['form', 'layer','js_tools', 'laydate','jquery'], function () {
        var form = layui.form,
            laydate = layui.laydate,
            $ = layui.jquery,
            js_tools = layui.js_tools,
            layer = layui.layer;
        <#list table.fields as field>
            <#if field.propertyType=='Date'>
            <#--字段注释-->
            <#if field.comment!?length gt 0>
            <!--  ${field.comment} laydate实例-->
            </#if>
            <#-- 日期字段-->
            laydate.render({
                elem: '#${field.propertyName}' //指定元素
                ,type: 'datetime'
            });
            </#if>
        </#list>
        form.render();
        //自定义验证规则
        form.verify({
            nikename: function (value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
            , pass: [/(.+){6,12}$/, '密码必须6到12位']
            , repass: function (value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(submit-btn)', function (data) {
            <#list table.fields as field>
                <#if field.propertyType=='Date'>
                <#-- 日期字段-->
                data.field.${field.propertyName} = new Date(data.field.${field.propertyName});
                </#if>
            </#list>
            js_tools.quick_post("${baseUrl}/add",data.field,function (res) {
                if (res.code==js_tools.successCode){
                    layer.alert("增加成功", {icon: 6}, function () {
                        // 刷新列表
                        parent.${lowEntity}ListIns.reload();
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                    });
                }else{
                    layer.alert(res.msg);
                }
            });

            return false;
        });

    });
</script>
</body>

</html>