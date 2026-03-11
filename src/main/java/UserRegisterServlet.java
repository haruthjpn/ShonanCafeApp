import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	/* ローカル */

	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS =
	 * "postgres";
	 */

	// External Database

	String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381k1c0-a.oregon-postgres.render.com/shonan_db";
	String DB_USER = "admin";
	String DB_PASS = "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// JSPのinputのname属性（name="name", name="email"）に合わせて取得
		String userName = request.getParameter("userName");
		String userMail = request.getParameter("userMail");
		String userPass = request.getParameter("userPass");

		// パスワードも登録する場合（必要に応じて）
		// String password = request.getParameter("password");

		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
				// 新規ユーザーを追加するSQL文
				// roleの初期値を'customer'にする場合は、role列も指定します
				String sql = "INSERT INTO users_db (name, email, password, role) VALUES (?, ?, ?, 'customer')";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, userName);
				st.setString(2, userMail);
				st.setString(3, userPass);

				st.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 登録が完了したら、ユーザー一覧画面へ戻る
		response.sendRedirect("UserServlet");
	}
}