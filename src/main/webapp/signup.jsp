<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>新規登録 | 管理システム</title>
<style>
/* login.jspと同じ基本スタイル */
body {
	font-family: sans-serif;
	background-color: #f4f7f8;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.card {
	background: white;
	padding: 2rem;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	width: 100%;
	max-width: 400px;
}

h2 {
	text-align: center;
	color: #333;
	margin-bottom: 1.5rem;
}

.input-group {
	margin-bottom: 1rem;
}

label {
	display: block;
	margin-bottom: 0.5rem;
	color: #666;
}

input[type="text"], input[type="email"], input[type="password"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

button {
	width: 100%;
	padding: 12px;
	background-color: #28a745;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 1rem;
}

button:hover {
	background-color: #218838;
}

.footer {
	text-align: center;
	margin-top: 1rem;
}

.back-link {
	color: #666;
	text-decoration: none;
	font-size: 0.9rem;
}
</style>
</head>
<body>
	<div class="card">
		<h2>新規会員登録</h2>
		<form action="SignupServlet" method="post">
			<div class="input-group">
				<label>お名前 (ログインID用)</label> <input type="text" name="userName"
					required placeholder="例：湘南 太郎">
			</div>
			<div class="input-group">
				<label>メールアドレス</label> <input type="email" name="userMail" required
					placeholder="example@shonan.co.jp">
			</div>
			<div class="input-group">
				<label>パスワード</label> <input type="password" name="userPass" required>
			</div>
			<button type="submit">登録する</button>
		</form>
		<div class="footer">
			<a href="login.jsp" class="back-link">← ログイン画面へ戻る</a>
		</div>
	</div>
</body>
</html>