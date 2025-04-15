package da;

import domain.Customer;
import java.sql.*;

public class CustomerDA {
    private String host = "jdbc:derby://localhost:1527/sportdb";
    private String user = "nbuser"; 
    private String password = "nbuser"; 
    private String tableName = "CUSTOMER";
    private Connection conn;
    private PreparedStatement stmt;

    public CustomerDA() throws SQLException {
        createConnection();
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public void addRecord(Customer customer) throws SQLException {
        String insertStr = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(insertStr);
        stmt.setInt(1, customer.getCustomerId());
        stmt.setString(2, customer.getName());
        stmt.setString(3, customer.getEmail());
        stmt.setString(4, customer.getPassword());
        stmt.setString(5, customer.getPhone());
        stmt.executeUpdate();
    }

    public Customer getRecord(int customerId) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE CUSTOMERID = ?";
        Customer customer = null;
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            customer = new Customer(
                rs.getInt("CUSTOMERID"),
                rs.getString("NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("PHONE")
            );
        }
        return customer;
    }

    public void updateRecord(Customer customer) throws SQLException {
        String updateStr = "UPDATE " + tableName + " SET NAME = ?, EMAIL = ?, PASSWORD = ?, PHONE = ? WHERE CUSTOMERID = ?";
        stmt = conn.prepareStatement(updateStr);
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getEmail());
        stmt.setString(3, customer.getPassword());
        stmt.setString(4, customer.getPhone());
        stmt.setInt(5, customer.getCustomerId());
        stmt.executeUpdate();
    }

    public void deleteRecord(int customerId) throws SQLException {
        String deleteStr = "DELETE FROM " + tableName + " WHERE CUSTOMERID = ?";
        stmt = conn.prepareStatement(deleteStr);
        stmt.setInt(1, customerId);
        stmt.executeUpdate();
    }

    public void shutDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}