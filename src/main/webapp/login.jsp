<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン画面</title>
</head>
<body>
    <h2>管理システム：ログイン</h2>
    
    <%-- ログイン失敗時のエラーメッセージ表示 --%>
    <p style="color:red;">${error}</p>

    <form action="LoginServlet" method="post">
        ユーザーID: <input type="text" name="adminId" required><br><br>
        パスワード: <input type="password" name="adminPass" required><br><br>
        <button type="submit">ログイン</button>
    </form>
</body>
</html>