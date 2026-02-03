<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Pehota
  Date: 03.02.2026
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            ${not empty news ? 'Редактировать новость' : 'Создать новую статью'}
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

        <form action="createNews" method="post">
            <c:if test="${not empty news}">
                <input type="hidden" name="id" value="${news.id}">
            </c:if>

            <div class="row">
                <div class="col-md-8">
                    <div class="mb-3">
                        <label for="title" class="form-label">Заголовок *</label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${news.title}" required maxlength="255"
                               placeholder="Введите заголовок новости">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="mb-3">
                        <label for="status" class="form-label">Статус</label>
                        <select class="form-select" id="status" name="status">
                            <option value="1" ${news.newsStatusId == 1 ? 'selected' : ''}>Черновик</option>
                            <option value="2" ${news.newsStatusId == 2 ? 'selected' : ''}>Опубликовать</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label for="brief" class="form-label">Краткое описание *</label>
                <textarea class="form-control" id="brief" name="brief"
                          rows="3" required maxlength="500"
                          placeholder="Краткое описание (до 500 символов)">${news.brief}</textarea>
                <div class="form-text text-end">
                    <span id="brief-counter">0</span>/500
                </div>
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">Полный текст</label>
                <textarea class="form-control" id="content" name="content"
                          rows="10" placeholder="Полный текст новости">${news.content}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label for="publish_date" class="form-label">Дата публикации</label>
                        <input type="date" class="form-control" id="publish_date" name="publish_date"
                               value="${news.publishDate != null ? news.publishDate.toLocalDate() : ''}">
                        <div class="form-text">Оставьте пустым для публикации сразу</div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <a href="myNews" class="btn btn-secondary">Отмена</a>
                <button type="submit" class="btn btn-save">
                    ${not empty news ? 'Обновить' : 'Сохранить'}
                </button>
            </div>
        </form>
    </div>
</div>

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
        } else {
            briefCounter.style.color = '';
        }
    }

    briefTextarea.addEventListener('input', updateBriefCounter);
    updateBriefCounter();

    // Устанавливаем минимальную дату как сегодня
    const publishDateInput = document.getElementById('publish_date');
    if (!publishDateInput.value) {
        const today = new Date().toISOString().split('T')[0];
        publishDateInput.value = today;
        publishDateInput.min = today;
    }
</script>
</body>
</html>