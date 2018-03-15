/**
 * 资金概览
 */
window.renderTable = function(){};
$(function() {
	// 获取当前登陆的用户信息
	$.ajax({
        type: "GET",
        url: "/promotion/user/getCurrent",
        dataType: "json",
        async: false,
        success: function (jsonResult) {
        	window.currentOrgId = jsonResult.result.org.id;
        	window.currentOrgCode = jsonResult.result.org.code;
        	window.searchData = { orgId: currentOrgId }
        }
    });
	// 获取机构账户信息
	$.ajax({
        type: "GET",
        url: "/promotion/organizationAccount/orgId/" + searchData.orgId,
        dataType: "json",
        success: function (jsonResult) {
        	if("200" == jsonResult.code) {
        		var account = jsonResult.result;
        		$("#balance").html(account.availableBalance);
        	} else {
        		layer.msg(jsonResult.message);
        	}
        }
    });
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
            url: "/promotion/orgflow/pages",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            dataType: "json",
            data: $.param(searchData),
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
		searchData = {
				orgId: searchData.orgId
		};
	}
	// 渲染表格
	renderTable = function(id) {
		if($(id + "_wrapper").length > 0) {
			$(id).dataTable().fnDraw();
		} else {
			var columns = [
	            { "data": "flowNo", "title": "流水号", orderable: false},
	            { "data": "amount", "title": "金额", orderable: false},
	            { "data": "state", "title": "资金流水类型", orderable: false, "render": function(data, type, full, meta) {
	                var type = full.type;
	                if(type == "ServiceFeeAssign") {
	                	return "信息服务费分成";
	                } else if(type == "DeferredChargesAssign") {
	                	return "递延费分成";
	                } else if(type == "RightMoneyAssign") {
	                	return "期权权利金收益分成";
	                } else if(type == "Withdrawals") {
	                	return "提现";
	                } else if(type == "WithdrawalsFailure") {
	                	return "提现失败退回";
	                } else {
	                	return type;
	                }
	            }},
	            { "data": "occurrenceTime", "title": "流水时间", orderable: false}
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
	renderTable("#account-flow-table");
	// 加载layui
	layui.use(['element', 'table','laydate'], function() {
		var laydate = layui.laydate;
		laydate.render({
			elem : '#start-time'
		});
		laydate.render({
			elem : '#end-time'
		});
	});
	// 搜索
	$('#search-btn').on('click', function(){
		var formDataArr = $("#search-form").serializeArray();
		
		for(var i = 0; i < formDataArr.length; i++) {
			var name = formDataArr[i].name;
			var value = formDataArr[i].value;
			if((name == "startTime" || name == "endTime") && value) {
				value += " 00:00:00"
			}
			if(searchData[name]) {
				searchData[name] = searchData[name] + "," + value;
			} else {
				searchData[name] = value;
			}
		}
		renderTable("#account-flow-table");
	});
	// 弹出申请提现页面
	$('#withdrawals-apply-btn').on('click', function(){
		var index = layer.open({
			type: 2,
			title: '申请提现',
			shadeClose: true,
			shade: 0.8,
			area: ['500px', '225px'],
			content: 'withdrawals-apply.html',
		});
	});
	// 弹出设置提现密码页面
	$('#payment-password-btn').on('click', function(){
		var index = layer.open({
			type: 2,
			title: '设置提现密码',
			shadeClose: true,
			shade: 0.8,
			area: ['500px', '280px'],
			content: 'payment-password.html',
		});
	});
});