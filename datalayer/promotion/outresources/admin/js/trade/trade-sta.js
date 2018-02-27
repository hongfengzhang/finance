/**
 * 交易汇总
 */
$(function() {
	// 加载数据
	function retrieveData(sSource, aoData, fnCallback, oSettings) {
		var draw = (aoData[3].value / 10) + 1;
		oSettings.iDraw = draw;
		// 搜索
		var keyword = aoData[5].value.value;
		$.ajax({
            type: "GET",
            url: "/admin/data/trade-sta.json",
            contentType: "application/json",
            dataType: "json",
            success: function (jsonResult) {
            	var dtData = {
            		"draw": draw,
          			"recordsTotal": jsonResult.recordsTotal,
					"recordsFiltered": jsonResult.recordsFiltered,
					"data": jsonResult.data
            	};
            	fnCallback(dtData);
            }
        });
	}
	// 渲染表格
	function renderTable(id) {
		if($(id + "_wrapper").length > 0) {
			$(id).dataTable().fnDraw();
		} else {
			var columns = [
	            { "data": "col1", "title": "交易时间", orderable: false},
	            { "data": "col2", "title": "商户名称", orderable: false},
	            { "data": "col3", "title": "商户编号", orderable: false},
	            { "data": "col4", "title": "支付方式", orderable: false},
	            { "data": "col5", "title": "支付状态", orderable: false},
	            { "data": "col6", "title": "总交易笔数", orderable: false},
	            { "data": "col7", "title": "总交易金额", orderable: false},
	            { "data": "col8", "title": "费率", orderable: false},
	            { "data": "col9", "title": "交易手续费", orderable: false},
	            { "data": "col10", "title": "总结算金额", orderable: false},
	            { "data": "col11", "title": "总代付笔数", orderable: false},
	            { "data": "col12", "title": "总代付金额", orderable: false},
	            { "data": "col13", "title": "账户余额", orderable: false}
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
	renderTable("#trade-table");
	// 日期
	layui.use(['element', 'table', 'laydate'], function() {
		var laydate = layui.laydate;
		laydate.render({
			elem : '#start-time'
		});
		laydate.render({
			elem : '#end-time'
		});
	});
});