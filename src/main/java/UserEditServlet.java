import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	/* ローカル */
	
	/*
	 * private final String URL = "jdbc:postgresql://localhost:5432/my_practice";
	 * private final String DB_USER = "postgres"; private final String DB_PASS =
	 * "postgres";
	 */
	 
	// External Database
	
	  String URL = "jdbc:postgresql://dpg-d6kicsp5pdvs7381...:5432/shonan_db";
	  String DB_USER = "admin"; 
	  String DB_PASS ="7yOIcqsNhYl7Bym8hoJ5LiwKSyWAcS7S";
	 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
                String sql = "SELECT * FROM users_db WHERE id = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, Integer.parseInt(id));
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    // JSPで ${user.name} のように使えるようにデータをセット
                    Map<String, String> user = new HashMap<>();
                    user.put("id", String.valueOf(rs.getInt("id")));
                    user.put("name", rs.getString("name"));
                    user.put("email", rs.getString("email"));
                    request.setAttribute("user", user);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        // データを準備したら編集画面を表示する
        request.getRequestDispatcher("/editUser.jsp").forward(request, response);
    }
}