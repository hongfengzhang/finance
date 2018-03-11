/**
 * 添加机构
 */
$(function() {
	// 加载layui
	/*
	layui.use(['element', 'table', 'form'], function() {
		var form = layui.form;
		// 获取平台select
		$.ajax({
	        type: "GET",
	        url: "/organization/listByParentId?parentId=0",
	        dataType: "json",
	        success: function (jsonResult) {
	        	var platformDataArr = jsonResult.result;
	        	for(var i = 0; i < platformDataArr.length; i++) {
	        		var selected = "";
	        		if(i == 0) {
	        			selected = "selected";
	        			// $('input[name="parentCode"]').val(platformDataArr[i].code);
	        			$('input[name="parentId"]').val(platformDataArr[i].id);
	        		}
	        		$("#platform-select").append('<option ' + selected + ' code="' + platformDataArr[i].code + '" value="' + platformDataArr[i].id + '">' + platformDataArr[i].name + '</option>');
	        	}
	        	form.render();
	        }
	    });
		// 切换机构类别
		form.on('radio(categoryId)', function (data) {
            var value = data.value;
            if(value == '2') {
            	$("#operate-select-container").css('display', 'none');
            	// $('input[name="parentCode"]').val($("#platform-select option:selected").attr("code"));
            	$('input[name="parentId"]').val($("#platform-select").val());
            } else if(value == '3') {
            	$("#operate-select-container").css('display', '');
            	var platformOrgId = $("#platform-select").val();
            	// 获取运营中心select
            	$.ajax({
                    type: "GET",
                    url: "/organization/listByParentId?parentId=" + platformOrgId,
                    dataType: "json",
                    success: function (jsonResult) {
                    	$("#operate-select").empty();
                    	var operateDataArr = jsonResult.result;
                    	for(var i = 0; i < operateDataArr.length; i++) {
                    		var selected = "";
                    		if(i == 0) {
                    			selected = "selected";
                    			// $('input[name="parentCode"]').val(operateDataArr[i].code);
                    			$('input[name="parentId"]').val(operateDataArr[i].id);
                    		}
                    		$("#operate-select").append('<option ' + selected + ' code="' + operateDataArr[i].code + '" value="' + operateDataArr[i].id + '">' + operateDataArr[i].name + '</option>');
                    	}
                    	form.render();
                    }
                });
            }
        });
		// 切换运营中心
		form.on('select(operateOrgId)', function(data) {
			// $('input[name="parentCode"]').val($("#operate-select option:selected").attr("code"));
			$('input[name="parentId"]').val($("#operate-select").val());
		});
	});
	*/
	// 取消按钮
	$("#cancel-btn").on('click', function() {
		parent.layer.closeAll();
	});
	// 提交按钮
	$("#submit-btn").on('click', function() {
		var formData = $("#add-form").serialize();
		if(formData.length > 0) {
			formData = "parentId=" + parent.searchData.parentId + "&" + formData;
		}
		$.ajax({
            type: "POST",
            url: "/organization/",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            dataType: "json",
            data: formData,
            success: function (jsonResult) {
            	if("200" == jsonResult.code) {
            		parent.renderTable("#org-list-table");
            		parent.layer.closeAll();
            	} else {
            		parent.layer.msg(jsonResult.message);
            	}
            }
        });
	});
});