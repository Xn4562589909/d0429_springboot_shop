<%--
  Created by IntelliJ IDEA.
  User: 54640
  Date: 2023/4/3
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../include/header.jsp" />
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form class="form-horizontal" role="form" action="/admin/property/update" method="post">
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="hidden" class="form-control" id="id" name="id" value="${pt.id}" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="hidden" class="form-control" id="cid" name="cid" value="${pt.category.id}" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="propertyName" class="col-sm-2 control-label">属性名称:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="propertyName" name="name" value="${pt.name}" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">更新</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
