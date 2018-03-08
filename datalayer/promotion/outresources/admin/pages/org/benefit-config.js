/**
 * 设置机构分成比例
 */
$(function() {
	// 加载layui
	layui.use(['element', 'table'], function() {});
	// 取消按钮
	$("#strategy-cancel-btn, #stockoption-cancel-btn").on('click', function() {
		parent.layer.closeAll();
	});
	// 策略提交按钮
	$("#strategy-submit-btn").on('click', function() {
		alert("策略提交");
	});
	// 期权提交按钮
	$("#stockoption-submit-btn").on('click', function() {
		alert("期权提交");
	});
});