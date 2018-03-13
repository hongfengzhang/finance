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
});