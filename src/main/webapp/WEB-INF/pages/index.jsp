<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>111</title>
</head>
<body>
	<script type="text/javascript">
		var sse = new EventSource("http://localhost:8080/springtest/pull.do");
		/* sse.addEventListener("message", function(e) {
			console.log(e);
		});
		sse.addEventListener('open', function(e) {
			console.log("Connection was opened.");
		}, false);

		sse.addEventListener('error', function(e) {
			if (e.readyState == EventSource.CLOSED) {
				console.log("Connection was closed.");
			} else {
				console.log(e.readyState);
			}
		}, false); */
		console.log("==========iit...");
		sse.onmessage = function(e){
			console.log("=-----"+e);
		};
	</script>
</body>
</html>