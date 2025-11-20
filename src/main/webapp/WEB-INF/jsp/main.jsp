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
    </div>
</section>

<!-- Основной контент -->
<main class="container">
    <div class="row g-4">


        <c:forEach var="news" items="${requestScope.topNews}">
        <!-- Новость 1 -->
        <div class="col-md-4">
            <div class="card news-card h-100">
                <img src="https://images.unsplash.com/photo-1589652717521-10c0d092dea9?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80"
                     class="card-img-top news-image" alt="Экономика Беларуси">
                <div class="card-body d-flex flex-column">
                    <span class="badge bg-success mb-2 align-self-start">Экономика</span>
                    <h5 class="card-title">${news.title}</h5>
                    <p class="card-text flex-grow-1">${news.brief}</p>
                    <div class="mt-auto">
                        <small class="text-muted">Опубликовано: 03.11.2025</small>
                        <a href="fullNews?id=1" class="read-more d-block mt-2">Посмотреть всю новость →</a>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>>




<%--        <!-- Новость 2 -->--%>
<%--        <div class="col-md-4">--%>
<%--            <div class="card news-card h-100">--%>
<%--                <img src="https://images.unsplash.com/photo-1541336032412-2048a678540d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80"--%>
<%--                     class="card-img-top news-image" alt="Культурное событие">--%>
<%--                <div class="card-body d-flex flex-column">--%>
<%--                    <span class="badge bg-primary mb-2 align-self-start">Культура</span>--%>
<%--                    <h5 class="card-title">Открытие нового музея в Минске</h5>--%>
<%--                    <p class="card-text flex-grow-1">В столице открылся современный музей истории Беларуси с интерактивными экспонатами и цифровыми технологиями.</p>--%>
<%--                    <div class="mt-auto">--%>
<%--                        <small class="text-muted">Опубликовано: 02.11.2025</small>--%>
<%--                        <a href="fullNews?id=2" class="read-more d-block mt-2">Посмотреть всю новость →</a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <!-- Новость 3 -->--%>
<%--        <div class="col-md-4">--%>
<%--            <div class="card news-card h-100">--%>
<%--                <img src="https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80"--%>
<%--                     class="card-img-top news-image" alt="Спортивное событие">--%>
<%--                <div class="card-body d-flex flex-column">--%>
<%--                    <span class="badge bg-danger mb-2 align-self-start">Спорт</span>--%>
<%--                    <h5 class="card-title">Белорусские атлеты завоевали медали на международных соревнованиях</h5>--%>
<%--                    <p class="card-text flex-grow-1">На чемпионате Европы по легкой атлетике белорусские спортсмены показали выдающиеся результаты, завоевав 3 золотые медали.</p>--%>
<%--                    <div class="mt-auto">--%>
<%--                        <small class="text-muted">Опубликовано: 01.11.2025</small>--%>
<%--                        <a href="fullNews?id=3" class="read-more d-block mt-2">Посмотреть всю новость →</a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

    <!-- Дополнительные ссылки -->
    <div class="row mt-5">
        <div class="col-12 text-center">
            <a href="allNews" class="btn btn-success btn-lg">Все новости</a>
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