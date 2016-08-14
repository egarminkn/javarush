<%@ page session="false"%>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/mvc/crud/users" var="userActionUrl" />

<jsp:include page="tiles/header.jsp" />

	<div class="container">
		<c:choose>
			<c:when test="${isAddOperation}">
				<h1>Создание пользователя</h1>
			</c:when>
			<c:otherwise>
				<h1>Обновление пользователя</h1>
			</c:otherwise>
		</c:choose>
		<br />

		<form:form class="form-horizontal" method="post" modelAttribute="userForm" action="${userActionUrl}">
			<c:if test="${!isAddOperation}">
				<spring:bind path="id">
					<div class="form-group">
						<label class="col-sm-2 control-label">ID</label>
						<div class="col-sm-10">
							<div class="form-control">
								<form:hidden path="id" /> ${userForm.id}
							</div>
						</div>
					</div>
				</spring:bind>
			</c:if>

			<spring:bind path="name">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Имя</label>
					<div class="col-sm-10">
						<form:input path="name" type="text" class="form-control" id="name" placeholder="Имя" />
						<form:errors path="name" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="age">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Возраст</label>
					<div class="col-sm-10">
						<form:input path="age" type="number" class="form-control" id="age" placeholder="Возраст" />
						<form:errors path="age" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<c:if test="${!isAddOperation}">
				<spring:bind path="createdDate">
					<div class="form-group">
						<label class="col-sm-2 control-label">Дата создания</label>
						<div class="col-sm-10">
							<div class="form-control">
								<input type="hidden" name="createdDate"
									   value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${userForm.createdDate.time}" />" />
								<fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${userForm.createdDate.time}" />
							</div>
						</div>
					</div>
				</spring:bind>
			</c:if>

			<spring:bind path="admin">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Права доступа</label>
					<div class="col-sm-10">
						<label class="radio-inline">
							<form:radiobutton path="admin" value="yes" /> Админ
						</label>
						<br />
						<label class="radio-inline">
							<form:radiobutton path="admin" value="no" /> Обычный пользователь
						</label>
						<br />
						<form:errors path="admin" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${isAddOperation}">
							<button type="submit" class="btn-lg btn-primary pull-right">Создать</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn-lg btn-primary pull-right">Обновить</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<input type="hidden" name="isAddOperation" value="${isAddOperation}" />
		</form:form>
	</div>

<jsp:include page="tiles/footer.jsp" />