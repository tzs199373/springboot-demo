<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020/5/27
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <button id="pay" style="width: 500px;height: 500px">点我支付</button>
</body>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#pay").click(function () {
            if (typeof WeixinJSBridge == "undefined"){
                if( document.addEventListener ){
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                }else if (document.attachEvent){
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            }else{
                onBridgeReady();
            }
        })


    });

    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":"wx7a69c82cd67514c8",     //公众号名称，由商户传入
                "timeStamp":"1590566861",         //时间戳，自1970年以来的秒数
                "nonceStr":"5a34351db740460485fe860b4fcb17c4", //随机串
                "package":"prepay_id=wx2716074188887522d9cc136c1028400900",
                "signType":"RSA",         //微信签名方式：
                "paySign":"HuHnlte37tuPYDy6jM2mh2al1v3Wh77VoN+saJZhogSh+PGBnaT4xdGDZ0YcK2K6wcbignny/WZb1dL4oNc0UKRmEx0bXSVpegzbyEA8b3HrT0D4wWzsS17CSHrdVxxmsSb7CMGyQ4d2qM/gwj7hftHeNTMEaIEj66BwgU1xpFe60tm2c+6JPc8Rx0AtsmOwFvEMJ/x+O6ccayDL2vg7PUB8liNyf94vHO6ajAjxMwWNfmDEicl0jWJAHJnGPvMvhBiQaSSDykF8ezY/Z9tg//q7UGYHXzNdZd07nR7e8H+3RMP9wLz1HTp8QfHku6OIw6W6IBUE6Jdzzk3rKmSlgQ==" //微信签名
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ){
                    // 使用以上方式判断前端返回,微信团队郑重提示：
                    //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                }
            });
    }
</script>
</html>
