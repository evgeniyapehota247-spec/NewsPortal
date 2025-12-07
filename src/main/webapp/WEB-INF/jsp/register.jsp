<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Kychinskaya
  Date: 11.11.2025
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .alert-error {
            background: linear-gradient(135deg, #ffebee 0%, #ffcdd2 100%);
            border: 1px solid var(--belarus-red);
            border-radius: 8px;
            color: #b71c1c;
            padding: 12px 16px;
            margin-bottom: 20px;
        }

        .alert-success {
            background: linear-gradient(135deg, #d4edda 0%, #c3e6cb 100%);
            border: 1px solid var(--belarus-green);
            border-radius: 8px;
            color: #155724;
            padding: 12px 16px;
            margin-bottom: 20px;
        }

        .is-invalid {
            border-color: var(--belarus-red) !important;
        }

        .invalid-feedback {
            color: var(--belarus-red);
            font-size: 0.875em;
            margin-top: 0.25rem;
        }
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

                    <!-- Блок ошибок -->
                    <c:if test="${not empty error}">
                        <div class="alert-error">
                            <div class="d-flex align-items-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-exclamation-triangle-fill me-2" viewBox="0 0 16 16">
                                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                </svg>
                                <strong>Ошибка регистрации</strong>
                            </div>
                            <p class="mb-0 mt-2">${error}</p>
                        </div>
                    </c:if>

                    <!-- Блок предупреждений -->
                    <c:if test="${not empty warning}">
                        <div class="alert alert-warning alert-dismissible fade show">
                            <strong>Внимание:</strong> ${warning}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Сохранение введенных значений при ошибке -->
                    <c:set var="firstNameValue" value="${not empty param.firstName ? param.firstName : ''}"/>
                    <c:set var="lastNameValue" value="${not empty param.lastName ? param.lastName : ''}"/>
                    <c:set var="emailValue" value="${not empty param.email ? param.email : ''}"/>

                    <form action="register" method="post" id="registerForm" onsubmit="return validateForm()">
                        <div class="row">
                            <!-- Имя -->
                            <div class="col-md-6 mb-3">
                                <label for="firstName" class="form-label">Имя</label>
                                <input type="text"
                                       class="form-control ${not empty firstNameError ? 'is-invalid' : ''}"
                                       id="firstName"
                                       name="firstName"
                                       placeholder="Ваше имя"
                                       value="${firstNameValue}"
                                       required>
                                <c:if test="${not empty firstNameError}">
                                    <div class="invalid-feedback">${firstNameError}</div>
                                </c:if>
                            </div>

                            <!-- Фамилия -->
                            <div class="col-md-6 mb-3">
                                <label for="lastName" class="form-label">Фамилия</label>
                                <input type="text"
                                       class="form-control ${not empty lastNameError ? 'is-invalid' : ''}"
                                       id="lastName"
                                       name="lastName"
                                       placeholder="Ваша фамилия"
                                       value="${lastNameValue}"
                                       required>
                                <c:if test="${not empty lastNameError}">
                                    <div class="invalid-feedback">${lastNameError}</div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email адрес</label>
                            <input type="email"
                                   class="form-control ${not empty emailError ? 'is-invalid' : ''}"
                                   id="email"
                                   name="email"
                                   placeholder="ваш@email.com"
                                   value="${emailValue}"
                                   required>
                            <c:if test="${not empty emailError}">
                                <div class="invalid-feedback">${emailError}</div>
                            </c:if>
                            <c:if test="${not empty duplicateEmail}">
                                <div class="invalid-feedback">Пользователь с таким email уже существует</div>
                            </c:if>
                        </div>

                        <!-- Пароль -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password"
                                   class="form-control ${not empty passwordError ? 'is-invalid' : ''}"
                                   id="password"
                                   name="password"
                                   placeholder="Создайте пароль"
                                   value="${not empty param.password ? param.password : ''}"
                                   required
                                   onkeyup="checkPasswordStrength(this.value)">
                            <div class="password-strength strength-weak" id="passwordStrength"></div>
                            <c:if test="${not empty passwordError}">
                                <div class="invalid-feedback">${passwordError}</div>
                            </c:if>
                            <div class="form-text">
                                Пароль должен содержать минимум 8 символов, включая цифры и буквы
                            </div>
                        </div>

                        <!-- Подтверждение пароля -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Подтверждение пароля</label>
                            <input type="password"
                                   class="form-control ${not empty confirmPasswordError ? 'is-invalid' : ''}"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   placeholder="Повторите пароль"
                                   value="${not empty param.confirmPassword ? param.confirmPassword : ''}"
                                   required
                                   onkeyup="checkPasswordMatch()">
                            <div class="form-text text-danger" id="passwordMatch"></div>
                            <c:if test="${not empty confirmPasswordError}">
                                <div class="invalid-feedback">${confirmPasswordError}</div>
                            </c:if>
                        </div>

                        <!-- Соглашение -->
                        <div class="mb-4">
                            <div class="form-check">
                                <input class="form-check-input ${not empty termsError ? 'is-invalid' : ''}"
                                       type="checkbox"
                                       id="agreeTerms"
                                       name="agreeTerms"
                                ${param.agreeTerms eq 'on' ? 'checked' : ''}
                                       required>
                                <label class="form-check-label" for="agreeTerms">
                                    Я соглашаюсь с
                                    <a href="terms" class="text-decoration-none">условиями использования</a>
                                    и
                                    <a href="privacy" class="text-decoration-none">политикой конфиденциальности</a>
                                </label>
                                <c:if test="${not empty termsError}">
                                    <div class="invalid-feedback">${termsError}</div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Кнопка регистрации -->
                        <button type="submit" class="btn btn-register w-100 mb-3" id="submitBtn">Создать аккаунт</button>

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

        // Возвращаем true/false для валидации
        return strength >= 2;
    }

    // Проверка совпадения паролей
    function checkPasswordMatch() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const matchText = document.getElementById('passwordMatch');

        if (confirmPassword === '') {
            matchText.textContent = '';
            return false;
        } else if (password === confirmPassword) {
            matchText.textContent = '✓ Пароли совпадают';
            matchText.className = 'form-text text-success';
            return true;
        } else {
            matchText.textContent = '✗ Пароли не совпадают';
            matchText.className = 'form-text text-danger';
            return false;
        }
    }

    // Валидация формы на клиенте
    function validateForm() {
        let isValid = true;

        // Сброс предыдущих ошибок
        document.querySelectorAll('.is-invalid').forEach(el => {
            el.classList.remove('is-invalid');
        });

        // Проверка пароля
        const password = document.getElementById('password').value;
        if (!checkPasswordStrength(password)) {
            document.getElementById('password').classList.add('is-invalid');
            isValid = false;
        }

        // Проверка совпадения паролей
        if (!checkPasswordMatch()) {
            document.getElementById('confirmPassword').classList.add('is-invalid');
            isValid = false;
        }

        // Проверка email
        const email = document.getElementById('email').value;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            document.getElementById('email').classList.add('is-invalid');
            isValid = false;
        }

        // Проверка согласия с условиями
        if (!document.getElementById('agreeTerms').checked) {
            document.getElementById('agreeTerms').classList.add('is-invalid');
            isValid = false;
        }

        // Блокировка кнопки при успешной валидации
        if (isValid) {
            const submitBtn = document.getElementById('submitBtn');
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Регистрация...';
        }

        return isValid;
    }

    // Восстановление ошибок после загрузки страницы
    document.addEventListener('DOMContentLoaded', function() {
        // Автоматически проверяем пароли если они уже введены
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password) checkPasswordStrength(password);
        if (confirmPassword) checkPasswordMatch();
    });
</script>
</body>
</html>