<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>多文件上传</title>
</head>
<body>
<form id="form1">
    <input type="file"  name="uploadFile" id="file1" multiple>
</form>

<script src="js/jquery.min.js"></script>
<script type="text/javascript">
    $("#file1").on("change",function () {
        if (this.files.length > 0) {
            console.log("上传文件个数："+ this.files.length);
            var formData = new FormData(document.getElementById("form1"));
            formData.append("name","tzs");//追加参数
            $.ajax({
                type: "post",
                url: "http://localhost:8080/file/upload",
                data: formData,
                processData: false,
                contentType: false,
                beforeSend: function () {

                },
                success: function (data) {
                    var json = JSON.parse(data);
                    alert(json.msg);
                },
                error: function (e) {
                    alert("上传失败");
                }
            });
        }
    });
</script>
</body>
</html>
