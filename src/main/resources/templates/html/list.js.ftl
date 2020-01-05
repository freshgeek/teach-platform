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
/***
*
* @todo  ${table.comment!}列表控制js
* @author ${author!}
* @version ${version!}${date!}
*/
layui.extend({
    admin: '/layui/js/extends/admin',
    js_tools: '/layui/js/extends/js_tools'
});

layui.use(['laydate','admin','form', 'jquery', 'table', 'js_tools'], function () {
    var laydate = layui.laydate,
        $ = layui.jquery,
        form = layui.form,
        js_tools = layui.js_tools,
        table = layui.table;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.propertyType=='Date'>
    <#--字段注释-->
        <#if field.comment!?length gt 0>
            <!--  ${field.comment} 筛选-->
        </#if>
    <#-- 日期字段-->
    //执行一个laydate实例
    laydate.render({
        elem: '#${field.propertyName}Start' //指定元素
        ,type: 'datetime'
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#${field.propertyName}End' //指定元素
        ,type: 'datetime'
    });
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

    var option = js_tools.getTableTemplate();
    option.url = "${baseUrl}/page";
    option.cols = [[
        {fixed: 'left',checkbox: true},
        <#-- ----------  BEGIN 字段循环遍历  ---------->
        <#list table.fields as field>
        {field: '${field.propertyName}', title: '<#if field.comment!?length gt 0>${field.comment}<#else>未备注字段</#if>'},
        </#list>
        <#------------  END 字段循环遍历  ---------->
        {fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#list_btn'} //这里的toolbar值是模板元素的选择器
    ]];

    window.${lowEntity}ListIns = table.render(option);

    //监听工具条
    table.on('tool(list)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {
            // 编辑功能
            WeAdminEdit('编辑','./${lowEntity}Edit.html',data.id);
        } else if (obj.event === 'del') {
            //删除功能
            js_tools.quick_post("${baseUrl}/delete",data,function (rs) {
               if (rs.code==js_tools.successCode){
                   table.reload('list');
               }else{
                   layer.alert(rs.msg);
               }
            });
        } else {
            //其他功能

        }
    });

    //表格筛选监听
    form.on('submit(search)', function(data){
        //console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        //console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        //console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        ${lowEntity}ListIns.reload({where:data.field});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});