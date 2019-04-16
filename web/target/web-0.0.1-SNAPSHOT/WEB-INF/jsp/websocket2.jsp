<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Welcome<br/>
发送内容：<input id="text" type="text" />
接收人id：<input id="target" type="text" placeholder="填0则群发"/>
<button onclick="send()">Send</button>
<button onclick="closeWebSocket()">Close</button>
<div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        websocket = new WebSocket("ws://localhost:8080/websocket/2");
    }
    else{
        alert('Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        setMessageInnerHTML("open");
    };

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    };

    //连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    };

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    };

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    function send(){
        var target = document.getElementById('target').value;
        var idReg = /^[1-9]\d*$|^0*$/;
        if (target == "") {
            alert('接收人不能为空');
            return false;
        }
        if (!idReg.test(target)) {
            alert('id必须为非负整数');
            return false;
        }
        if (content == "") {
            alert('消息不能为空');
            return false;
        }
        var content = document.getElementById('text').value;
        websocket.send(content+"|"+target);//后台通过“|”解析数据
    }
</script>
</html>
