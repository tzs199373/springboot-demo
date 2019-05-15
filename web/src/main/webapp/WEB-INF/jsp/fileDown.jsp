<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件下载</title>
</head>
<body>
<button id="down">点我下载</button>
<div style="display: none;">
    <form id="downForm" action="<%=path %>/project/downTask.do"></form>
</div>

<script src="js/jquery.min.js"></script>
<script type="text/javascript">
    //下载模板
    $("#down").click(function () {
        $("#downForm").get(0).submit();
    });
</script>
</body>
</html>
