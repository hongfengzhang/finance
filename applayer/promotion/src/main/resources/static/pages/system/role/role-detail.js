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
                var permissions = jsonResult.result.permissionVos;
                var html = '';
                $.each(permissions,function (index,permissions){
                    html += '<span>'+permissions.name+'  <input value='+permissions.id+' type="checkbox" name="permission" disabled="disabled"/></span>&nbsp;&nbsp;&nbsp;&nbsp;';
                    if((index+1)%3==0) {
                        html += "<br>";
                    }
                });
                $("#permissions").append(html);
            } else {
                parent.layer.msg(jsonResult.message);
            }
        },
        error: function (jsonResult) {
            parent.layer.msg(jsonResult.responseJSON.message)
        }
    });
});