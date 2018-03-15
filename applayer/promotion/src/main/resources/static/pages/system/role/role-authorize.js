/**
 * 添加角色
 */
$(function() {
    var roleId = parent.currentRoleId;

	// 加载layui
	layui.use(['element', 'table', 'form'], function() {});
	// 取消按钮
	$("#cancel-btn").on('click', function() {
		parent.layer.closeAll();
	});
	// 提交按钮
	$("#submit-btn").on('click', function() {
        var permissionIds = new Array();
        $("input[name='permission']:checked").each(function() {
            var permissionId = $(this).val();
            permissionIds.push(parseInt(permissionId));
        })
		$.ajax({
            type: "POST",
            url: "/promotion/role/permission/"+roleId,
            dataType: "json",
            data: {"permissionIds": permissionIds},
            traditional: true,
            success: function (jsonResult) {
            	if("200" == jsonResult.code) {
                    parent.layer.msg("授权成功");
            		parent.layer.closeAll();
                    parent.renderTable("#role-list-table");
            	} else {
            		parent.layer.msg(jsonResult.message);
            	}
            },
            error: function (jsonResult) {
                parent.layer.msg(jsonResult.responseJSON.message)
            }
        });
	});

	//初始化权限信息
    $.ajax({
        type: "GET",
        url: "/promotion/role/permissions",
        dataType: "json",
        success: function (jsonResult) {
            if("200" == jsonResult.code) {
                var permissions = jsonResult.result;
                var html = '';
                $.each(permissions,function (index,permissions){
                    html += '<span>'+permissions.name+'  <input value='+permissions.id+' type="checkbox" name="permission"/></span>&nbsp;&nbsp;&nbsp;&nbsp;';
                    if((index+1)%3==0) {
                        html += "<br>";
                    }
                });
                $("#permissions").append(html);
            } else {
                parent.layer.msg(jsonResult.message);
            }
        }
    });
});