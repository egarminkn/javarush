<%@ page session="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="tiles/header.jsp" />

	<div class="container">
<jsp:include page="tiles/flasher.jsp" />

		<h1>Все пользователи</h1>

<jsp:include page="tiles/paginator.jsp" />

		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Имя</th>
					<th>Возраст</th>
					<th>Админ ?</th>
					<th>Дата создания</th>
					<th>Действия</th>
				</tr>
			</thead>

			<c:forEach var="user" items="${users}">
				<tr>
					<td>
						${user.id}
					</td>
					<td>
						${user.name}
					</td>
					<td>
						${user.age}
					</td>
					<td>
						<c:if test="${user.admin}">
							+
						</c:if>
					</td>
					<td>
						<fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${user.createdDate.time}" />
					</td>
					<td>
						<spring:url var="updateUrl" value="/mvc/crud/users/${user.id}/update" />
						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">
							Обновить
						</button>

						<spring:url var="deleteUrl" value="/mvc/crud/users/${user.id}/delete" />
						<button class="btn btn-danger" onclick="location.href='${deleteUrl}'">
							Удалить
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

<jsp:include page="tiles/footer.jsp" />