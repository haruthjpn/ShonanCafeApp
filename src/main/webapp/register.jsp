<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>新規ユーザー登録</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-5">
	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card shadow">
				<div class="card-header bg-success text-white">
					<h4 class="mb-0">🆕 新規ユーザー登録</h4>
				</div>
				<div class="card-body">
					<form action="UserRegisterServlet" method="post">
						<div class="mb-3">
							<label class="form-label fw-bold">お名前</label> <input type="text"
								name="userName" class="form-control" placeholder="例：湘南 太郎"
								required>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">メールアドレス</label> <input
								type="email" name="userMail" class="form-control"
								placeholder="example@shonan.com" required>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">パスワード</label> <input
								type="password" name="userPass" class="form-control" required>
						</div>

						<div class="d-grid gap-2 mt-4">
							<button type="submit" class="btn btn-success">登録する</button>
							<a href="UserServlet" class="btn btn-outline-secondary">一覧に戻る</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>