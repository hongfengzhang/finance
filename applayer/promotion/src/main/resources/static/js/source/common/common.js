/**
 * 公共方法，公共操作
 */
$(function() {
	$("#logout-link").on("click", function(){
		$.ajax({
            type: "GET",
            url: "/promotion/logout",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            success: function (jsonResult) {
            	window.location.href = "/promotion/login";
            }
        });
	});

    $.ajax({
        type: "GET",
        url: "/promotion/menus",
        dataType: "json",
        success: function (jsonResult) {
        	var url = window.location.href;
            var menus = jsonResult;
            $.each(menus,function (index,menu){
                var html = '<li class="layui-nav-item"><a href="javascript:;" style="text-decoration:none;">'+menu.name+'</a><dl class="layui-nav-child">';
                var liOpened = false;
                $.each(menu.childs,function (index,child) {
                	if(url.indexOf(child.url) >= 0) {
                		html += '<dd class="layui-this"><a href="'+child.url+'">'+child.name+'</a></dd>';
                		liOpened = true;
                	} else {
                		html += '<dd><a href="'+child.url+'">'+child.name+'</a></dd>';
                	}
                })
                html += '</dl></li>';
                if(liOpened) {
                	html = html.replace(/layui-nav-item/, "layui-nav-item layui-nav-itemed");
                }
                $(".layui-nav.layui-nav-tree").append(html);
            });
        }
    });
});