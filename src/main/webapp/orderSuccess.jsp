<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>注文完了 | SHONAN CAFE</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow border-0 p-5 text-center">
                    <div class="mb-4">
                        <span style="font-size: 5rem;">🎉</span>
                    </div>
                    <h1 class="text-success mb-3">ご注文ありがとうございました！</h1>
                    <p class="lead text-muted">商品の発送準備を開始いたします。<br>お手元に届くまで楽しみにお待ちください。</p>
                    
                    <hr class="my-4">
                    
                    <div class="d-grid gap-3 d-md-flex justify-content-md-center">
                        <a href="ProductServlet" class="btn btn-primary btn-lg px-5">買い物を続ける</a>
                        <a href="OrderListServlet" class="btn btn-outline-secondary btn-lg px-5">注文履歴を確認</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>