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
<%--    <title>Новость</title>--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--</head>--%>
<%--<body>--%>

<%--<jsp:include page="/WEB-INF/jsp/fragments/navbar.jsp"/>--%>

<%--<div class="container mt-5">--%>

<%--    <div class="card shadow">--%>
<%--        <div class="card-body">--%>

<%--            <h2 class="mb-4">--%>
<%--                <c:choose>--%>
<%--                    <c:when test="${mode == 'create'}">--%>
<%--                        Добавление новости--%>
<%--                    </c:when>--%>
<%--                    <c:otherwise>--%>
<%--                        Редактирование новости--%>
<%--                    </c:otherwise>--%>
<%--                </c:choose>--%>
<%--            </h2>--%>

<%--            <form method="post" action="${pageContext.request.contextPath}/news/edit">--%>

<%--                <input type="hidden" name="mode" value="${mode}"/>--%>

<%--                <c:if test="${mode == 'edit'}">--%>
<%--                    <input type="hidden" name="id" value="${news.id}"/>--%>
<%--                </c:if>--%>

<%--                <div class="mb-3">--%>
<%--                    <label class="form-label">Заголовок</label>--%>
<%--                    <input type="text" name="title" class="form-control"--%>
<%--                           value="${news.title}" required>--%>
<%--                </div>--%>

<%--                <div class="mb-3">--%>
<%--                    <label class="form-label">Краткое описание</label>--%>
<%--                    <textarea name="brief" class="form-control" rows="3" required>--%>
<%--                        ${news.brief}</textarea>--%>
<%--                </div>--%>

<%--                <div class="mb-3">--%>
<%--                    <label class="form-label">Текст новости</label>--%>
<%--                    <textarea name="content" class="form-control" rows="6" required>--%>
<%--                        ${news.content}</textarea>--%>
<%--                </div>--%>

<%--                <button type="submit" class="btn btn-success">--%>
<%--                    Сохранить--%>
<%--                </button>--%>

<%--                <a href="${pageContext.request.contextPath}/news"--%>
<%--                   class="btn btn-secondary ms-2">--%>
<%--                    Отмена--%>
<%--                </a>--%>
<%--            </form>--%>

<%--        </div>--%>
<%--    </div>--%>

<%--</div>--%>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>--%>
<%--</body>--%>
<%--</html>--%>
