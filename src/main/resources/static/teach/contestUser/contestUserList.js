/***
*
* @todo  竞赛参与列表控制js
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
            <!--  参与时间 筛选-->
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
    option.url = "/admin/api/contestUser/page";
    option.cols = [[
        {fixed: 'left',checkbox: true},
        {field: 'id', title: '未备注字段'},
        {field: 'contestId', title: '竞赛id'},
        {field: 'userId', title: '参与用户id'},
        {field: 'achvId', title: '参与作品id'},
        {field: 'createTime', title: '参与时间'},
        {fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#list_btn'} //这里的toolbar值是模板元素的选择器
    ]];

    window.contestUserListIns = table.render(option);

    //监听工具条
    table.on('tool(list)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {
            // 编辑功能
            WeAdminEdit('编辑','./contestUserEdit.html',data.id);
        } else if (obj.event === 'del') {
            //删除功能
            js_tools.quick_post("/admin/api/contestUser/delete",data,function (rs) {
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
        contestUserListIns.reload({where:data.field});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});