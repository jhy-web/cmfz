<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $("#Usertable").jqGrid({
            url: "${path}/user/querypage",
            editurl: "${path}/user/addAndDelete",
            datatype: "json",
            height: 200,
            colNames: ['id', '电话', '密码', '头像', '名字', '昵称', '性别', '地区', '注册时间'],
            colModel: [
                {name: 'id', align: 'center', hidden: true},
                {name: 'phone', align: 'center', editable: true},
                {name: 'password', align: 'center', editable: true},
                {
                    name: 'photo', align: 'center', hidden: true, edittype: "file",
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                    }
                },
                {name: 'name', align: 'center', editable: true},
                {name: 'nick_name', align: 'center', editable: true},
                {name: 'sex', align: 'center', editable: true},
                {name: 'location', align: 'center', editable: true},
                {
                    name: 'rigest_date', align: 'center',
                    editrules: {required: true},
                    edittype: "date"
                }
            ],
            autowidth: true,
            pager: "#Userpage",
            rowNum: 3,
            rowList: [5, 10, 15, 20],
            sortorder: "desc",
            mtype: "get",
            viewrecords: true,
            caption: "用户",
            styleUI: "Bootstrap",
        });
        $("#Usertable").jqGrid('navGrid', '#Userpage', {edit: true, add: true, del: true},
            {
                //修改之后得操作
                closeAfterEdit: true,
            }, {
                //添加之后得操作
                closeAfterAdd: true,
            }, {
                //删除之后得操作
                closeAfterDel: true,
            });
    })

    function poiExe() {
        $.ajax({
            url: "${path}/user/out",
            type: "post",
            datatype: "json",
            success: function () {
                $("#Usertable").trigger("reloadGrid");
            }
        })
    }

    function lead() {
        $.ajax({
            url: "${path}/user/in",
            type: "post",
            datatype: "json",
            success: function () {
                $("#Usertable").trigger("reloadGrid");
            }
        })
    }
</script>
<body>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
    <li><a onclick="poiExe()">导出</a></li>
    <li><a onclick="lead()">导入</a></li>
</ul>
<table id="Usertable"></table>

<div id="Userpage" style="height: 50px">

</div>
</body>