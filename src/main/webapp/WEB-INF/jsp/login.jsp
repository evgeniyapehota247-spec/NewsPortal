<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 11.11.2025
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход - ${pageTitle}</title>
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
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }

        .login-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .login-header {
            background: linear-gradient(135deg, var(--belarus-red) 0%, var(--belarus-green) 100%);
            color: white;
            padding: 2rem;
            text-align: center;
        }

        .login-form {
            padding: 2rem;
        }

        .form-control {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 12px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: var(--belarus-green);
            box-shadow: 0 0 0 0.2rem rgba(0, 124, 48, 0.25);
        }

        .btn-login {
            background: linear-gradient(135deg, var(--belarus-red) 0%, var(--belarus-green) 100%);
            border: none;
            color: white;
            padding: 12px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .alert-error {
            background: linear-gradient(135deg, #ffebee 0%, #ffcdd2 100%);
            border: 1px solid var(--belarus-red);
            border-radius: 8px;
            color: #b71c1c;
            padding: 12px 16px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="login-container">
                <!-- Заголовок -->
                <div class="login-header">
                    <h2 class="mb-3">Добро пожаловать!</h2>
                    <p class="mb-0">Войдите в свой аккаунт</p>
                </div>

                <!-- Форма входа -->
                <div class="login-form">
                    <!-- Блок ошибки авторизации -->
                    <c:if test="${param.authError eq true}">
                        <div class="alert-error mb-4">
                            <div class="d-flex align-items-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-exclamation-triangle-fill me-2" viewBox="0 0 16 16">
                                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                </svg>
                                <strong>Ошибка авторизации</strong>
                            </div>
                            <p class="mb-0 mt-2">Неверный email или пароль. Пожалуйста, проверьте введенные данные и попробуйте снова.</p>
                        </div>
                    </c:if>

<%--                    сессия--%>
                    <c:if test="${param.message}">
                        <div class="alert-error mb-4">
                            <div class="d-flex align-items-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-exclamation-triangle-fill me-2" viewBox="0 0 16 16">
                                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                </svg>
                                <strong>Ваша сессия истекла</strong>
                            </div>
                            <p class="mb-0 mt-2">Пожалуйста, авторизуйтесь.</p>
                        </div>
                    </c:if>


                    <form action="login" method="post">
                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email адрес</label>
                            <input type="email"
                                   class="form-control"
                                   id="email"
                                   name="email"
                                   placeholder="ваш@email.com"
                                   required>
                        </div>

                        <!-- Пароль -->
                        <div class="mb-4">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   name="password"
                                   placeholder="Введите пароль"
                                   required>
                            <div class="form-text">
                                <a href="forgotPassword" class="text-decoration-none">Забыли пароль?</a>
                            </div>
                        </div>

                        <!-- Запомнить меня -->
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="remember" name="remember">
                            <label class="form-check-label" for="remember">Запомнить меня</label>
                        </div>

                        <!-- Кнопка входа -->
                        <button type="submit" class="btn btn-login w-100 mb-3">Войти</button>
                        <input type="hidden" name="command" value="do_auth"/>
                        <!-- Ссылка на регистрацию -->
                        <div class="text-center">
                            <p class="mb-0">Нет аккаунта?
                                <a href="register" class="text-decoration-none fw-bold">Зарегистрируйтесь</a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Ссылка назад -->
            <div class="text-center mt-4">
                <a href="home" class="text-decoration-none">
                    ← Вернуться на главную
                </a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>