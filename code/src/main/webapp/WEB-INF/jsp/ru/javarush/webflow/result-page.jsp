<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Результат</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link href="/css/ru/javarush/installer/create-form.css" rel="stylesheet">
</head>
<body>
    <form method="post">
        <h1>${myHeader}</h1>
        <div class="form-group row">
            <label for="sign" class="col-xs-2 col-form-label">Знак</label>
            <div class="col-xs-10">
                <input class="form-control" type="text" id="sign" name="sign" value=
                    <c:choose>
                        <c:when test="${model.signValue=='POSITIVE'}">
                            "ПОЛОЖИТЕЛЬНОЕ"
                        </c:when>
                        <c:when test="${model.signValue=='NEGATIVE'}">
                            "ОТРИЦАТЕЛЬНОЕ"
                        </c:when>
                        <c:otherwise>
                            "НОЛЬ"
                        </c:otherwise>
                    </c:choose>
                >
            </div>
        </div>
        <div class="form-group row">
            <label for="val" class="col-xs-2 col-form-label">Число</label>
            <div class="col-xs-10">
                <input class="form-control" type="text" value="${model.value}" id="val" name="val">
            </div>
        </div>
        <a href="${flowExecutionUrl}&_eventId=cancel">
            ${exitLink}
        </a>
    </form>
</body>
</html>