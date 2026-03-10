<%-- ★修正1: ページ設定とタグライブラリの宣言は必ず一番上に書きます --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
<title>商品管理</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
    body {
        /* ナビゲーションバーの高さ分（約70px）だけ上に隙間を作ります */
        padding-top: 70px;
    }
</style>
</head>
<body>
	<%-- ★修正2: navはbodyの中に移動しました --%>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand" href="ProductServlet">SHONAN CAFE
				オンラインショップ</a>
			<div class="navbar-nav me-auto">
				<a class="nav-link active" href="ProductServlet">商品一覧</a>

				<c:if test="${userRole == 'admin'}">
					<a class="nav-link" href="OrderListServlet">売上履歴</a>
					<a class="nav-link" href="UserServlet">ユーザー管理</a>
				</c:if>
			</div>
			<div class="navbar-text">
				<span class="text-light me-2">${userName}
					さん（${userRole}）としてログイン中</span> <a href="LogoutServlet"
					class="btn btn-outline-warning btn-sm">ログアウト</a>
			</div>
		</div>
	</nav>

	<main class="container">
		
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>📦 商品在庫管理</h2>
			<div>
				<%-- 購入者にも見えるボタンを追加 --%>
				<a href="OrderListServlet" class="btn btn-outline-primary me-2">📋
					注文履歴を確認</a>

				<c:if test="${userRole == 'admin'}">
					<a href="UserServlet" class="btn btn-outline-secondary">ユーザー管理へ</a>
				</c:if>
			</div>
		</div>

		<div class="card mb-4 shadow-sm">
			<div class="card-body">
				<form action="ProductServlet" method="get"
					class="row g-3 align-items-center">
					<div class="col-auto">
						<label for="searchName" class="col-form-label fw-bold">商品名検索:</label>
					</div>
					<div class="col-auto">
						<input type="text" id="searchName" name="searchName"
							class="form-control" placeholder="キーワードを入力..."
							value="${param.searchName}">
					</div>
					<div class="col-auto">
						<button type="submit" class="btn btn-primary">🔍 検索</button>
						<a href="ProductServlet" class="btn btn-outline-secondary">クリア</a>
					</div>
				</form>
			</div>
		</div>

		<table class="table table-hover table-bordered mt-3 shadow-sm">
			<thead class="table-dark">
				<tr>
					<th>ID</th>
					<th>商品名</th>
					<th>価格</th>
					<th>在庫数</th>
					<th>ご注文</th> </tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${products}">
					<tr>
						<td>${p.id}</td>
						<td>${p.name}</td>
						<td>${p.price}円</td>
						<td><span
							class="${p.stock < 10 ? 'text-danger fw-bold' : ''}">
								${p.stock} </span></td>
						<td><c:choose>
								<%-- 【管理者(admin)の場合】 数量入力 + 入荷ボタン --%>
								<c:when test="${userRole == 'admin'}">
									<form action="StockServlet" method="post"
										class="d-flex align-items-center">
										<input type="hidden" name="productId" value="${p.id}">
										<input type="number" name="amount"
											class="form-control form-control-sm me-2" value="1" min="1"
											style="width: 70px;">
										<button type="submit"
											class="btn btn-sm btn-outline-primary text-nowrap">
											📦 入荷</button>
									</form>
								</c:when>

								<%-- 【★修正箇所：購入者(customer)の場合】 数量指定 + 注文ボタン --%>
								<c:otherwise>
									<form action="OrderServlet" method="post"
										class="d-flex align-items-center">
										<input type="hidden" name="productId" value="${p.id}">
										
										<%-- 在庫がある場合のみ数量入力を表示 --%>
										<c:if test="${p.stock > 0}">
											<input type="number" name="orderCount"
												class="form-control form-control-sm me-2" 
												value="1" min="1" max="${p.stock}"
												style="width: 70px;">
										</c:if>
										
										<button type="submit" class="btn btn-sm btn-success text-nowrap w-100"
											${p.stock == 0 ? 'disabled' : ''}>
											${p.stock == 0 ? '売り切れ' : '注文する'}
										</button>
									</form>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</main>
</body>
</html>