
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class StockServlet
 */
@WebServlet("/StockServlet")
public class StockServlet extends HttpServlet {
	/* ローカル */

	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS =
	 * "postgres";
	 */

	// External Database

	String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381k1c0-a.render.com:5432/shonan_db";
	String DB_USER = "admin";
	String DB_PASS = "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 管理者チェック
		HttpSession session = request.getSession();
		if (!"admin".equals(session.getAttribute("userRole"))) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// 2. パラメータの取得
		int productId = Integer.parseInt(request.getParameter("productId"));
		int amount = Integer.parseInt(request.getParameter("amount")); // ★自由に入力された数

		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
				// 指定された数量分だけ在庫を増やす
				String sql = "UPDATE products SET stock = stock + ? WHERE id = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, amount); // ★ここで数量をセット
				st.setInt(2, productId);
				st.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3. 商品一覧へ戻る
		response.sendRedirect("ProductServlet");
	}
}