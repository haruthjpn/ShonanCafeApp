<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>ユーザー編集</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">👤 ユーザー情報の編集</h4>
        </div>
        <div class="card-body">
            <form action="UserUpdateServlet" method="post">
                <input type="hidden" name="id" value="${user.id}">

                <div class="mb-3">
                    <label class="form-label fw-bold">名前</label>
                    <input type="text" name="name" class="form-control" value="${user.name}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">メールアドレス</label>
                    <input type="email" name="email" class="form-control" value="${user.email}" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label fw-bold">パスワード</label>
                    <input type="email" name="password"  value="${user.password}" required>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-success">内容を保存する</button>
                    <a href="UserServlet" class="btn btn-outline-secondary">キャンセル</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>