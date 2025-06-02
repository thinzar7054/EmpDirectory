package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {
    private static DBConfig instance;
    private final String CONNECTION = "jdbc:mysql://localhost:3306/empdirectory";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private Connection con = null;

    // Private constructor for singleton pattern
    public DBConfig() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver");
        }
    }

    // Singleton instance getter
    public static synchronized DBConfig getInstance() {
        if (instance == null) {
            instance = new DBConfig();
        }
        return instance;
    }

    // Get the database connection
    public Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        }
        return con;
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to execute a query and return result set
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stm = getConnection().createStatement();
        return stm.executeQuery(query);
    }

    // Method to get primary key
    public String getPrimaryKey(String field, String table, String prefix) throws SQLException {
        String query = "SELECT " + field + " FROM " + table + " ORDER BY " + field + " DESC LIMIT 1";
        ResultSet rs = executeQuery(query);
        
        if (rs.next()) {
            String lastId = rs.getString(field);
            int current = Integer.parseInt(lastId.substring(prefix.length())) + 1;
            return String.format("%s%05d", prefix, current);
        }
        return prefix + "00001";
    }

    // Total count method
    public int getTotalCount(String table) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}