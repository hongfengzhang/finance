/**
 * 添加角色
 */
$(function() {
    var roleId = parent.currentRoleId;

	// 加载layui
	layui.use(['element', 'table', 'form'], function() {});

	//初始化权限信息
    $.ajax({
        type: "GET",
        url: "/promotion/role/"+roleId,
        dataType: "json",
        success: function (jsonResult) {
            if("200" == jsonResult.code) {
                var menus = jsonResult.result.menusVos;
                var html = '';
                $.each(menus,function (index,menu){
                    if(menu.pid==0) {
                        html += '<span>'+menu.name+'</span><br>&nbsp;&nbsp;&nbsp;&nbsp;';
                        $.each(menus,function (index,cmenu){
                            if(cmenu.pid==menu.id) {
                                html += '<span>'+cmenu.name+'  <input value='+cmenu.id+' type="checkbox" name="menu" disabled="disabled"/></span>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                        })
                        html += "<br>";
                    }
                });
                $("#menus").append(html);
            } else {
                parent.layer.msg(jsonResult.message);
            }
        },
        error: function (jsonResult) {
            parent.layer.msg(jsonResult.responseJSON.message)
        }
    });
});