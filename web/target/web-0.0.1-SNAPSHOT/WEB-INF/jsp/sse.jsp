
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SSE服务端推送测试</title>
    <style>
        h1{
            color:red;
        }
    </style>
</head>
<body>
<div id="msgFromPush"></div>
<script>
    /**
     * HTML5规范中提供了服务端事件EventSource，浏览器在实现了该规范的前提下创建一个EventSource连接后，
     * 便可收到服务端的发送的消息，这些消息需要遵循一定的格式，
     * 对于前端开发人员而言，只需在浏览器中侦听对应的事件皆可。
     * 浏览器端，需要创建一个EventSource对象，并且传入一个服务端的接口URI作为参数。
     */
    if(window.EventSource){
        var source=new EventSource("push");
        s='';
        source.addEventListener('message',function (e) {
            s+="<h1>"+e.data+"</h1>";
            document.getElementById("msgFromPush").innerHTML = s
        });

        source.addEventListener('open',function (e) {
            console.log(e);
        },false);

        source.addEventListener('error',function (e) {
            console.log(e);
        },false);

    }else{
        alert("当前这个渣渣浏览器不支持SSE");
    }
</script>
</body>
</html>