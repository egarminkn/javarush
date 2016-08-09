<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"   %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Наполнение бизнес-таблиц данными</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link href="/css/ru/javarush/installer/create-form.css" rel="stylesheet">
</head>
<body>
<form action="/mvc/create-rows" method="post">
    <h1>Шаг 4/4. Наполнение бизнес-таблиц User и Todo данными</h1>
    <div class="form-group row">
        <label for="url" class="col-xs-2 col-form-label">MySQL URL</label>
        <div class="col-xs-10">
            <input class="form-control" type="url" value="jdbc:mysql://localhost" id="url" name="url">
        </div>
    </div>
    <div class="form-group row">
        <label for="port" class="col-xs-2 col-form-label">Порт</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" value="3306" id="port" name="port">
        </div>
    </div>
    <button class="btn btn-success" type="submit">Наполнить бизнес-таблицы User и Todo данными &gt;</button>
</form>
</body>
</html>