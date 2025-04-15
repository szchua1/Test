package da;

import domain.Product;
import java.sql.*;

public class ProductDA {
    private String host = "jdbc:derby://localhost:1527/sportdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "PRODUCT";
    private Connection conn;
    private PreparedStatement stmt;

    public ProductDA() throws SQLException {
        createConnection();
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public void addRecord(Product product) throws SQLException {
        String insertStr = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(insertStr);
        stmt.setInt(1, product.getProductId());
        stmt.setString(2, product.getName());
        stmt.setString(3, product.getDescription());
        stmt.setDouble(4, product.getPrice());
        stmt.setInt(5, product.getStock());
        if (product.getImage() != null) {
            stmt.setBytes(6, product.getImage());
        } else {
            stmt.setNull(6, Types.BLOB);
        }
        stmt.executeUpdate();
    }

    public Product getRecord(int productId) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE PRODUCTID = ?";
        Product product = null;
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            product = new Product(
                rs.getInt("PRODUCTID"),
                rs.getString("NAME"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("PRICE"),
                rs.getInt("STOCK"),
                rs.getBytes("IMAGE")
            );
        }
        return product;
    }

    public void updateRecord(Product product) throws SQLException {
        String updateStr = "UPDATE " + tableName + " SET NAME = ?, DESCRIPTION = ?, PRICE = ?, STOCK = ?, IMAGE = ? WHERE PRODUCTID = ?";
        stmt = conn.prepareStatement(updateStr);
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getDescription());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getStock());
        if (product.getImage() != null) {
            stmt.setBytes(5, product.getImage());
        } else {
            stmt.setNull(5, Types.BLOB);
        }
        stmt.setInt(6, product.getProductId());
        stmt.executeUpdate();
    }

    public void deleteRecord(int productId) throws SQLException {
        String deleteStr = "DELETE FROM " + tableName + " WHERE PRODUCTID = ?";
        stmt = conn.prepareStatement(deleteStr);
        stmt.setInt(1, productId);
        stmt.executeUpdate();
    }

    public void shutDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}