<%@ page session="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="tiles/header.jsp" />

	<div class="container">
		<h1>
			<c:choose>
				<c:when test="${not empty nameFilter}">
					Результаты поиска по имени "${nameFilter}..."
				</c:when>
				<c:otherwise>
					Все пользователи
				</c:otherwise>
			</c:choose>
		</h1>

<jsp:include page="tiles/paginator.jsp" />

		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>
						Имя
						<input type="text" size="10" id="name-filter" style="font-weight: normal;"/>
						<spring:url var="nameFilterUrl" value="/mvc/crud/users/by" />
						<script>
							$('#name-filter').bind("enterKey", function(event) {
								if ($(this).val().trim() != "") {
									var form = document.createElement("form");
									form.setAttribute("method", "get");
									form.setAttribute("action", "${nameFilterUrl}");

									var hidden = document.createElement("input");
									hidden.setAttribute("type", "hidden");
									hidden.setAttribute("name", "name");
									hidden.setAttribute("value", $(this).val().trim())
									form.appendChild(hidden);

									document.body.appendChild(form);
									form.submit();
								}
							});
							$('#name-filter').keyup(function(event) {
								if(event.keyCode == 13) { // 13 - это клавиша enter
									$(this).trigger("enterKey");
								}
							});
						</script>
					</th>
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
						<c:choose>
							<c:when test="${not empty nameFilter}">
								<spring:url var="updateUrl" value="/mvc/crud/users/${user.id}/update?name=${nameFilter}&pageNumber=${page.number}" />
								<spring:url var="deleteUrl" value="/mvc/crud/users/${user.id}/delete?name=${nameFilter}&pageNumber=${page.number}" />
							</c:when>
							<c:otherwise>
								<spring:url var="updateUrl" value="/mvc/crud/users/${user.id}/update?pageNumber=${page.number}" />
								<spring:url var="deleteUrl" value="/mvc/crud/users/${user.id}/delete?pageNumber=${page.number}" />
							</c:otherwise>
						</c:choose>

						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">
							Обновить
						</button>

						<button class="btn btn-danger" onclick="this.disabled=true; location.href='${deleteUrl}'">
							Удалить
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

<jsp:include page="tiles/footer.jsp" />