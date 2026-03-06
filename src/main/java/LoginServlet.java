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
	 * private final String DB_USER = "postgres"; 
	 * private final String DB_PASS =* "postgres";
	 */
	// External Database
	String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381...:5432/shonan_db"; 
	String DB_USER = "admin";
	String DB_PASS = "7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // JSPのinputのname属性に合わせて取得（name="adminId" と仮定）
        String inputName = request.getParameter("adminId"); 
        String inputEmail = request.getParameter("adminPass"); // 今回はパスワード代わりにEmailを使う例

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
                // 名前とEmailが一致するユーザーを探す
                String sql = "SELECT * FROM users_db WHERE name = ? AND email = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, inputName);
                st.setString(2, inputEmail);
                
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("userId", rs.getInt("id"));
                    session.setAttribute("userName", rs.getString("name"));
                    
                    // ★権限（role）をセッションに保存
                    session.setAttribute("userRole", rs.getString("role")); 
                    
                    response.sendRedirect("ProductServlet");
                } else {
                    // 【ログイン失敗】
                    request.setAttribute("error", "名前またはメールアドレスが違います");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}