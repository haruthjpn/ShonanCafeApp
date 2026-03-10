import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // 追加

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	/* ローカル */
	
	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS
	 * ="postgres";
	 */
	 
	// External Database
	
	  String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381...:5432/shonan_db";
	  String DB_USER = "admin"; String DB_PASS =
	  "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";
	 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int productId = Integer.parseInt(request.getParameter("productId"));
        int orderCount = Integer.parseInt(request.getParameter("orderCount"));
        
        // ★ 判定用のフラグを try の外で用意します
        boolean isSuccess = false;
        String orderedItemName = "";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
                
                // 1. 商品名を取得（完了画面で表示するため）
                String nameSql = "SELECT name FROM products WHERE id = ?";
                PreparedStatement st0 = conn.prepareStatement(nameSql);
                st0.setInt(1, productId);
                ResultSet rs = st0.executeQuery();
                if(rs.next()) {
                    orderedItemName = rs.getString("name");
                }

                // 2. 在庫を減らす処理
                String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
                PreparedStatement st1 = conn.prepareStatement(updateStockSql);
                st1.setInt(1, orderCount); // ★1つ目の?に数量をセット
                st1.setInt(2, productId);
                st1.setInt(3, orderCount); // ★3つ目の?（在庫チェック用）に数量をセット
                int updatedRows = st1.executeUpdate();

                if (updatedRows > 0) {
                    // 3. 注文履歴に保存
                	String insertOrderSql = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement st2 = conn.prepareStatement(insertOrderSql);
                    st2.setInt(1, userId);
                    st2.setInt(2, productId);
                    st2.setInt(3, orderCount); // ★数量をセット
                    st2.executeUpdate();
                    
                    // ★ すべて成功したらフラグを true にする
                    isSuccess = true;
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }

        // ★ 最後に判定して飛ばす先を分ける
        if (isSuccess) {
            // 成功：完了画面へ（商品名を渡す）
            request.setAttribute("orderedItemName", orderedItemName);
            request.getRequestDispatcher("orderSuccess.jsp").forward(request, response);
        } else {
            // 失敗：商品一覧へエラーを付けて戻す
            response.sendRedirect("ProductServlet?error=stock");
        }
    }
}