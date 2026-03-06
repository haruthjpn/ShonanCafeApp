<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>ユーザー管理</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        /* ナビゲーションバーの高さ分（約70px）だけ上に隙間を作ります */
        padding-top: 70px;
    }
</style>
</head>
<body>
   <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container-fluid">
        <a class="navbar-brand d-flex align-items-center" href="ProductServlet">
            <span>SHONAN CAFE オンラインショップ</span>
        </a>
        <div class="navbar-nav me-auto">
          <a class="nav-link" href="ProductServlet">商品一覧</a>
          <a class="nav-link" href="OrderListServlet">売上履歴</a>
          <a class="nav-link active" href="UserServlet">ユーザー管理</a>
        </div>
        <div class="navbar-text">
            <span class="text-light me-2">${userName} さんとしてログイン中</span>
            <a href="LogoutServlet" class="btn btn-outline-warning btn-sm">ログアウト</a>
        </div>
      </div>
    </nav>

    <main class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>👥 登録ユーザー一覧</h2>
            <a href="register.jsp" class="btn btn-primary">新規ユーザー登録画面へ</a>
        </div>

        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <form action="UserServlet" method="get" class="row g-3 align-items-center">
                    <div class="col-auto">
                        <label for="searchName" class="col-form-label">名前検索:</label>
                    </div>
                    <div class="col-auto">
                        <input type="text" id="searchName" name="searchName" class="form-control" placeholder="名前を入力...">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                        <a href="UserServlet" class="btn btn-outline-secondary">クリア</a>
                    </div>
                </form>
            </div>
        </div>

        <div class="table-responsive shadow-sm">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th style="width: 10%">ID</th>
                        <th style="width: 30%">名前</th>
                        <th style="width: 40%">メール</th>
                        <th style="width: 20%">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td>${u.id}</td>
                            <td class="fw-bold">${u.name}</td>
                            <td>${u.email}</td>
                            <td>
                                <a href="UserEditServlet?id=${u.id}" class="btn btn-sm btn-outline-primary">編集</a>
                                <a href="delete.jsp?id=${u.id}" class="btn btn-sm btn-outline-danger">削除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty users}">
            <div class="alert alert-warning text-center">該当するユーザーは見つかりませんでした。</div>
        </c:if>
    </main>
</body>
</html>