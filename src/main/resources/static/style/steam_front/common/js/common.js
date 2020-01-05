
function common_getPage(pageNo,size,pageCount){
	var html = "";
		var begin = 0;
		var end = 0;
		if(pageNo>3){
			begin = pageNo-(size-3);
			end = pageNo+(size-2);
		}else{
			begin = 1;
			end = size;
		}
		for(var i=begin; i<end&&i<pageCount; i++){
			if(i==pageNo){
				html += '<a data="'+i+'" class="common_pageClick">'+i+'</a>';
			}else{
				html += '<a data="'+i+'">'+i+'</a>';
			}
		}
		if(pageCount>(pageNo+3)){
			html += '<a data="0">...</a>';
		}
		if(pageNo==pageCount){
			html += '<a data="'+pageCount+'" class="common_pageClick">'+pageCount+'</a>';
		}else{
			html += '<a data="'+pageCount+'">'+pageCount+'</a>';
		}
		$(".common_page_comment").html(html);
}