/**
 * 机构详情
 */
$(function() {
	var currentOrgId = parent.currentOrgId;
	// 加载layui
	layui.use(['element', 'table'], function() {});
	// 修改机构名称
	$("#name-modify-link").on('click', function(){
		$("#name-display").css("display", "none");
		$("#name-modify").css("display", "");
	});
	$("#name-modify-btn").on('click', function(){
		// 请求修改机构名称
		var formData = $("#name-modify-form").serialize();
		$.ajax({
            type: "POST",
            url: "/organization/modifyName",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            dataType: "json",
            data: formData,
            success: function (jsonResult) {
            	if("200" == jsonResult.code) {
            		$('span[data="name"], input[data="name"]').each(function(i){
        				var tagName = this.tagName;
        				if(tagName.toUpperCase() == "SPAN") {
        					$(this).html(jsonResult.result.name);
        				} else {
        					$(this).val(jsonResult.result.name);
        				}
        			});
            		$("#name-display").css("display", "");
            		$("#name-modify").css("display", "none");
            	} else {
            		parent.layer.msg(jsonResult.message);
            	}
            }
        });
	});
	// 初始加载结构信息
	$.ajax({
        type: "GET",
        url: "/organization/detail?orgId=" + currentOrgId,
        dataType: "json",
        success: function (jsonResult) {
        	if("200" == jsonResult.code) {
        		var org = jsonResult.result;
        		for(var prop in org) {
        			$('span[data="' + prop + '"], input[data="' + prop + '"]').each(function(i){
        				var tagName = this.tagName;
        				if(tagName.toUpperCase() == "SPAN") {
        					$(this).html(org[prop]);
        				} else {
        					$(this).val(org[prop]);
        				}
        			});
        		}
        	} else {
        		parent.layer.msg(jsonResult.message);
        	}
        }
    });
});