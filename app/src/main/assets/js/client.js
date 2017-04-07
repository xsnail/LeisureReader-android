$(function() {

	// 数据提交
	$('#submit').click(function() {
		$.ajax({
			type : "POST",
			url : "/request_data/",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				"username" : $('#username').val(),
				"password" : $('#password').val()
			}),
			success : function(data) {
				alert("success");
			},
			error : function(data) {
				alert("error");
			}
		});
	});

});