

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        String userName = (String) session.getAttribute("userName");

        List<Order> orders = new ArrayList<>();
        int totalSales = 0;
        int totalCount = 0;
        Map<String, Integer> productSummary = new HashMap<>();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_practice", "postgres", "postgres")) {
            
            // ★ SQLの切り分け
            String sql;
            if ("admin".equals(userRole)) {
                // 管理者は全注文を取得
                sql = "SELECT o.id, u.name as user_name, p.name as product_name, o.quantity, p.price, o.order_date " +
                      "FROM orders o JOIN users_db u ON o.user_id = u.id JOIN products p ON o.product_id = p.id " +
                      "ORDER BY o.order_date DESC";
            } else {
                // 購入者は「自分の名前」に一致する注文のみ取得
                sql = "SELECT o.id, u.name as user_name, p.name as product_name, o.quantity, p.price, o.order_date " +
                      "FROM orders o JOIN users_db u ON o.user_id = u.id JOIN products p ON o.product_id = p.id " +
                      "WHERE u.name = ? ORDER BY o.order_date DESC";
            }

            PreparedStatement st = conn.prepareStatement(sql);
            if (!"admin".equals(userRole)) {
                st.setString(1, userName);
            }
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // 注文リストの作成
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getInt("price"),
                    rs.getTimestamp("order_date")
                );
                orders.add(order);

                // 売上・集計処理（管理者の分析用）
                totalSales += rs.getInt("price") * rs.getInt("quantity");
                totalCount += rs.getInt("quantity");
                String pName = rs.getString("product_name");
                productSummary.put(pName, productSummary.getOrDefault(pName, 0) + rs.getInt("quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ★ ここで初めてJSPにデータを渡して表示させる
        request.setAttribute("userRole", userRole);
        request.setAttribute("userName", userName);
        request.setAttribute("orders", orders);
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("productSummary", productSummary);
        
        request.getRequestDispatcher("orderList.jsp").forward(request, response);
    }
}