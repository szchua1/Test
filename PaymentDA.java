package da;

import domain.Payment;
import java.sql.*;

public class PaymentDA {
    private String host = "jdbc:derby://localhost:1527/sportdb"; 
    private String user = "nbuser"; 
    private String password = "nbuser"; 
    private String tableName = "PAYMENT";
    private Connection conn;
    private PreparedStatement stmt;

    public PaymentDA() throws SQLException {
        createConnection();
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public void addRecord(Payment payment) throws SQLException {
        String insertStr = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(insertStr);
        stmt.setInt(1, payment.getPaymentId());
        stmt.setInt(2, payment.getCustomerId());
        stmt.setDouble(3, payment.getAmount());
        stmt.setString(4, payment.getPaymentDate());
        stmt.setString(5, payment.getName());
        stmt.setString(6, payment.getShippingAddress());
        stmt.setString(7, payment.getPaymentMethod());
        stmt.executeUpdate();
    }

    public Payment getRecord(int paymentId) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE PAYMENTID = ?";
        Payment payment = null;
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, paymentId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            payment = new Payment(
                rs.getInt("PAYMENTID"),
                rs.getInt("CUSTOMERID"),
                rs.getDouble("AMOUNT"),
                rs.getString("PAYMENTDATE"),
                rs.getString("NAME"),
                rs.getString("SHIPPINGADDRESS"),
                rs.getString("PAYMENTMETHOD")
            );
        }
        return payment;
    }

    public void updateRecord(Payment payment) throws SQLException {
        String updateStr = "UPDATE " + tableName + " SET CUSTOMERID = ?, AMOUNT = ?, PAYMENTDATE = ?, NAME = ?, SHIPPINGADDRESS = ?, PAYMENTMETHOD = ? WHERE PAYMENTID = ?";
        stmt = conn.prepareStatement(updateStr);
        stmt.setInt(1, payment.getCustomerId());
        stmt.setDouble(2, payment.getAmount());
        stmt.setString(3, payment.getPaymentDate());
        stmt.setString(4, payment.getName());
        stmt.setString(5, payment.getShippingAddress());
        stmt.setString(6, payment.getPaymentMethod());
        stmt.setInt(7, payment.getPaymentId());
        stmt.executeUpdate();
    }

    public void deleteRecord(int paymentId) throws SQLException {
        String deleteStr = "DELETE FROM " + tableName + " WHERE PAYMENTID = ?";
        stmt = conn.prepareStatement(deleteStr);
        stmt.setInt(1, paymentId);
        stmt.executeUpdate();
    }

    public void shutDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}