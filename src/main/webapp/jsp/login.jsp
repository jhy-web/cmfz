<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        /*
            1. 在提交按钮上调用方法 如 login();
            2. 在script中声明login方法 function login(){}
            3. 使用ajax技术发送异步请求
            4. 处理返回结果
         */
        $(function () {
            //给图片绑定单击事件
            $("#captchaImage").click(function () {
                $("#captchaImage").prop("src", "${path}/admin/creatImage?" + new Date().getTime());
            })
        })

        function login() {
            $.ajax({
                url: "${path}/admin/login",
                type: "post",
                datatype: "json",
                // 传递表单数据时,通常使用选择器选中该表单,使用serialize()序列化方法,将表单数据全部提交
                data: $("#loginForm").serialize(),
                success: function (data) {
                    if (data.status == 200) {
                        location.href = "${path}/jsp/main.jsp";
                    } else {
                        alert(data.message);
                    }
                }
            })
        }

    </script>
</head>
<body style=" background: url(../img/波妞宗杰.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名" autocomplete="off" name="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
                </div>
                <div class="form-group">
                    <label class="sr-only" for="form-code">Code</label>
                    <img id="captchaImage" style="height: 47px" class="captchaImage"
                         src="${path}/admin/creatImage">
                    <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                           required type="test" name="enCode" id="form-code" minlength="4">
                </div>
                <span id="msg"></span>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
