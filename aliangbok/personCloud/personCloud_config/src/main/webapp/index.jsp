<%--
  Created by IntelliJ IDEA.
  User: 10484
  Date: 2019/03/12
  Time: 1:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
</head>
<body>
<button onclick="ceshi();">点击</button>
</body>
<script>
    function ceshi() {
        console.log("进入方法的提示");
        var getJson = {
            'username': 'aliangbok',
            'password': 'aliangbok'
        };

        $.ajax({
            url: "http://localhost:8080/personCloud/testShow",
            type:'POST',
            data: getJson,
            async:true,
            cache:false,
            dataType: 'json',
            success:function(msg){
                console.log(msg);
            },
            error:function(msg) {
                console.log(msg);
            }
        });
    }
</script>
</html>
