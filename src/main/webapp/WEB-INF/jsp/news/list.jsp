<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: Evgeniya.Kychinskaya--%>
<%--  Date: 16.12.2025--%>
<%--  Time: 12:45--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<%--<!DOCTYPE html>--%>
<%--<html lang="ru">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Все новости</title>--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--</head>--%>
<%--<body>--%>

<%--<jsp:include page="/WEB-INF/jsp/fragments/navbar.jsp"/>--%>

<%--<div class="container mt-5">--%>

<%--    <div class="d-flex justify-content-between align-items-center mb-4">--%>
<%--        <h2>Все новости</h2>--%>

<%--        <a href="${pageContext.request.contextPath}/news/edit?action=create"--%>
<%--           class="btn btn-success">--%>
<%--            ➕ Добавить новость--%>
<%--        </a>--%>
<%--    </div>--%>

<%--    <div class="row g-4">--%>
<%--        <c:forEach var="news" items="${newsList}">--%>
<%--            <div class="col-md-4">--%>
<%--                <div class="card h-100 shadow-sm">--%>
<%--                    <div class="card-body d-flex flex-column">--%>
<%--                        <h5 class="card-title">${news.title}</h5>--%>
<%--                        <p class="card-text flex-grow-1">${news.brief}</p>--%>

<%--                        <div class="mt-auto">--%>
<%--                            <a href="${pageContext.request.contextPath}/news/view?id=${news.id}"--%>
<%--                               class="btn btn-sm btn-outline-success">--%>
<%--                                Читать--%>
<%--                            </a>--%>

<%--                            <a href="${pageContext.request.contextPath}/news/edit?id=${news.id}"--%>
<%--                               class="btn btn-sm btn-outline-primary ms-2">--%>
<%--                                Редактировать--%>
<%--                            </a>--%>

<%--                            <form method="post"--%>
<%--                                  action="${pageContext.request.contextPath}/news/delete"--%>
<%--                                  class="d-inline">--%>
<%--                                <input type="hidden" name="id" value="${news.id}">--%>
<%--                                <button class="btn btn-sm btn-outline-danger ms-2"--%>
<%--                                        onclick="return confirm('Удалить новость?')">--%>
<%--                                    Удалить--%>
<%--                                </button>--%>
<%--                            </form>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>

<%--</div>--%>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>--%>
<%--</body>--%>
<%--</html>--%>
