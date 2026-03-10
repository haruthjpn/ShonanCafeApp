

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
import java.sql.SQLException;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String URL = "jdbc:postgresql://localhost:5432/my_practice";
    private static final String USER = "postgres";
    private static final String USER_PASS = "postgres";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 文字化け防止
        request.setCharacterEncoding("UTF-8");
        
        // JSPの name属性と完全に一致させる
        String userName = request.getParameter("userName");
        String userMail = request.getParameter("userMail");
        
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, USER_PASS)) {
                // 既存のテーブル構成 に合わせたINSERT文
                String sql = "INSERT INTO users_db (name, email, role) VALUES (?, ?, 'customer')";
                
                // 変数sqlを渡す（ダブルクォーテーションは不要）
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setString(1, userName);
                    st.setString(2, userMail);
                    
                    st.executeUpdate();
                    response.sendRedirect("login.jsp?msg=success");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup.jsp?msg=error");
        }
    }
}