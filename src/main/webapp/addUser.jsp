<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー登録</title>
</head>
<body>
	<h2>ユーザー新規登録</h2>
	<form action="UserServlet" method="post">
		名前: <input type="text" name="userName" required><br>
		<br> メール: <input type="email" name="userEmail" required><br>
		<br>
		<div class="input-group">
			<label>パスワード</label> <input type="password" name="userPass" required>
		</div>
		<button type="submit">登録する</button>
	</form>
	<br>
	<a href="UserServlet">一覧に戻る</a>
</body>
</html>