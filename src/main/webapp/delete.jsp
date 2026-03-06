<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ユーザー削除確認</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="alert alert-danger shadow">
        <h4>⚠️ ユーザー削除の確認</h4>
        <p>ID: ${param.id} のユーザーを削除します。この操作は取り消せません。本当によろしいですか？</p>
        
        <form action="UserDeleteServlet" method="post">
            <input type="hidden" name="id" value="${param.id}">
            <button type="submit" class="btn btn-danger">完全に削除する</button>
            <a href="UserServlet" class="btn btn-secondary">キャンセル</a>
        </form>
    </div>
</body>
</html>