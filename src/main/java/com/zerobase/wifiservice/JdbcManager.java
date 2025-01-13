package com.zerobase.wifiservice;
import java.sql.*;

public class JdbcManager {

    public void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeStatement(PreparedStatement stmt) {
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://localhost:3307/wifi_service";
        String id = "root";
        String password = "hi092787";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, id, password);
    }
}
