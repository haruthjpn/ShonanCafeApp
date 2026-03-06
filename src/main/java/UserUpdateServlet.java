import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_practice", "postgres", "postgres")) {
            String sql = "UPDATE users_db SET name = ?, email = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, email);
            st.setInt(3, Integer.parseInt(id));
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }

        response.sendRedirect("UserServlet"); // 一覧に戻る
    }
}