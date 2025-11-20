<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 18.11.2025
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ошибка - Новости Беларуси</title>
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
            min-height: 100vh;
            display: flex;
            flex-direction: column;
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

        .error-hero {
            background: linear-gradient(rgba(210, 39, 48, 0.9), rgba(0, 124, 48, 0.9)),
            url('https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 100px 0;
            text-align: center;
            flex-grow: 1;
            display: flex;
            align-items: center;
        }

        .error-content {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            padding: 3rem;
            margin-top: -50px;
            position: relative;
        }

        .error-icon {
            font-size: 4rem;
            color: var(--belarus-red);
            margin-bottom: 1.5rem;
        }

        .error-code {
            font-size: 6rem;
            font-weight: bold;
            color: var(--belarus-red);
            margin-bottom: 0;
            line-height: 1;
        }

        .error-subtitle {
            color: var(--belarus-red);
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .action-buttons {
            margin-top: 2rem;
        }

        .language-selector .dropdown-toggle {
            border: 1px solid #dee2e6;
            background: white;
        }

        footer {
            background: linear-gradient(135deg, var(--belarus-green) 0%, var(--belarus-red) 100%);
            color: white;
            margin-top: auto;
        }

        .error-animation {
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
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
                        <li><a class="dropdown-item" href="/Controller/changeLanguage?lang=ru">🇷🇺 Русский</a></li>
                        <li><a class="dropdown-item" href="/Controller/changeLanguage?lang=be">🇧🇾 Беларуская</a></li>
                        <li><a class="dropdown-item" href="/Controller/changeLanguage?lang=en">🇺🇸 English</a></li>
                    </ul>
                </div>
            </div>

            <!-- Кнопки входа и регистрации -->
            <a href="login" class="btn btn-outline-light me-2">Войти</a>
            <a href="register" class="btn btn-light">Регистрация</a>
        </div>
    </div>
</nav>

<!-- Секция ошибки -->
<section class="error-hero">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="error-content">
                    <!-- Иконка ошибки -->
                    <div class="error-icon error-animation">
                        <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                        </svg>
                    </div>

                    <!-- Код ошибки -->
                    <h1 class="error-code">500</h1>

                    <!-- Заголовок ошибки -->
                    <h2 class="error-subtitle">Внутренняя ошибка сервера</h2>

                    <!-- Описание ошибки -->
                    <div class="error-description">
                        <p class="lead text-muted mb-4">
                            К сожалению, произошла непредвиденная ошибка на сервере.
                            Наша команда уже уведомлена и работает над решением проблемы.
                        </p>

                        <div class="alert alert-warning" role="alert">
                            <strong>Что можно сделать:</strong>
                            <ul class="mt-2 mb-0">
                                <li>Попробуйте обновить страницу через несколько минут</li>
                                <li>Вернитесь на главную страницу и попробуйте снова</li>
                                <li>Если проблема повторяется, свяжитесь с технической поддержкой</li>
                            </ul>
                        </div>
                    </div>

                    <!-- Кнопки действий -->
                    <div class="action-buttons">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <a href="home" class="btn btn-success w-100">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-door me-2" viewBox="0 0 16 16">
                                        <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"/>
                                    </svg>
                                    На главную
                                </a>
                            </div>
                            <div class="col-md-4">
                                <button onclick="window.location.reload()" class="btn btn-primary w-100">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-clockwise me-2" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M8 3a5 5 0 1 0 4.546 2.914.5.5 0 0 1 .908-.417A6 6 0 1 1 8 2v1z"/>
                                        <path d="M8 4.466V.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384L8.41 4.658A.25.25 0 0 1 8 4.466z"/>
                                    </svg>
                                    Обновить
                                </button>
                            </div>
                            <div class="col-md-4">
                                <a href="/Controller/contact" class="btn btn-outline-secondary w-100">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope me-2" viewBox="0 0 16 16">
                                        <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
                                    </svg>
                                    Поддержка
                                </a>
                            </div>
                        </div>
                    </div>

                    <!-- Дополнительная информация -->
                    <div class="mt-4 pt-3 border-top">
                        <small class="text-muted">
                            Время ошибки: <span id="errorTime"></span> |
                            ID запроса: <span id="requestId">${pageContext.request.requestId}</span>
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Футер -->
<footer class="py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h5>Новости Беларуси</h5>
                <p>Самые актуальные и проверенные новости нашей страны</p>
            </div>
            <div class="col-md-4">
                <h5>Быстрые ссылки</h5>
                <ul class="list-unstyled">
                    <li><a href="/Controller/home" class="text-white">Главная</a></li>
                    <li><a href="/Controller/allNews" class="text-white">Все новости</a></li>
                    <li><a href="/Controller/about" class="text-white">О нас</a></li>
                    <li><a href="/Controller/contact" class="text-white">Контакты</a></li>
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
                <a href="/Controller/privacy" class="text-white me-3">Политика конфиденциальности</a>
                <a href="/Controller/terms" class="text-white">Условия использования</a>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Устанавливаем текущее время ошибки
    document.getElementById('errorTime').textContent = new Date().toLocaleString('ru-RU');

    // Автоматическое обновление через 30 секунд (опционально)
    setTimeout(() => {
        console.log('Автоматическое обновление страницы через 30 секунд...');
    }, 30000);
</script>
</body>
</html>