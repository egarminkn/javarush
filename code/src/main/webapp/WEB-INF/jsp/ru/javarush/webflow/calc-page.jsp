<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Калькулятор</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link href="/css/ru/javarush/installer/create-form.css" rel="stylesheet">
</head>
<body>
    <%-- Метод GET для spring webflow недопустим --%>
    <form method="post">
        <h1>Вычислим куб либо кубический корень числа</h1>
        <div class="form-group row">
            <label for="num" class="col-xs-2 col-form-label">Число</label>
            <div class="col-xs-10">
                <input class="form-control" type="number" value="1000" id="num" name="num">
            </div>
        </div>
        <button class="btn btn-success" name="_eventId_cube" type="submit">Вычислить куб</button>
        <input class="btn btn-success" name="_eventId_cube-root" type="submit" value="Вычислить куб. корень">
        <a href="${flowExecutionUrl}&_eventId=cancel">Отказаться от вычислений</a>
    </form>
</body>
</html>