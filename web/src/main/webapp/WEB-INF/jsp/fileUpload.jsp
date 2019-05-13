<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2019/5/13
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="form1">
    <input type="file"  name="uploadFile" id="file1" onchange="MutilInput(this)">
</form>

<script src="js/jquery.min.js"></script>
<script type="text/javascript">

    function MutilInput(a) {
        if (a.files.length == 0) {

        } else {
            var form = new FormData(document.getElementById("form"));
            $.ajax({
                type: "post",
                url: "http://localhost:8080/file/upload",
                data: form,
                processData: false,
                contentType: false,
                beforeSend: function () {

                },
                success: function (data) {
                    alert("上传成功");
                },
                error: function (e) {
                    alert("上传失败");
                }
            });
        }
    }

</script>
</body>
</html>
