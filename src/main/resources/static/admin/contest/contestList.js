/***
*
* @todo  擂台竞赛列表控制js
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
            <!--  创建时间 筛选-->
    //执行一个laydate实例
    laydate.render({
        elem: '#createTimeStart' //指定元素
        ,type: 'datetime'
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#createTimeEnd' //指定元素
        ,type: 'datetime'
    });

    var option = js_tools.getTableTemplate();
    option.url = "/admin/api/contest/page";
    option.cols = [[
        {fixed: 'left',checkbox: true},
        {field: 'id', title: '竞赛id'},
        {field: 'userId', title: '举办者id'},
        {field: 'name', title: '竞赛名'},
        {field: 'title', title: '竞赛标题'},
        {field: 'coverUrl', title: '竞赛封面'},
        {field: 'intro', title: '简介'},
        {field: 'createTime', title: '创建时间'},
        {fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#list_btn'} //这里的toolbar值是模板元素的选择器
    ]];

    window.contestListIns = table.render(option);

    //监听工具条
    table.on('tool(list)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {
            // 编辑功能
            WeAdminEdit('编辑','./contestEdit.html',data.id);
        } else if (obj.event === 'del') {
            //删除功能
            js_tools.quick_post("/admin/api/contest/delete",data,function (rs) {
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
        contestListIns.reload({where:data.field});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});