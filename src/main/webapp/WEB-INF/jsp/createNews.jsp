<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Pehota
  Date: 03.02.2026
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty news ? 'Редактировать новость' : 'Создать новость'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; padding: 20px 0; }
        .form-container {
            max-width: 900px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        .form-title {
            color: #007c30;
            border-bottom: 2px solid #007c30;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        .btn-save {
            background-color: #d22730;
            border-color: #d22730;
            color: white;
        }
        .btn-save:hover {
            background-color: #b21e25;
            border-color: #b21e25;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">
            <c:choose>
                <c:when test="${not empty news}">
                    <i class="fas fa-edit me-2"></i>Редактировать новость
                </c:when>
                <c:otherwise>
                    <i class="fas fa-plus me-2"></i>Создать новую статью
                </c:otherwise>
            </c:choose>
        </h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="createNews" method="post" id="newsForm">
            <c:if test="${not empty news}">
                <input type="hidden" name="id" value="${news.id}">
            </c:if>

            <div class="row">
                <div class="col-md-8">
                    <div class="mb-3">
                        <label for="title" class="form-label">
                            Заголовок <span class="text-danger">*</span>
                        </label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${news.title}" required maxlength="255"
                               placeholder="Введите заголовок новости">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="mb-3">
                        <label for="status" class="form-label">Статус</label>
                        <select class="form-select" id="news_status_id" name="news_status_id">
                            <option value="1" ${news.news_status_id == 1 ? 'selected' : ''}>Черновик</option>
                            <option value="2" ${news.news_status_id == 2 ? 'selected' : ''}>Опубликовать</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label for="brief" class="form-label">
                    Краткое описание <span class="text-danger">*</span>
                </label>
                <textarea class="form-control" id="brief" name="brief"
                          rows="3" required maxlength="500"
                          placeholder="Краткое описание (до 500 символов)">${news.brief}</textarea>
                <div class="form-text text-end">
                    <span id="brief-counter">${fn:length(news.brief)}</span>/500
                </div>
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">Полный текст</label>
                <textarea class="form-control" id="content_path" name="content_path"
                          rows="10" placeholder="Полный текст новости">${news.content_path}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label for="publish_date" class="form-label">Дата публикации</label>
                        <input type="date" class="form-control" id="publish_date" name="publish_date"
                               value="${formattedPublishDate}">
                        <div class="form-text">Оставьте пустым для публикации сразу после сохранения</div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Информация</label>
                        <div class="card">
                            <div class="card-body">
                                <c:if test="${not empty news}">
                                    <p class="mb-1"><strong>ID:</strong> ${news.id}</p>
                                    <p class="mb-1"><strong>Создано:</strong> ${news.created_at}</p>
                                    <p class="mb-1"><strong>Обновлено:</strong> ${news.updated_at}</p>
<%--                                    <p class="mb-0"><strong>Автор:</strong> ${news.author_name}</p>--%>
                                </c:if>
                                <c:if test="${empty news}">
                                    <p class="mb-0">Новая запись будет создана от вашего имени</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <a href="myNews" class="btn btn-secondary">
                    <i class="fas fa-times me-1"></i>Отмена
                </a>
                <button type="submit" class="btn btn-save">
                    <c:choose>
                        <c:when test="${not empty news}">
                            <i class="fas fa-save me-1"></i>Сохранить изменения
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-plus me-1"></i>Создать новость
                        </c:otherwise>
                    </c:choose>
                </button>
            </div>
        </form>
    </div>
</div>

<%-- Font Awesome для иконок --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Подсчет символов
    const briefTextarea = document.getElementById('brief');
    const briefCounter = document.getElementById('brief-counter');

    function updateBriefCounter() {
        const length = briefTextarea.value.length;
        briefCounter.textContent = length;
        if (length > 500) {
            briefCounter.style.color = 'red';
            briefTextarea.classList.add('is-invalid');
        } else {
            briefCounter.style.color = '';
            briefTextarea.classList.remove('is-invalid');
        }
    }

    briefTextarea.addEventListener('input', updateBriefCounter);
    updateBriefCounter();

    // Автоматически устанавливаем дату публикации при выборе статуса "Опубликовать"
    const statusSelect = document.getElementById('status');
    const publishDateInput = document.getElementById('publish_date');
    const today = new Date().toISOString().split('T')[0];

    // Устанавливаем минимальную дату как сегодня
    if (publishDateInput) {
        publishDateInput.min = today;

        // Если статус "Опубликовать" и дата не указана, устанавливаем сегодня
        statusSelect.addEventListener('change', function() {
            if (this.value === '2' && !publishDateInput.value) {
                publishDateInput.value = today;
            }
        });
    }

    // Валидация формы
    document.getElementById('newsForm').addEventListener('submit', function(e) {
        const title = document.getElementById('title').value.trim();
        const brief = document.getElementById('brief').value.trim();

        if (!title) {
            e.preventDefault();
            alert('Пожалуйста, введите заголовок');
            return false;
        }

        if (!brief) {
            e.preventDefault();
            alert('Пожалуйста, введите краткое описание');
            return false;
        }

        if (brief.length > 500) {
            e.preventDefault();
            alert('Краткое описание не должно превышать 500 символов');
            return false;
        }

        return true;
    });
</script>
</body>
</html>