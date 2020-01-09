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
        $("#ftable").jqGrid(
            {
                url: '${pageContext.request.contextPath}/album/querypage',
                editurl: '${path}/album/subgrid',
                datatype: "json",
                height: 300,
                colNames: ['ID', '专辑名称', '封面', '评分', '作者', '播音', '章节数量', '内容简介', '发布日期'],
                colModel: [
                    {name: 'id', align: "center"},
                    {name: 'title', align: "center", editable: true},
                    {
                        name: 'des', align: "center", editable: true, edittype: "file",
                        formatter: function (data) {
                            return "<img src='" + data + "' style='width: 120px;height: 60px'>"
                        }, editoptions: {enctype: "multipart/form-data"}
                    },
                    {name: 'score', align: "center", editable: true},
                    {name: 'author', align: "center", editable: true},
                    {name: 'broadcast', align: "center", editable: true},
                    {name: 'counts', align: "center", editable: true},
                    {name: 'status', align: "center", editable: true},
                    {
                        name: 'createDate',
                        align: "center",
                        editable: true,
                        editrules: {required: true},
                        edittype: "date"
                    },
                ],
                autowidth: true, //自适应窗体宽度
                rowNum: 3,
                rowList: [10, 20, 30],
                pager: '#fpage',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                mtype: "get",
                viewrecords: true,//是否显示总行数(右下角)
                multiselect: false,
                caption: "专辑信息管理",//jqgrid左上角显示
                autowidth: true,
                styleUI: "Bootstrap",
                // 开启子表格支持
                subGrid: true,
                //添加子表格的方法
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded: function (subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id, row_id);
                },
                // 删除表格的方法
                subGridRowColapsed: function (subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#fpage', {
                edit: true,
                add: true,
                del: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            },
            {
                //修改之后的方法
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    // var albumId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "des",
                        //回调函数
                        success: function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                //添加之后的方法
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    // var albumId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "des",
                        success: function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                //删除之后的方法
                closeAfterDel: true
            });
    });

    <!-- 子表格开始 -->

    // subgrid_id 父行级id
    function addSubgrid(subgrid_id, row_id) {
        var sid, spage;
        sid = subgrid_id + "_t";
        spage = "p_" + sid;
        // 声明子表格Id
        // var sid = subgrid_id + "table";
        // // 声明子表格工具栏id
        // var spage = subgrid_id + "page";
        $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='" + spage + "' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url: "${path}/chapter/querypage?ids=" + row_id,
                editurl: '${path}/chapter/subgrid?ids=' + row_id,
                datatype: "json",
                colNames: ['ID', '章节名称', '音频大小', '音频时长', '发布时间', '所属专辑id', '操作'],
                colModel: [
                    {name: 'id', align: "center"},
                    {name: 'title', align: "center", editable: true},
                    {name: 'size', align: "center"},
                    {name: 'time', align: "center"},
                    {name: 'createDate', align: "center", editrules: {required: true}, edittype: "date"},
                    {name: 'albumId', align: "center"},
                    {
                        name: "url", formatter: function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('" + cellvalue + "')\">下载</button>&nbsp;&nbsp;";
                            //                                                                声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                            //                                                              'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                            button += "<button type=\"button\" class=\"btn btn-info\" onclick=\"onPlay('" + cellvalue + "')\">在线播放</button>";
                            return button;
                        }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                    }
                ],
                rowNum: 3,
                pager: spage,
                sortname: 'num',
                sortorder: "asc",
                height: '100%',
                autowidth: true,
                styleUI: "Bootstrap",
            });
        $("#" + sid).jqGrid('navGrid', "#" + spage, {
                edit: true,
                add: true,
                del: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            },
            {
                //修改之后的方法
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    // var chapterId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/upload",
                        type: "post",
                        datatype: "json",
                        data: {chapterId: chapterId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#sid").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            {
                //添加之后的方法
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    // var chapterId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/upload",
                        type: "post",
                        datatype: "json",
                        data: {chapterId: chapterId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#" + sid).trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                //删除之后的方法
                closeAfterDel: true
            });
    };

    function onPlay(cellValue) {
        $("#music").attr("src", "${path}/upload2/" + cellValue);
        //打开模态框
        $("#myModal").modal("show");
    }

    function download(cellValue) {
        alert(cellValue)
        location.href = "${path}/chapter/download?url=" + cellValue;
    }
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>