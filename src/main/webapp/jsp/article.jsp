<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url: "${path}/article/queryPage",
                editurl: "${path}/article/jqgrid",
                datatype: "json",
                colNames: ['ID', '标题', '图片', '文章内容', '创建日期', '发布日期', '状态', '上师id', '操作'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'title', align: "center", editable: true, editrules: {required: true}},//,editrules:{required:true} 必填
                    {
                        name: 'img', align: "center", editable: true, edittype: "file",
                        formatter: function (data) {
                            return "<img src='" + data + "' style='width: 100px;height: 60px'>"
                        }, editoptions: {enctype: "multipart/form-data"}
                    },
                    {name: 'content', align: "center", editable: true},
                    {name: 'createDate', align: "center", editrules: {required: true}, edittype: "date"},
                    {name: 'publishDate', align: "center", editrules: {required: true}, edittype: "date"},
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
                    {name: 'guruId', align: "center", editable: true},
                    {
                        name: 'option',
                        formatter: function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改</button>&nbsp;&nbsp;";
                            button += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('" + rowObject.id + "')\">删除</button>";
                            return button;
                        }
                    }
                ],
                autowidth: true, //自适应窗体宽度
                rowNum: 3,//一页显示多少数行
                rowList: [10, 20, 30],
                pager: '#articlePage',
                sortname: 'id',
                mtype: "get",//发送请求方式
                viewrecords: true,//是否显示总行数(右下角)
                sortorder: "desc",
                caption: "文章管理",//jqgrid左上角显示
                autowidth: true,
                //multiselect: true,复选框
                height: "280px",//高度
                styleUI: "Bootstrap",//主题
            });
        $("#articleTable").jqGrid('navGrid', '#articlePage', {
                edit: true,
                add: true,
                del: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var articleId = response.responseJSON.articleId;
                    $.ajaxFileUpload({
                        url: "${path}/article/upload",
                        type: "post",
                        datatype: "json",
                        data: {articleId: articleId},
                        fileElementId: "img",
                        success: function (data) {
                            $("#articleTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }

            }, {
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var articleId = response.responseJSON.articleId;
                    // var articleId = postData.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/article/upload",
                        type: "post",
                        datatype: "json",
                        data: {articleId: articleId},
                        fileElementId: "img",
                        success: function (data) {
                            $("#articleTable").trigger("reloadGrid");
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
                url: "${path}/article/jqgrid",
                type: "post",
                datatype: "json",
                data: {"id": id, "status": "0", "oper": "edit"},
                success: function () {
                    $("#articleTable").trigger("reloadGrid");
                }
            });
        } else {
            $.ajax({
                url: "${path}/article/jqgrid",
                type: "post",
                datatype: "json",
                data: {"id": id, "status": "1", "oper": "edit"},
                success: function () {
                    $("#articleTable").trigger("reloadGrid");
                }
            });
        }
    }

    // 点击添加文章时触发事件
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id", "");
        $.ajax({
            url: "${path}/guru/showAllGuru",
            datatype: "json",
            type: "post",
            success: function (data) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }

    // 点击修改时触发事件
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
        // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articleTable").jqGrid("getRowData", id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#editor_id", data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option = "";
        if (data.status == "展示") {
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        } else {
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);
        // 处理上师信息

        $.ajax({
            url: "${pageContext.request.contextPath}/guru/showAllGuru",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id == data.guruId) {
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }
        });
        $("#myModal").modal("show");
    }

    // 文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            type: "post",
            // ajaxFileUpload 不支持serialize() 格式化形式
            // 只支持{"id":1,XXX:XX}
            // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码

            // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {
                //关闭模态框
                $("#close").click();
                $("#articleTable").trigger("reloadGrid");
            }
        })
    }
</script>
<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>文章信息</a></li>
    <li><a onclick="showArticle()">添加文章</a></li>
</ul>
<div class="panel">
    <table id="articleTable"></table>
    <div id="articlePage" style="height: 80px"></div>
</div>