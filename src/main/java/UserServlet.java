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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 門番（ログインチェック）
		HttpSession session = request.getSession();
		if (session.getAttribute("isLoggedIn") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// 2. 編集画面の判定（idがある場合）
		String idStr = request.getParameter("id");
		if (idStr != null) {
			int id = Integer.parseInt(idStr);
			try {
				Class.forName("org.postgresql.Driver");
				try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
					String sql = "SELECT * FROM users_db WHERE id = ?";
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, id);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						User u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
						request.setAttribute("user", u);
						request.getRequestDispatcher("/editUser.jsp").forward(request, response);
						return; // 編集画面へ行くのでここで終了
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 3. 検索または一覧表示の処理（idがない、または編集対象が見つからない場合ここに来る）
		String searchName = request.getParameter("searchName");
		List<User> userList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
				String sql;
				PreparedStatement st;

				if (searchName != null && !searchName.isEmpty()) {
					// 検索ワードがある場合
					sql = "SELECT * FROM users_db WHERE name LIKE ? ORDER BY id";
					st = conn.prepareStatement(sql);
					st.setString(1, "%" + searchName + "%");
				} else {
					// 検索ワードがない場合（通常の一覧）
					sql = "SELECT * FROM users_db ORDER BY id";
					st = conn.prepareStatement(sql);
				}

				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					userList.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4. 結果をJSPに渡して表示
		request.setAttribute("users", userList);
		request.getRequestDispatcher("/userList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// --- 1. 削除処理 ---
		if ("delete".equals(action)) {
			String idStr = request.getParameter("id");
			if (idStr != null) {
				int id = Integer.parseInt(idStr);
				try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
					String sql = "DELETE FROM users_db WHERE id = ?";
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, id);
					st.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			response.sendRedirect("UserServlet");
			return;
		}

		// --- 2. 値の取得とバリデーション ---
		String name = request.getParameter("userName");
		String email = request.getParameter("userEmail");

		if (name == null || name.trim().isEmpty()) {
			response.sendRedirect("UserServlet");
			return;
		}

		// --- 3. 登録 or 更新の実行（ここを具体的に記述します） ---
		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
				if ("update".equals(action)) {
					// 更新処理
					int id = Integer.parseInt(request.getParameter("id"));
					String sql = "UPDATE users_db SET name = ?, email = ? WHERE id = ?";
					PreparedStatement st = conn.prepareStatement(sql);
					st.setString(1, name);
					st.setString(2, email);
					st.setInt(3, id);
					st.executeUpdate();
				} else {
					// 新規登録処理
					String sql = "INSERT INTO users_db (name, email) VALUES (?, ?)";
					PreparedStatement st = conn.prepareStatement(sql);
					st.setString(1, name);
					st.setString(2, email);
					st.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 最後に一覧画面へ戻る
		response.sendRedirect("UserServlet");
	}
}