<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 11.11.2025
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: ${yourName}
  Date: ${currentDate}
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация - ${pageTitle}</title>
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

        .register-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            overflow: hidden;
        }

        .register-header {
            background: linear-gradient(135deg, var(--belarus-green) 0%, var(--belarus-red) 100%);
            color: white;
            padding: 2rem;
            text-align: center;
        }

        .register-form {
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

        .btn-register {
            background: linear-gradient(135deg, var(--belarus-green) 0%, var(--belarus-red) 100%);
            border: none;
            color: white;
            padding: 12px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }

        .password-strength {
            height: 5px;
            border-radius: 5px;
            margin-top: 5px;
            transition: all 0.3s ease;
        }

        .strength-weak { background-color: #dc3545; width: 25%; }
        .strength-medium { background-color: #ffc107; width: 50%; }
        .strength-strong { background-color: #28a745; width: 100%; }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7">
            <div class="register-container">
                <!-- Заголовок -->
                <div class="register-header">
                    <h2 class="mb-3">Создайте аккаунт</h2>
                    <p class="mb-0">Присоединяйтесь к нашему сообществу</p>
                </div>

                <!-- Форма регистрации -->
                <div class="register-form">
                    <form action="register" method="post">
                        <div class="row">
                            <!-- Имя -->
                            <div class="col-md-6 mb-3">
                                <label for="firstName" class="form-label">Имя</label>
                                <input type="text"
                                       class="form-control"
                                       id="firstName"
                                       name="firstName"
                                       placeholder="Ваше имя"
                                       required>
                            </div>

                            <!-- Фамилия -->
                            <div class="col-md-6 mb-3">
                                <label for="lastName" class="form-label">Фамилия</label>
                                <input type="text"
                                       class="form-control"
                                       id="lastName"
                                       name="lastName"
                                       placeholder="Ваша фамилия"
                                       required>
                            </div>
                        </div>

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
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   name="password"
                                   placeholder="Создайте пароль"
                                   required
                                   onkeyup="checkPasswordStrength(this.value)">
                            <div class="password-strength strength-weak" id="passwordStrength"></div>
                            <div class="form-text">
                                Пароль должен содержать минимум 8 символов, включая цифры и буквы
                            </div>
                        </div>

                        <!-- Подтверждение пароля -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Подтверждение пароля</label>
                            <input type="password"
                                   class="form-control"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   placeholder="Повторите пароль"
                                   required
                                   onkeyup="checkPasswordMatch()">
                            <div class="form-text text-danger" id="passwordMatch"></div>
                        </div>

                        <!-- Соглашение -->
                        <div class="mb-4">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="agreeTerms" required>
                                <label class="form-check-label" for="agreeTerms">
                                    Я соглашаюсь с
                                    <a href="terms" class="text-decoration-none">условиями использования</a>
                                    и
                                    <a href="privacy" class="text-decoration-none">политикой конфиденциальности</a>
                                </label>
                            </div>
                        </div>

                        <!-- Кнопка регистрации -->
                        <button type="submit" class="btn btn-register w-100 mb-3">Создать аккаунт</button>

                        <!-- Ссылка на вход -->
                        <div class="text-center">
                            <p class="mb-0">Уже есть аккаунт?
                                <a href="login" class="text-decoration-none fw-bold">Войдите</a>
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

<script>
    // Проверка сложности пароля
    function checkPasswordStrength(password) {
        const strengthBar = document.getElementById('passwordStrength');
        let strength = 0;

        if (password.length >= 8) strength++;
        if (password.match(/[a-z]/) && password.match(/[A-Z]/)) strength++;
        if (password.match(/\d/)) strength++;
        if (password.match(/[^a-zA-Z\d]/)) strength++;

        strengthBar.className = 'password-strength ';
        if (password.length === 0) {
            strengthBar.className += 'strength-weak';
        } else if (strength < 2) {
            strengthBar.className += 'strength-weak';
        } else if (strength < 4) {
            strengthBar.className += 'strength-medium';
        } else {
            strengthBar.className += 'strength-strong';
        }
    }

    // Проверка совпадения паролей
    function checkPasswordMatch() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const matchText = document.getElementById('passwordMatch');

        if (confirmPassword === '') {
            matchText.textContent = '';
        } else if (password === confirmPassword) {
            matchText.textContent = '✓ Пароли совпадают';
            matchText.className = 'form-text text-success';
        } else {
            matchText.textContent = '✗ Пароли не совпадают';
            matchText.className = 'form-text text-danger';
        }
    }
</script>
</body>
</html>