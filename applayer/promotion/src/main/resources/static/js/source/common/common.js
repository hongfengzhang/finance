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
            var menus = jsonResult;
            var html = '';
            $.each(menus,function (index,menu){
                    html += '<li class="layui-nav-item"><a href="javascript:;">'+menu.name+'</a><dl class="layui-nav-child">';
                    $.each(menu.childs,function (index,child){
                        html += '<dd><a href="'+child.url+'">'+child.name+'</a></dd>';
                    })
                    html += '</dl></li>';
            });
            $(".layui-nav.layui-nav-tree").append(html);
        }
    });
});