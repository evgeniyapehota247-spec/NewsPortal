<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 03.11.2025
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Новости Беларуси</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --belarus-red: #d22730;
            --belarus-green: #007c30;
            --belarus-white: #ffffff;
            --belarus-red-light: #e74c3c;
            --belarus-green-light: #27ae60;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
        }

        .navbar {
            background: linear-gradient(135deg, var(--belarus-red) 0%, var(--belarus-green) 100%);
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .navbar-brand {
            font-weight: bold;
            color: var(--belarus-white) !important;
        }

        .btn-primary {
            background-color: var(--belarus-red);
            border-color: var(--belarus-red);
        }

        .btn-primary:hover {
            background-color: var(--belarus-red-light);
            border-color: var(--belarus-red-light);
        }

        .btn-success {
            background-color: var(--belarus-green);
            border-color: var(--belarus-green);
        }

        .btn-success:hover {
            background-color: var(--belarus-green-light);
            border-color: var(--belarus-green-light);
        }

        .news-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .news-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0,0,0,0.2);
        }

        .news-image {
            height: 200px;
            object-fit: cover;
        }

        .read-more {
            color: var(--belarus-green);
            text-decoration: none;
            font-weight: 500;
        }

        .read-more:hover {
            color: var(--belarus-green-light);
        }

        .language-selector .dropdown-toggle {
            border: 1px solid #dee2e6;
            background: white;
        }

        footer {
            background: linear-gradient(135deg, var(--belarus-green) 0%, var(--belarus-red) 100%);
            color: white;
        }

        .hero-section {
            background: linear-gradient(rgba(0, 124, 48, 0.8), rgba(210, 39, 48, 0.8)),
            url('https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 80px 0;
            margin-bottom: 40px;
        }

        .pagination .page-link {
            color: var(--belarus-green);
        }

        .pagination .page-item.active .page-link {
            background-color: var(--belarus-green);
            border-color: var(--belarus-green);
            color: white;
        }

        .pagination-info {
            background-color: #f8f9fa;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid var(--belarus-green);
        }

        .badge-category {
            font-size: 0.8em;
            padding: 5px 10px;
        }

        .news-counter {
            font-size: 0.9em;
            color: #6c757d;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<!-- Навигационная панель -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="home">Новости Беларуси</a>

        <div class="d-flex align-items-center">
            <!-- Выбор языка -->
            <div class="language-selector me-3">
                <div class="dropdown">
                    <button class="btn btn-light dropdown-toggle" type="button" data-bs-toggle="dropdown">
                        🇷🇺 Русский
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="changeLanguage?lang=ru">🇷🇺 Русский</a></li>
                        <li><a class="dropdown-item" href="changeLanguage?lang=be">🇧🇾 Беларуская</a></li>
                        <li><a class="dropdown-item" href="changeLanguage?lang=en">🇺🇸 English</a></li>
                    </ul>
                </div>
            </div>

            <!-- Кнопки входа и регистрации -->
            <a href="login" class="btn btn-outline-light me-2">Войти</a>
            <a href="register" class="btn btn-light">Регистрация</a>
        </div>
    </div>
</nav>

<!-- Герой секция -->
<section class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 fw-bold mb-4">Последние новости Беларуси</h1>
        <p class="lead">Будьте в курсе самых важных событий страны</p>

        <!-- Информация о пагинации -->
        <c:if test="${not empty requestScope.topNews}">
            <div class="mt-4 bg-white bg-opacity-25 p-3 rounded d-inline-block">
                <p class="mb-0">
                    Страница <strong>${requestScope.currentPage}</strong> из <strong>${requestScope.totalPages}</strong>
                    | Новости <strong>${requestScope.startNews}-${requestScope.endNews}</strong> из <strong>${requestScope.totalNewsCount}</strong>
                </p>
            </div>
        </c:if>
    </div>
</section>

<!-- Основной контент -->
<main class="container">

    <!-- Информация о текущей странице -->
    <div class="pagination-info">
        <h4>Последние новости</h4>
        <c:if test="${not empty requestScope.topNews}">
            <p class="mb-0">
                Показано <strong>${requestScope.topNews.size()}</strong> новостей на странице
                <span class="float-end">
                    <a href="allNews" class="btn btn-sm btn-outline-success">Все новости (${requestScope.totalNewsCount})</a>
                </span>
            </p>
        </c:if>
    </div>

    <!-- Список новостей -->
    <div class="row g-4">
        <c:choose>
            <c:when test="${not empty requestScope.topNews}">
                <c:forEach var="news" items="${requestScope.topNews}" varStatus="status">
                    <div class="col-md-6 col-lg-4">
                        <div class="card news-card h-100">
                            <img src="https://images.unsplash.com/photo-1589652717521-10c0d092dea9?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80"
                                 class="card-img-top news-image" alt="Новость Беларуси">
                            <div class="card-body d-flex flex-column">
                                <!-- Категория -->
                                <c:choose>
                                    <c:when test="${status.index % 3 == 0}">
                                        <span class="badge bg-success mb-2 align-self-start badge-category">Экономика</span>
                                    </c:when>
                                    <c:when test="${status.index % 3 == 1}">
                                        <span class="badge bg-primary mb-2 align-self-start badge-category">Культура</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger mb-2 align-self-start badge-category">Спорт</span>
                                    </c:otherwise>
                                </c:choose>

                                <!-- Счетчик новости -->
                                <div class="news-counter">
                                    Новость #${(requestScope.currentPage - 1) * requestScope.pageSize + status.index + 1}
                                </div>

                                <!-- Заголовок и краткое описание -->
                                <h5 class="card-title">${news.title}</h5>
                                <p class="card-text flex-grow-1">${news.brief}</p>

                                <!-- Дата и ссылка -->
                                <div class="mt-auto">
                                    <small class="text-muted">Опубликовано:
                                        <c:set var="daysAgo" value="${(requestScope.totalNewsCount - (status.index + (requestScope.currentPage - 1) * requestScope.pageSize)) % 30 + 1}" />
                                        0${daysAgo}.11.2025
                                    </small>
                                    <a href="fullNews?id=${news.id}" class="read-more d-block mt-2">Читать полностью →</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12">
                    <div class="alert alert-info text-center">
                        <h4>Новости не найдены</h4>
                        <p>В данный момент нет доступных новостей.</p>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Пагинация -->
    <c:if test="${requestScope.totalPages > 1}">
        <nav aria-label="Навигация по страницам" class="mt-5">
            <ul class="pagination justify-content-center">
                <!-- Кнопка "Первая" -->
                <c:if test="${requestScope.currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=1" aria-label="Первая">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <!-- Кнопка "Назад" -->
                <c:if test="${requestScope.currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=${requestScope.currentPage - 1}" aria-label="Предыдущая">
                            <span aria-hidden="true">&laquo;</span>
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
                        <c:when test="${i <= 3 || i >= requestScope.totalPages - 2 ||
                                       (i >= requestScope.currentPage - 2 && i <= requestScope.currentPage + 2)}">
                            <li class="page-item">
                                <a class="page-link" href="home?page=${i}">${i}</a>
                            </li>
                        </c:when>
                        <c:when test="${i == 4 && requestScope.totalPages > 7}">
                            <li class="page-item disabled">
                                <span class="page-link">...</span>
                            </li>
                        </c:when>
                        <c:when test="${i == requestScope.totalPages - 3 && requestScope.totalPages > 7}">
                            <li class="page-item disabled">
                                <span class="page-link">...</span>
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <!-- Кнопка "Вперед" -->
                <c:if test="${requestScope.currentPage < requestScope.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=${requestScope.currentPage + 1}" aria-label="Следующая">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>

                <!-- Кнопка "Последняя" -->
                <c:if test="${requestScope.currentPage < requestScope.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=${requestScope.totalPages}" aria-label="Последняя">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>

            <!-- Быстрая навигация -->
            <div class="text-center mt-3">
                <small class="text-muted me-3">Перейти:</small>
                <div class="btn-group btn-group-sm" role="group">
                    <c:forEach var="quickPage" begin="1" end="${requestScope.totalPages}">
                        <c:if test="${quickPage <= 5 || quickPage >= requestScope.totalPages - 4}">
                            <a href="home?page=${quickPage}"
                               class="btn btn-outline-success ${quickPage == requestScope.currentPage ? 'active' : ''}">
                                    ${quickPage}
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </nav>
    </c:if>

    <!-- Дополнительные ссылки -->
    <div class="row mt-5">
        <div class="col-12 text-center">
            <a href="allNews" class="btn btn-success btn-lg">Все новости (${requestScope.totalNewsCount})</a>
            <a href="categories" class="btn btn-outline-success btn-lg ms-3">Категории</a>
        </div>
    </div>
</main>

<!-- Футер -->
<footer class="mt-5 py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h5>Новости Беларуси</h5>
                <p>Самые актуальные и проверенные новости нашей страны</p>
            </div>
            <div class="col-md-4">
                <h5>Быстрые ссылки</h5>
                <ul class="list-unstyled">
                    <li><a href="home" class="text-white">Главная</a></li>
                    <li><a href="allNews" class="text-white">Все новости</a></li>
                    <li><a href="about" class="text-white">О нас</a></li>
                    <li><a href="contact" class="text-white">Контакты</a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <h5>Контакты</h5>
                <ul class="list-unstyled">
                    <li>📧 info@belarus-news.by</li>
                    <li>📞 +375 (17) 123-45-67</li>
                    <li>📍 Минск, пр. Независимости, 1</li>
                </ul>
            </div>
        </div>
        <hr class="my-4" style="border-color: rgba(255,255,255,0.3);">
        <div class="row">
            <div class="col-md-6">
                <p>&copy; 2025 Новости Беларуси. Все права защищены.</p>
            </div>
            <div class="col-md-6 text-md-end">
                <a href="privacy" class="text-white me-3">Политика конфиденциальности</a>
                <a href="terms" class="text-white">Условия использования</a>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>