<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 13.11.2025
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }

        .welcome-message {
            color: white;
            margin-right: 15px;
        }

        .user-menu {
            background: rgba(255,255,255,0.1);
            border-radius: 20px;
            padding: 5px 15px;
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

            <!-- Панель пользователя -->
            <div class="user-menu d-flex align-items-center me-3">
                <span class="welcome-message">Добро пожаловать, <strong>Иван Иванов</strong>!</span>
                <div class="dropdown">
                    <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
                       data-bs-toggle="dropdown">
                        <img src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-4.0.3&auto=format&fit=crop&w=100&q=80"
                             alt="Аватар" class="user-avatar me-2">
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="profile">
                            <i class="fas fa-user me-2"></i>Мой профиль
                        </a></li>
                        <li><a class="dropdown-item" href="myNews">
                            <i class="fas fa-newspaper me-2"></i>Мои новости
                        </a></li>
                        <li><a class="dropdown-item" href="favorites">
                            <i class="fas fa-bookmark me-2"></i>Закладки
                        </a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="createNews">
                            <i class="fas fa-plus me-2"></i>Создать новость
                        </a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger" href="logout">
                            <i class="fas fa-sign-out-alt me-2"></i>Выйти
                        </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- Герой секция -->
<section class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 fw-bold mb-4">Добро пожаловать в личный кабинет!</h1>
        <p class="lead">Управляйте своими новостями и оставайтесь в курсе событий</p>
        <a href="createNews" class="btn btn-light btn-lg mt-3">
            <i class="fas fa-plus me-2"></i>Создать новость
        </a>
    </div>
</section>

<!-- Основной контент -->
<main class="container">
    <!-- Быстрый доступ -->
    <div class="row mb-5">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Быстрый доступ</h5>
                    <div class="row g-3">
                        <div class="col-md-3">
                            <a href="createNews" class="btn btn-success w-100">
                                <i class="fas fa-plus me-2"></i>Новая запись
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="myNews" class="btn btn-outline-success w-100">
                                <i class="fas fa-list me-2"></i>Мои новости
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="favorites" class="btn btn-outline-primary w-100">
                                <i class="fas fa-bookmark me-2"></i>Закладки
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="profile" class="btn btn-outline-secondary w-100">
                                <i class="fas fa-cog me-2"></i>Настройки
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Последние новости пользователя -->
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0"><i class="fas fa-newspaper me-2"></i>Мои последние новости</h5>
                </div>
                <div class="card-body">
                    <div class="list-group list-group-flush">
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-1">Открытие нового парка в Минске</h6>
                                <small class="text-muted">Опубликовано: 02.11.2025 | Просмотры: 156</small>
                            </div>
                            <div>
                                <span class="badge bg-success me-1">Опубликовано</span>
                                <div class="btn-group btn-group-sm">
                                    <button class="btn btn-outline-primary"><i class="fas fa-edit"></i></button>
                                    <button class="btn btn-outline-danger"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-1">Интервью с министром образования</h6>
                                <small class="text-muted">Опубликовано: 01.11.2025 | Просмотры: 89</small>
                            </div>
                            <div>
                                <span class="badge bg-success me-1">Опубликовано</span>
                                <div class="btn-group btn-group-sm">
                                    <button class="btn btn-outline-primary"><i class="fas fa-edit"></i></button>
                                    <button class="btn btn-outline-danger"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-1">Новые технологии в сельском хозяйстве</h6>
                                <small class="text-muted">Черновик | Последнее изменение: 03.11.2025</small>
                            </div>
                            <div>
                                <span class="badge bg-warning me-1">Черновик</span>
                                <div class="btn-group btn-group-sm">
                                    <button class="btn btn-outline-primary"><i class="fas fa-edit"></i></button>
                                    <button class="btn btn-outline-danger"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Статистика -->
        <div class="col-lg-4">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0"><i class="fas fa-chart-bar me-2"></i>Моя статистика</h5>
                </div>
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h3 class="text-success">12</h3>
                                    <small>Опубликовано</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h3 class="text-warning">3</h3>
                                    <small>Черновики</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h3 class="text-info">1,245</h3>
                                    <small>Просмотры</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h3 class="text-danger">23</h3>
                                    <small>Комментарии</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Избранное -->
            <div class="card mt-4">
                <div class="card-header bg-warning text-dark">
                    <h5 class="mb-0"><i class="fas fa-bookmark me-2"></i>Последние закладки</h5>
                </div>
                <div class="card-body">
                    <div class="list-group list-group-flush">
                        <a href="#" class="list-group-item list-group-item-action">
                            <small class="text-muted">Экономика</small>
                            <h6 class="mb-1">Рост промышленного производства</h6>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action">
                            <small class="text-muted">Спорт</small>
                            <h6 class="mb-1">Победа белорусских атлетов</h6>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action">
                            <small class="text-muted">Культура</small>
                            <h6 class="mb-1">Новый музей в Минске</h6>
                        </a>
                    </div>
                </div>
            </div>
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

<!-- Bootstrap JS и Font Awesome -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
</body>
</html>