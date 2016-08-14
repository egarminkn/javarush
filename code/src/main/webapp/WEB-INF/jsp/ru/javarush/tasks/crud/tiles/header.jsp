<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="mainUrl" value="/mvc/crud" />
<spring:url var="addUserUrl" value="/mvc/crud/users/add" />

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Тестовое задание CRUD</title>
	<link href="/css/bootstrap.css" rel="stylesheet" />
	<link href="/css/ru/javarush/tasks/crud/crud.css" rel="stylesheet" />
	<script src="/js/jquery-3.1.0.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${mainUrl}">Тестовое задание CRUD</a>
			</div>
			<div id="navbar">
				<ul class="nav navbar-nav navbar-right">
					<li class="active">
						<a href="${addUserUrl}">Создать пользователя</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>