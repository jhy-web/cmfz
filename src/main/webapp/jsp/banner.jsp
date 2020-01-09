<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/banner/queryPage",
                editurl: "${path}/banner/jqgrid",
                datatype: "json",
                colNames: ['ID', '标题', '图片', '创建时间', '描述', '状态'],
                colModel: [
                    {name: 'id', align: "center"},
                    {name: 'title', align: "center", editable: true, editrules: {required: true}},//,editrules:{required:true} 必填
                    {
                        name: 'url', align: "center", editable: true, edittype: "file",
                        formatter: function (data) {
                            return "<img src='${path}/upload/" + data + "' style='width: 120px;height: 60px'>"
                        }, editoptions: {enctype: "multipart/form-data"}
                    },
                    {
                        name: 'createDate',
                        align: "center",
                        editable: true,
                        editrules: {required: true},
                        edittype: "date"
                    },
                    {name: 'des', align: "center", editable: true},
                    {
                        name: 'status', align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            if (cellvalue == 1) {
                                return "<button class='btn btn-success' onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>解冻</button>";
                            } else {
                                return "<button class='btn btn-danger'onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                            }
                        }
                    },
                ],
                autowidth: true, //自适应窗体宽度
                rowNum: 5,//一页显示多少数行
                rowList: [10, 20, 30],
                pager: '#bannerPage',
                sortname: 'id',
                mtype: "get",//发送请求方式
                viewrecords: true,//是否显示总行数(右下角)
                sortorder: "desc",
                caption: "轮播图管理",//jqgrid左上角显示
                autowidth: true,
                multiselect: true,//复选框
                height: "300px",//高度
                // styleUI:"Bootstrap",
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit: true, add: true, del: true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    // var bannerId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        datatype: "json",
                        data: {bannerId: bannerId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }

            }, {
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    // var bannerId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        datatype: "json",
                        data: {bannerId: bannerId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                closeAfterDel: true
            });
    });

    //点击状态按钮
    function updateStatus(id, status) {
        if (status == "1") {
            $.ajax({
                url: "${path}/banner/jqgrid",
                type: "post",
                datatype: "json",
                data: {"id": id, "status": "0", "oper": "edit"},
                success: function () {
                    $("#bannerTable").trigger("reloadGrid");
                }
            });
        } else {
            $.ajax({
                url: "${path}/banner/jqgrid",
                type: "post",
                datatype: "json",
                data: {"id": id, "status": "1", "oper": "edit"},
                success: function () {
                    $("#bannerTable").trigger("reloadGrid");
                }
            });
        }
    }
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a>导出轮播图信息</a></li>
    <li><a>导入轮播图信息</a></li>
    <li><a>Excel模板下载</a></li>
</ul>
<div class="panel">
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height: 50px"></div>
</div>