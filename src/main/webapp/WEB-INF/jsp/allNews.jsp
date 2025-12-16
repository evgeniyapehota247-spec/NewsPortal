<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 16.12.2025
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%-- allNews.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Все новости</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .pagination .page-link {
            color: #007c30;
        }
        .pagination .page-item.active .page-link {
            background-color: #007c30;
            border-color: #007c30;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(135deg, #d22730 0%, #007c30 100%);">
    <div class="container">
        <a class="navbar-brand" href="home">Новости Беларуси</a>
    </div>
</nav>

<main class="container my-4">
    <h1 class="mb-4">Все новости</h1>

    <!-- Список новостей -->
    <div class="row g-4">
        <c:forEach var="news" items="${requestScope.newsList}">
            <div class="col-md-6 col-lg-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${news.title}</h5>
                        <p class="card-text">${news.brief}</p>
                        <a href="fullNews?id=${news.id}" class="btn btn-outline-success btn-sm">Читать далее</a>
                    </div>
                    <div class="card-footer text-muted">
                        ID: ${news.id}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Пагинация -->
    <c:if test="${requestScope.totalPages > 1}">
        <nav aria-label="Навигация по страницам" class="mt-5">
            <ul class="pagination justify-content-center">
                <!-- Кнопка "Назад" -->
                <c:if test="${requestScope.currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="allNews?page=${requestScope.currentPage - 1}">
                            &laquo; Назад
                        </a>
                    </li>
                </c:if>

                <!-- Номера страниц -->
                <c:forEach var="i" begin="1" end="${requestScope.totalPages}">
                    <c:choose>
                        <c:when test="${i == requestScope.currentPage}">
                            <li class="page-item active">
                                <span class="page-link">${i}</span>
                            </li>
                        </c:when>
                        <c:when test="${i <= 5 || i >= requestScope.totalPages - 4 ||
                                           (i >= requestScope.currentPage - 2 && i <= requestScope.currentPage + 2)}">
                            <li class="page-item">
                                <a class="page-link" href="allNews?page=${i}">${i}</a>
                            </li>
                        </c:when>
                        <c:when test="${i == 6 || i == requestScope.totalPages - 5}">
                            <li class="page-item disabled">
                                <span class="page-link">...</span>
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <!-- Кнопка "Вперед" -->
                <c:if test="${requestScope.currentPage < requestScope.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="allNews?page=${requestScope.currentPage + 1}">
                            Вперед &raquo;
                        </a>
                    </li>
                </c:if>
            </ul>

            <!-- Информация о странице -->
            <div class="text-center mt-2 text-muted">
                Страница ${requestScope.currentPage} из ${requestScope.totalPages}
                (всего новостей: ${requestScope.totalPages * requestScope.pageSize})
            </div>
        </nav>
    </c:if>

    <!-- Кнопка возврата на главную -->
    <div class="text-center mt-4">
        <a href="home" class="btn btn-success">На главную</a>
    </div>
</main>

<footer class="bg-dark text-white py-4 mt-5">
    <div class="container text-center">
        <p>&copy; 2025 Новости Беларуси</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>