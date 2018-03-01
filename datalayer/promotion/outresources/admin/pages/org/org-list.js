/**
 * 机构列表
 */
window.renderTable = function(){};
$(function() {
	var searchData = {};
	// 加载数据
	function retrieveData(sSource, aoData, fnCallback, oSettings) {
		var draw = (aoData[3].value / 10) + 1;
		oSettings.iDraw = draw;
		// 搜索
		var keyword = aoData[5].value.value;
		searchData.page = (draw - 1);
		searchData.size = 10;
		$.ajax({
            type: "POST",
            url: "/organization/adminPage",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(searchData),
            success: function (jsonResult) {
            	var dtData = {
            		"draw": draw,
          			"recordsTotal": jsonResult.result.totalElements,
					"recordsFiltered": jsonResult.result.totalElements,
					"data": jsonResult.result.content
            	};
            	fnCallback(dtData);
            }
        });
		searchData = {};
	}
	// 渲染表格
	renderTable = function(id) {
		if($(id + "_wrapper").length > 0) {
			$(id).dataTable().fnDraw();
		} else {
			var columns = [
	            { "data": "id", "title": "机构ID", orderable: false},
	            { "data": "code", "title": "结构代码", orderable: false},
	            { "data": "name", "title": "机构名称", orderable: false},
	            { "data": "categoryName", "title": "机构类型", orderable: false},
	            { "data": "state", "title": "机构状态", orderable: false, "render": function(data, type, full, meta) {
	                var state = full.state;
	                if(state == "NORMAL") {
	                	return "正常";
	                } else if(state == "FROZEN") {
	                	return "停用";
	                } else if(state == "DESTROY") {
	                	return "注销";
	                } else {
	                	return state;
	                }
	            }},
	            { "data": "parentCode", "title": "从属机构代码", orderable: false},
	            { "data": "createTime", "title": "创建时间", orderable: false},
	            { "data": "id", "title": "操作", "className": "align-center", orderable: false, "render": function(data, type, full, meta) {
	            	var level = full.level;
	            	if(level == 1) {
	            		return "<a href='javascript:;'>查看详情</a>";
	            	} else {
	            		return "<a class='mr20' href='javascript:;'>设置分成比例</a><a class='mr20' href='javascript:;'>权限分配</a><a href='javascript:;'>查看详情</a>";
	            	}
	            }}
	        ];
			$(id).dataTable({
				"responsive": true,
		        "processing": true,
		        "serverSide": true,
		        "bPaginate": true,
		        "dom": "<'row'<'col-sm-6'><'col-sm-6'>><'row'<'col-sm-12'tr>><'row'<'col-sm-3'i><'col-sm-9'p>>",
		        "fnServerData": retrieveData,
		        "columns": columns,
		        "oLanguage": {                        //汉化     
	                "sLengthMenu": "每页显示 _MENU_ 条记录",
	                "sSearch": '<span>搜索：</span>',
	                "sZeroRecords": "没有检索到数据",     
	                "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
	                "sInfoEmtpy": "没有数据",     
	                "sProcessing": "正在加载数据...",     
	                "oPaginate": {     
	                    "sFirst": "首页",     
	                    "sPrevious": "前页",     
	                    "sNext": "后页",     
	                    "sLast": "尾页"    
	                }     
	            }
		    });
		}
	}
	// 执行
	renderTable("#org-list-table");
	// 加载layui
	layui.use(['element', 'table'], function() {
	});
	// 搜索
	$('#search-btn').on('click', function(){
		var formDataArr = $("#search-form").serializeArray();
		for(var i = 0; i < formDataArr.length; i++) {
			var name = formDataArr[i].name;
			var value = formDataArr[i].value;
			if(searchData[name]) {
				searchData[name] = searchData[name] + "," + value;
			} else {
				searchData[name] = value;
			}
		}
		renderTable("#org-list-table");
	});
	// 弹出添加页面
	$('#add-btn').on('click', function(){
		var index = layer.open({
			type: 2,
			title: '添加机构',
			shadeClose: true,
			shade: 0.8,
			area: ['500px', '400px'],
			content: 'org-add.html',
		});
	});
});