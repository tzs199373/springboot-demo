<%@ page import="com.web.statistics.Indicator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请求计数</title>
</head>
<body>
RequestCount:<%= Indicator.getINSTANCE().getRequestCount()%><br/>
SuccessCount:<%= Indicator.getINSTANCE().getSuccessCount()%><br/>
FailureCount:<%= Indicator.getINSTANCE().getFailureCount()%><br/>

<script src="js/jquery.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
