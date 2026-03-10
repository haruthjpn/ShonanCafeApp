<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン | 管理システム</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f7f8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
        h2 { text-align: center; color: #333; margin-bottom: 1.5rem; }
        .msg { padding: 10px; margin-bottom: 1rem; border-radius: 4px; text-align: center; font-size: 0.9rem; }
        .success { background-color: #e3f2fd; color: #0d47a1; border: 1px solid #bbdefb; }
        .input-group { margin-bottom: 1rem; }
        label { display: block; margin-bottom: 0.5rem; color: #666; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; }
        button:hover { background-color: #0056b3; }
        .footer { text-align: center; margin-top: 1.5rem; border-top: 1px solid #eee; padding-top: 1rem; }
        .signup-link { color: #007bff; text-decoration: none; font-size: 0.9rem; }
    </style>
</head>
<body>
    <div class="card">
        <h2>管理システム：ログイン</h2>
        
        <% String msg = request.getParameter("msg"); if("success".equals(msg)) { %>
            <div class="msg success">新規登録が完了しました！ログインしてください。</div>
        <% } %>

        <form action="LoginServlet" method="post">
    <div class="input-group">
        <label>ユーザー名 (admin や user001)</label>
        <input type="text" name="userId" required>
    </div>
    <div class="input-group">
        <label>パスワード (1234など)</label>
        <input type="password" name="userPass" required>
    </div>
    <button type="submit">ログイン</button>
</form>

        <div class="footer">
            <p style="font-size: 0.8rem; color: #999;">初めての方はこちら</p>
            <a href="signup.jsp" class="signup-link">新規会員登録</a>
        </div>
    </div>
</body>
</html>