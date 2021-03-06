/***
*
* @todo  创客资源列表控制js
* @author 
* @version 2019-12-26
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
    var option = js_tools.getTableTemplate();
    option.url = "/admin/api/resource/page";
    option.cols = [[
      /*  {fixed: 'left',checkbox: true},
        {field: 'id', title: '资源id'},
      */  {field: 'typeId', title: '资源分类',templet:'<div>{{d.resourceType.name}}</div>'},
        {field: 'userId', title: '上传资源人',templet:'<div><a href="/user/other/info/{{d.user.id}}"' +
                ' target="_blank">{{d.user.nickName}}</a></div>'},
        {field: 'name', title: '资源名'},
        {field: 'title', title: '资源标题'},
        {field: 'content', title: '资源内容'},
        {field: 'intro', title: '资源简介'},
        {field: 'size', title: '资源大小'},
        {field: 'createTime', title: '上传时间'},
        {field: 'status', title: '状态'},
        {field: 'visitNum', title: '访问量'},
        {field: 'likeNum', title: '点赞量'},
        {fixed: 'right',field: 'top', title: '置顶',templet: '#switchTpl'},
        {fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#list_btn'} //这里的toolbar值是模板元素的选择器
    ]];

    window.resourceListIns = table.render(option);

    //监听性别操作
    form.on('switch(switchDemo)', function(obj){
        js_tools.quick_post('/admin/api/resource/update',{id:this.value,top:obj.elem.checked?'1':'0'},null);
    });

    //监听工具条
    table.on('tool(list)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {
            // 编辑功能
            WeAdminEdit('编辑','./resourceEdit.htm',data.id);
        } else if (obj.event === 'del') {
            //删除功能
            js_tools.quick_post("/admin/api/resource/delete",data,function (rs) {
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
        resourceListIns.reload({where:data.field});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});