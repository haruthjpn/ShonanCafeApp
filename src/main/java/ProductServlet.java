import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	/* ローカル */

	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS =
	 * "postgres";
	 */

	// External Database

	String URL = "jdbc:postgresql://admin:7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S@dpg-d6kicsp5pdvs7381k1c0-a.oregon-postgres.render.com/shonan_db";
	String DB_USER = "admin";
	String DB_PASS = "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック（UserServletと同じ仕組み）
		if (request.getSession().getAttribute("isLoggedIn") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		String searchName = request.getParameter("searchName");
		List<Product> productList = new ArrayList<>();
		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
				String sql = "SELECT * FROM products";
				// ★検索キーワードがある場合はWHERE句を追加
				if (searchName != null && !searchName.isEmpty()) {
					sql += " WHERE name LIKE ? ORDER BY id";
				} else {
					sql += " ORDER BY id";
				}

				PreparedStatement st = conn.prepareStatement(sql);
				if (searchName != null && !searchName.isEmpty()) {
					st.setString(1, "%" + searchName + "%");
				}
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					productList.add(
							new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("stock")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("products", productList);
		request.getRequestDispatcher("/productList.jsp").forward(request, response);
	}
}