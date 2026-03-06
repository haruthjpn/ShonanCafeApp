<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>売上・注文分析</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
</head>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
<script>
    $(document).ready(function() {
        $('#myTable').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/ja.json"
            },
            "pageLength": 10, // 1ページに表示する件数
            "ordering": true  // 並び替え機能を有効にする
        });
    });
</script>
<body>
    <%-- ナビゲーションバー --%>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container-fluid">
        <a class="navbar-brand" href="ProductServlet">SHONAN CAFE オンラインショップ</a>
        <div class="navbar-nav me-auto">
          <a class="nav-link" href="ProductServlet">商品一覧</a>
          <c:if test="${userRole == 'admin'}">
              <a class="nav-link active" href="OrderListServlet">売上履歴</a>
              <a class="nav-link" href="UserServlet">ユーザー管理</a>
          </c:if>
        </div>
        <span class="navbar-text text-light">${userName} さん（${userRole}）としてログイン中</span>
        <a href="LogoutServlet" class="btn btn-outline-warning btn-sm ms-3">ログアウト</a>
      </div>
    </nav>
<style>
    body { padding-top: 70px; }
</style>
    <div class="container">
    <c:choose>
        <%-- 管理者の場合 --%>
        <c:when test="${userRole == 'admin'}">
            <h2 class="mt-4 mb-4">📊 売上・注文分析（管理者用）</h2>
            
            <%-- ★ 以下の集計カードコードを追加 --%>
            <div class="row mb-4">
                <div class="col-md-3">
                    <div class="card text-white bg-success shadow-sm h-100">
                        <div class="card-body text-center">
                            <h6 class="card-title">総売上金額</h6>
                            <p class="card-text fs-3">
                                ¥ <fmt:formatNumber value="${not empty totalSales ? totalSales : 0}" pattern="#,###" />
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-white bg-info shadow-sm h-100">
                        <div class="card-body text-center">
                            <h6 class="card-title">累計販売数</h6>
                            <p class="card-text fs-3">${not empty totalCount ? totalCount : 0} 件</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card shadow-sm h-100">
                        <div class="card-header bg-light fw-bold">📦 商品別の販売状況</div>
                        <div class="card-body p-0">
                            <table class="table table-sm m-0">
                                <thead class="table-light"><tr><th>商品名</th><th>累計販売数</th></tr></thead>
                                <tbody>
                                    <c:forEach var="entry" items="${productSummary}">
                                        <tr><td>${entry.key}</td><td>${entry.value} 個</td></tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <h4 class="mb-3 mt-5">📝 全ユーザーの注文明細</h4>
        </c:when>
    </c:choose>

    <%-- 注文明細テーブル（共通：Servlet側で既にデータは絞り込まれています） --%>
    <div class="table-responsive shadow-sm mt-3">
        <table id="myTable" class="table table-hover border">
            <thead class="table-dark">
                <tr>
                    <th>注文ID</th>
                    <c:if test="${userRole == 'admin'}"><th>顧客名</th></c:if>
                    <th>商品名</th>
                    <th>数量</th>
                    <th>価格</th>
                    <th>注文日時</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>
                        <c:if test="${userRole == 'admin'}"><td>${order.userName}</td></c:if>
                        <td>${order.productName}</td>
                        <td>${order.quantity}</td>
                        <td>¥ <fmt:formatNumber value="${order.price}" pattern="#,###" /></td>
                        <td>${order.orderDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
    </div>
</body>
</html>