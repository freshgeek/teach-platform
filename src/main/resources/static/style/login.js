$(function(){
	
	$("#q_login_id").blur(function(){
		$.ajax({
			url:"/indexlogin",
			data:{"username":$("#q_login_id").val()},
			type:"post",
			dataType:"json",
			async:false,
			cache:false,
			success:function(data){
				$("#imageUrl").attr("src",data.imageUrl);
			}
		});
		regname();
			
	});
	
	
	$("#q_login_pass").blur(function(){
	
		repassword();
			
	});	
});
function regname(){
	var value=$("#q_login_id").val();
	if(!value){
		$("#q_login_id").attr("placeholder","用户名不能为空");
	
		return false;
	}
	if(value.length>18||value.length<6){
		
		$("#q_login_id").val("");
		$("#q_login_id").attr("placeholder","用户名必须为6——18个字符");
		return false;
	}


	return true;
}
function repassword(){
	var value=$("#q_login_pass").val();
	if(!value){
		$("#q_login_pass").attr("placeholder","密码不能为空");
		
		return false;
	}
	if(value.length>16||value.length<6){
		$("#q_login_pass").val("");
		$("#q_login_pass").attr("placeholder","密码必须为6——16个字符");
		return false;
	}
	return true;
}