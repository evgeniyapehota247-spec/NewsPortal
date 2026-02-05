<%--
  Created by IntelliJ IDEA.
  User: Evgeniya.Pehota
  Date: 04.02.2026
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Страница не найдена</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5 text-center">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1 class="display-1 text-danger">404</h1>
            <h2 class="mb-4">Страница не найдена</h2>
            <p class="lead mb-4">Запрошенная страница не существует или была перемещена.</p>
            <div class="d-grid gap-2 d-md-block">
                <a href="/" class="btn btn-primary btn-lg me-2">На главную</a>
                <a href="javascript:history.back()" class="btn btn-outline-secondary btn-lg">Назад</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>