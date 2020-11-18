<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>videoPaly</title>
<style>
	html { overflow-x:hidden; }
	html { overflow-y:hidden; }
</style>
</head>
<body>
<video src="" width="640px" height="360px" controls="controls"></video>
</body>

<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../common/common.js"></script>
<script>
	$("video").attr("src",window.location.href.split("?")[1]);
</script>
</html>