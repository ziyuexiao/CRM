<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛CRM | 文档管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="document"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">文档列表</h3>
                    <div class="box-tools">
                        <span id="uploadBtn"><span class="text"><i class="fa fa-upload"></i> 上传文件</span></span>
                        <button class="btn btn-bitbucket btn-xs" id="newDir"><i class="fa fa-folder"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body">

                    <table class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>名称</th>
                            <th>大小</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty documentList}">
                            <tr>
                                <td colspan="5">暂时没有任何数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${documentList}" var="doc">
                            <tr>
                                <c:choose>
                                    <c:when test="${doc.type == 'dir'}">
                                        <td><i class="fa fa-folder-o"></i></td>
                                        <td><a href="/doc?fid=${doc.id}">${doc.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa fa-file-o"></i></td>
                                        <td><a href="/doc/download/${doc.id}">${doc.name}</a></td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${doc.size}</td>
                                <td>${doc.createuser}</td>
                                <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:m"/></td>
                                <td><a href="javascript:;" rel="${doc.id}" class="del">
                                    <i class="fa fa-trash text-danger"></i>
                                </a></td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<!-- /.modal -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        //上传文件
        var uploader = WebUploader.create({
            swf: "/static/plugins/webuploader/Uploader.swf",
            pick: "#uploadBtn",
            server: "/doc/file/upload",
            fileValL: "file",
            formData: {"fid": "${fid}"},
            auto: true //选择文件后直接上传
        });

        //上传文件成功

        uploader.on('uploadSuccess', function (file, resp) {
            layer.alert(resp.status)
            if (resp.status == "success") {
                window.history.go(0);
                layer.msg("上传成功");
            } else {
                layer.msg("上传失败");
            }
        });

        uploader.on('uploadError', function (file) {
            layer.alert("文件上传失败.......");
        });


        //新建文件夹
        $("#newDir").click(function () {

            var fid = ${fid};

            layer.prompt({title: '请输入文件夹名称'}, function (text, index) {
                layer.close(index);
                //拿到输入的内容
                // layer.alert(text); /doc/dir/new
                $.post("/doc/dir/new", {"fid": fid, "name": text}).done(function (resp) {
                    layer.alert(fid)
                    if (resp.status == 'success') {
                        window.history.go(0);
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });
        });
        $("#saveDirBtn").click(function () {
            if (!$("#dirname").val()) {
                $("#dirname").focus();
                return;
            }
            $("#saveDirForm").submit();
        });

        //删除文件
        $(".del").click(function () {
            //根据del获取
            var id = $(this).attr("rel");
            layer.confirm("确定要删除码？", function (index) {
                layer.close(index);
                $.get("/doc/del/" + id).done(function (resp) {
                    if (resp.status == "success") {
                        window.history.go(0);
                    } else {
                        layer.alert(resp.message);
                    }
                }).error(function () {
                    layer.alert("服务器忙.请稍后。。。");

                })
            })

        })
    });
</script>
</body>
</html>
