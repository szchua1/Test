package da;

import domain.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDA {
    private String host = "jdbc:derby://localhost:1527/sportdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "CARTITEM";
    private Connection conn;
    private PreparedStatement stmt;

    public CartItemDA() throws SQLException {
        createConnection();
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public void addRecord(CartItem cartItem) throws SQLException {
        String insertStr = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?)";
        stmt = conn.prepareStatement(insertStr);
        stmt.setInt(1, cartItem.getCartItemId());
        stmt.setInt(2, cartItem.getCustomerId());
        stmt.setInt(3, cartItem.getProductId());
        stmt.setInt(4, cartItem.getQuantity());
        stmt.executeUpdate();
    }

    public CartItem getRecord(int cartItemId) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE CARTITEMID = ?";
        CartItem cartItem = null;
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, cartItemId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            cartItem = new CartItem(
                rs.getInt("CARTITEMID"),
                rs.getInt("CUSTOMERID"),
                rs.getInt("PRODUCTID"),
                rs.getInt("QUANTITY")
            );
        }
        return cartItem;
    }

    public List<CartItem> getCartItemsByCustomerId(int customerId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String queryStr = "SELECT * FROM " + tableName + " WHERE CUSTOMERID = ?";
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CartItem cartItem = new CartItem(
                rs.getInt("CARTITEMID"),
                rs.getInt("CUSTOMERID"),
                rs.getInt("PRODUCTID"),
                rs.getInt("QUANTITY")
            );
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    public void updateRecord(CartItem cartItem) throws SQLException {
        String updateStr = "UPDATE " + tableName + " SET CUSTOMERID = ?, PRODUCTID = ?, QUANTITY = ? WHERE CARTITEMID = ?";
        stmt = conn.prepareStatement(updateStr);
        stmt.setInt(1, cartItem.getCustomerId());
        stmt.setInt(2, cartItem.getProductId());
        stmt.setInt(3, cartItem.getQuantity());
        stmt.setInt(4, cartItem.getCartItemId());
        stmt.executeUpdate();
    }

    public void deleteRecord(int cartItemId) throws SQLException {
        String deleteStr = "DELETE FROM " + tableName + " WHERE CARTITEMID = ?";
        stmt = conn.prepareStatement(deleteStr);
        stmt.setInt(1, cartItemId);
        stmt.executeUpdate();
    }

    public void shutDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}