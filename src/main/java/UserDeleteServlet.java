

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class UserDeleteServlet
 */
@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_practice", "postgres", "postgres")) {
            String sql = "DELETE FROM users_db WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(id));
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }

        response.sendRedirect("UserServlet");
    }
}