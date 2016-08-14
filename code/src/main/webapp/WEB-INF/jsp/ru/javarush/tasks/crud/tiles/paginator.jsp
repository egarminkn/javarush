<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- countPageLinks - количество ссылок на страницы в пагинаторе. Должно быть нечетным, наприме, 5 --%>
<c:set var="countPageLinks" value="5" />
<c:choose>
	<c:when test="${page.totalCount <= countPageLinks}">
		<c:set var="countPageLinks" value="${page.totalCount}" />
		<c:set var="indexPageInPaginator" value="${page.number - 1}" />
	</c:when>
	<c:otherwise>
		<%-- Делим, но в jstl el "/" - это не целочисленное деление --%>
		<c:set var="middleOfPaginator" value="${countPageLinks / 2}" /> <%-- отсчет, начиная с 0 --%>
		<%-- Поэтому далее вычитаем дробную часть, отбрасываем десятичную точку и послеющие нули 0 --%>
		<fmt:formatNumber var="middleOfPaginator" value="${middleOfPaginator - (middleOfPaginator % 1)}" maxFractionDigits="0" />
		<c:choose>
			<c:when test="${page.number < middleOfPaginator + 1}">
				<c:set var="indexPageInPaginator" value="${page.number - 1}" />
			</c:when>
			<c:when test="${page.number > page.totalCount - middleOfPaginator}">
				<c:set var="indexPageInPaginator" value="${countPageLinks - (page.totalCount - page.number) - 1}" />
			</c:when>
			<c:otherwise>
				<c:set var="indexPageInPaginator" value="${middleOfPaginator}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<c:set var="numberOfFirstPageInPaginator" value="${page.number - indexPageInPaginator}" />

<spring:url var="firstPageUrl" value="/mvc/crud/users?pageNumber=1" />
<spring:url var="lastPageUrl" value="/mvc/crud/users?pageNumber=${page.totalCount}" />
<spring:url var="prevPageUrl" value="/mvc/crud/users?pageNumber=${page.number - 1}" />
<spring:url var="nextPageUrl" value="/mvc/crud/users?pageNumber=${page.number + 1}" />

<nav>
	<ul class="pagination">
		<li class="page-item
			<c:if test="${page.number == 1}">
				disabled
			</c:if>
		">
			<a class="page-link" href="
				<c:choose>
					<c:when test="${page.number == 1}">
						#
					</c:when>
					<c:otherwise>
						${firstPageUrl}
					</c:otherwise>
				</c:choose>
			" tabindex="-1" aria-label="Первый">
				<span aria-hidden="true">&laquo;</span>
				<span class="sr-only">Первый</span>
			</a>
		</li>
		<li class="page-item
			<c:if test="${page.number == 1}">
				disabled
			</c:if>
		">
			<a class="page-link" href="
				<c:choose>
					<c:when test="${page.number == 1}">
						#
					</c:when>
					<c:otherwise>
						${prevPageUrl}
					</c:otherwise>
				</c:choose>
			" tabindex="-1" aria-label="Предыдущий">
				<span aria-hidden="true">&lt;</span>
				<span class="sr-only">Предыдущий</span>
			</a>
		</li>

		<c:forEach begin="0" end="${countPageLinks - 1}" varStatus="counter">
			<c:set var="pageNumber" value="${numberOfFirstPageInPaginator + counter.index}" />
			<spring:url var="pageUrl" value="/mvc/crud/users?pageNumber=${pageNumber}" />
			<li class="page-item
				<c:if test="${page.number == pageNumber}">
					active
				</c:if>
			">
				<a class="page-link" href="${pageUrl}">
					${pageNumber}
					<c:if test="${page.number == pageNumber}">
						<span class="sr-only">(current)</span>
					</c:if>
				</a>
			</li>
		</c:forEach>

		<li class="page-item
			<c:if test="${page.number == page.totalCount}">
				disabled
			</c:if>
		">
			<a class="page-link" href="
				<c:choose>
					<c:when test="${page.number == page.totalCount}">
						#
					</c:when>
					<c:otherwise>
						${nextPageUrl}
					</c:otherwise>
				</c:choose>
			" aria-label="Следующий">
				<span aria-hidden="true">&gt;</span>
				<span class="sr-only">Следующий</span>
			</a>
		</li>
		<li class="page-item
			<c:if test="${page.number == page.totalCount}">
				disabled
			</c:if>
		">
			<a class="page-link" href="
				<c:choose>
					<c:when test="${page.number == page.totalCount}">
						#
					</c:when>
					<c:otherwise>
						${lastPageUrl}
					</c:otherwise>
				</c:choose>
			" aria-label="Последний">
				<span aria-hidden="true">&raquo;</span>
				<span class="sr-only">Последний</span>
			</a>
		</li>
	</ul>
</nav>