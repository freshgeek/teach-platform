/***
*
* @todo  用户表列表控制js
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
    option.url = "/admin/api/user/page";
    option.cols = [[
        {fixed: 'left',checkbox: true},
        {field: 'id', title: 'id'},
        {field: 'roleId', title: '用户角色'},
        {field: 'phone', title: '手机号码'},
        {field: 'password', title: '密码'},
        {field: 'nickName', title: '昵称'},
        {field: 'qq', title: 'qq'},
        {field: 'school', title: '学校'},
        {field: 'headUrl', title: '头像'},
        {field: 'userTag', title: '用户标签'},
        {field: 'pointLevel', title: '积分称号'},
        {field: 'point', title: '积分'},
        {field: 'intro', title: '个人简介'},
        {fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#list_btn'} //这里的toolbar值是模板元素的选择器
    ]];

    window.userListIns = table.render(option);

    //监听工具条
    table.on('tool(list)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {
            // 编辑功能
            WeAdminEdit('编辑','./userEdit.htm',data.id);
        } else if (obj.event === 'del') {
            //删除功能
            js_tools.quick_post("/admin/api/user/delete",{id:data.id},function (rs) {
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
        userListIns.reload({where:data.field});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});