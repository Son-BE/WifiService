package com.zerobase.wifiservice;

import lombok.var;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class JDBCRepository {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/wifi_service";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hi092787";



    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


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

    public int countTotalWifiRecords() {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM wifi_service";
        try (Connection conn = createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버 오류: " + e.getMessage());
            e.printStackTrace();
        }
        return totalCount;
    }


}
