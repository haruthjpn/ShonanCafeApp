import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	/* ローカル */

	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS =
	 * "postgres";
	 */

	// External Database
	// ✅ こちらに書き換えてください（Internal Hostnameを使用）

	String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381k1c0-a.render.com:5432/shonan_db";
	String DB_USER = "admin"; // Username欄の値
	String DB_PASS = "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S"; // Password欄の値

	// これを追記すると、URLを叩いた時に画面が表示されるようになります

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ① JSPの <input name="userId"> と一致させる
		String inputName = request.getParameter("userId");
		// ② JSPの <input name="userPass"> と一致させる
		String inputPass = request.getParameter("userPass");

		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {

				// ③ ★最重要： email ではなく password カラムと比較するように修正
				String sql = "SELECT * FROM users_db WHERE name = ? AND password = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, inputName);
				st.setString(2, inputPass);

				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					HttpSession session = request.getSession();

					// ★重要：この1行が抜けていると ProductServlet で追い出されます
					session.setAttribute("isLoggedIn", true);

					session.setAttribute("userId", rs.getInt("id"));
					session.setAttribute("userName", rs.getString("name"));
					session.setAttribute("userRole", rs.getString("role"));

					response.sendRedirect("ProductServlet");

				} else {
					// 失敗：エラーメッセージをセットして戻す
					request.setAttribute("error", "名前またはパスワードが違います");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * protected void doPost(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 * 
 * // 1. JSPの入力欄から値を取得 // IDとして「お名前」を入力する場合 String inputName =
 * request.getParameter("adminId"); // パスワードとして「1234」などを入力する場合 String inputPass
 * = request.getParameter("adminPass");
 * 
 * try { Class.forName("org.postgresql.Driver"); try (Connection conn =
 * DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
 * 
 * // 2. ★修正：名前(name)とパスワード(password)で照合する // emailをパスワード代わりに使っていた以前のロジックから変更します
 * String sql = "SELECT * FROM users_db WHERE name = ? AND password = ?";
 * PreparedStatement st = conn.prepareStatement(sql); st.setString(1,
 * inputName); st.setString(2, inputPass);
 * 
 * ResultSet rs = st.executeQuery();
 * 
 * if (rs.next()) { // 【ログイン成功】 HttpSession session = request.getSession();
 * session.setAttribute("userId", rs.getInt("id"));
 * session.setAttribute("userName", rs.getString("name"));
 * session.setAttribute("userRole", rs.getString("role"));
 * 
 * // 商品一覧サーブレットへ移動 response.sendRedirect("ProductServlet"); } else { //
 * 【ログイン失敗】 request.setAttribute("error", "名前またはパスワードが違います");
 * request.getRequestDispatcher("login.jsp").forward(request, response); } } }
 * catch (Exception e) { e.printStackTrace(); // エラー時はログイン画面へ戻す
 * response.sendRedirect("login.jsp"); } } }
 */