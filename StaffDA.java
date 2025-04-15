package da;

import domain.Staff;
import java.sql.*;

public class StaffDA {
    private String host = "jdbc:derby://localhost:1527/sportdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "STAFF";
    private Connection conn;
    private PreparedStatement stmt;

    public StaffDA() throws SQLException {
        createConnection();
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public void addRecord(Staff staff) throws SQLException {
        String insertStr = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(insertStr);
        stmt.setInt(1, staff.getStaffId());
        stmt.setString(2, staff.getName());
        stmt.setString(3, staff.getUsername());
        stmt.setString(4, staff.getPassword());
        stmt.setString(5, staff.getRole());
        stmt.executeUpdate();
    }

    public Staff getRecord(int staffId) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE STAFFID = ?";
        Staff staff = null;
        stmt = conn.prepareStatement(queryStr);
        stmt.setInt(1, staffId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            staff = new Staff(
                rs.getInt("STAFFID"),
                rs.getString("NAME"),
                rs.getString("USERNAME"),
                rs.getString("PASSWORD"),
                rs.getString("ROLE")
            );
        }
        return staff;
    }

    public void updateRecord(Staff staff) throws SQLException {
        String updateStr = "UPDATE " + tableName + " SET NAME = ?, USERNAME = ?, PASSWORD = ?, ROLE = ? WHERE STAFFID = ?";
        stmt = conn.prepareStatement(updateStr);
        stmt.setString(1, staff.getName());
        stmt.setString(2, staff.getUsername());
        stmt.setString(3, staff.getPassword());
        stmt.setString(4, staff.getRole());
        stmt.setInt(5, staff.getStaffId());
        stmt.executeUpdate();
    }

    public void deleteRecord(int staffId) throws SQLException {
        String deleteStr = "DELETE FROM " + tableName + " WHERE STAFFID = ?";
        stmt = conn.prepareStatement(deleteStr);
        stmt.setInt(1, staffId);
        stmt.executeUpdate();
    }

    public void shutDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}