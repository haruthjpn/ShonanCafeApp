import java.sql.Timestamp;

public class Order {
    private int id;
    private String userName;
    private String productName;
    private int quantity;
    private int price;
    private Timestamp orderDate;

    // コンストラクタ
    public Order(int id, String userName, String productName, int quantity, int price, Timestamp orderDate) {
        this.id = id;
        this.userName = userName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
    }

    // Getter (JSPの ${order.userName} などで使用します)
    public int getId() { return id; }
    public String getUserName() { return userName; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }
    public Timestamp getOrderDate() { return orderDate; }
}