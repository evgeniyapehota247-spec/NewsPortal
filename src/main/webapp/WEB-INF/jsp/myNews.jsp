<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Pehota
  Date: 03.02.2026
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои новости</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Мои новости</h2>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <c:if test="${empty myNews}">
        <div class="alert alert-info">
            У вас пока нет созданных новостей.
            <a href="createNews" class="alert-link">Создайте первую!</a>
        </div>
    </c:if>

    <c:if test="${not empty myNews}">
        <div class="list-group">
            <c:forEach var="news" items="${myNews}">
                <div class="list-group-item">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h5>${news.title}</h5>
                            <p>${news.brief}</p>
                            <small class="text-muted">
                                Создано: ${news.createdAt}
                            </small>
                        </div>
                        <div class="btn-group">
                            <a href="editNews?id=${news.id}" class="btn btn-sm btn-outline-primary">Редактировать</a>
                            <a href="deleteNews?id=${news.id}" class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Удалить эту новость?')">Удалить</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <div class="mt-3">
        <a href="createNews" class="btn btn-success">Создать новую новость</a>
        <a href="userHome" class="btn btn-secondary">Назад в личный кабинет</a>
    </div>
</div>
</body>
</html>