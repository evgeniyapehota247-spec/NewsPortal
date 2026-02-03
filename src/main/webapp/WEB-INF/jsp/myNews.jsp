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
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .news-card {
            transition: transform 0.3s;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .news-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .badge-draft {
            background-color: #ffc107;
            color: #000;
        }
        .badge-published {
            background-color: #28a745;
        }
        .badge-moderation {
            background-color: #17a2b8;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Мои новости</h2>
        <a href="createNews" class="btn btn-success">
            <i class="fas fa-plus"></i> Создать новость
        </a>
    </div>

    <%-- Сообщения --%>
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <%-- Статистика --%>
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title">Всего новостей</h5>
                    <h3 class="text-primary">${totalCount}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title">Опубликовано</h5>
                    <h3 class="text-success">${publishedCount}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title">Черновики</h5>
                    <h3 class="text-warning">${draftCount}</h3>
                </div>
            </div>
        </div>
    </div>

    <%-- Фильтры --%>
    <div class="mb-3">
        <div class="btn-group" role="group">
            <a href="myNews" class="btn btn-outline-primary ${empty currentStatus ? 'active' : ''}">
                Все
            </a>
            <a href="myNews?status=2" class="btn btn-outline-success ${currentStatus == 2 ? 'active' : ''}">
                Опубликованные
            </a>
            <a href="myNews?status=1" class="btn btn-outline-warning ${currentStatus == 1 ? 'active' : ''}">
                Черновики
            </a>
            <a href="myNews?status=3" class="btn btn-outline-info ${currentStatus == 3 ? 'active' : ''}">
                На модерации
            </a>
        </div>
    </div>

    <%-- Список новостей --%>
    <c:choose>
        <c:when test="${empty myNews}">
            <div class="alert alert-info">
                <h4 class="alert-heading">У вас пока нет новостей!</h4>
                <p>Создайте свою первую новость, чтобы она появилась здесь.</p>
                <hr>
                <a href="createNews" class="btn btn-success">Создать первую новость</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <c:forEach var="news" items="${myNews}">
                    <div class="col-md-6 col-lg-4">
                        <div class="card news-card h-100">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start mb-2">
                                    <span class="badge
                                        ${news.news_status_id == 1 ? 'bg-warning' : ''}
                                        ${news.news_status_id == 2 ? 'bg-success' : ''}
                                        ${news.news_status_id == 3 ? 'bg-info' : ''}">
                                            ${news.status_name}
                                    </span>
                                    <small class="text-muted">
                                        <c:if test="${not empty news.publish_date}">
                                            ${news.publish_date}
                                        </c:if>
                                        <c:if test="${empty news.publish_date}">
                                            Не опубликовано
                                        </c:if>
                                    </small>
                                </div>

                                <h5 class="card-title">${news.title}</h5>
                                <p class="card-text">${news.brief}</p>

                                <div class="mt-3">
                                    <small class="text-muted">
                                        Создано: ${news.created_at}
                                    </small>
                                </div>
                            </div>

                                <%-- Вместо JavaScript функции используйте прямую ссылку --%>
                            <div class="card-footer bg-transparent">
                                <div class="btn-group w-100">
                                    <a href="createNews?edit=${news.id}" class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-edit"></i> Редактировать
                                    </a>
                                    <a href="viewNews?id=${news.id}" class="btn btn-outline-info btn-sm" target="_blank">
                                        <i class="fas fa-eye"></i> Просмотр
                                    </a>
                                    <a href="deleteNews?id=${news.id}"
                                       class="btn btn-outline-danger btn-sm"
                                       onclick="return confirm('Удалить новость \"${news.title}\"?')">
                                    <i class="fas fa-trash"></i> Удалить
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

    <%-- Кнопка назад --%>
    <div class="mt-4">
        <a href="userHome" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Назад в личный кабинет
        </a>
    </div>
</div>


<%-- Добавьте скрипт для предпросмотра --%>
<script>
    function previewNews(newsId) {
        window.open('viewNews?id=' + newsId, '_blank');
    }
</script>

<%-- Font Awesome для иконок --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>