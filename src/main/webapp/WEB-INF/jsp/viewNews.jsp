<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Pehota
  Date: 03.02.2026
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${news.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="home">Главная</a></li>
            <li class="breadcrumb-item"><a href="allNews">Все новости</a></li>
            <li class="breadcrumb-item active">${news.title}</li>
        </ol>
    </nav>

    <article>
        <header class="mb-4">
            <h1>${news.title}</h1>
            <div class="text-muted mb-3">
                <i class="fas fa-calendar ms-3 me-1"></i> ${news.publish_date}
                <i class="fas fa-eye ms-3 me-1"></i> Просмотры: 0
            </div>
        </header>

        <div class="lead mb-4">
            ${news.brief}
        </div>

        <div class="news-content mb-5">
            ${news.content_path}
        </div>

        <footer class="border-top pt-3">
            <div class="row">
                <div class="col-md-6">
                    <small class="text-muted">
                        <i class="fas fa-clock me-1"></i> Создано: ${news.created_at}
                    </small>
                </div>
                <div class="col-md-6 text-md-end">
                    <a href="javascript:window.print()" class="btn btn-outline-secondary btn-sm">
                        <i class="fas fa-print me-1"></i> Печать
                    </a>
                    <button onclick="window.close()" class="btn btn-outline-secondary btn-sm">
                        <i class="fas fa-times me-1"></i> Закрыть
                    </button>
                </div>
            </div>
        </footer>
    </article>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>