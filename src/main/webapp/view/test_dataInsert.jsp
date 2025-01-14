<%--
  Created by IntelliJ IDEA.
  User: son
  Date: 25. 1. 13.
  Time: 오후 7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Public Wi-Fi List</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
    }
    th {
      background-color: #f4f4f4;
      text-align: left;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
  </style>
</head>
<body>
<h1>서울 공공 와이파이 정보</h1>
<table>
  <thead>
  <tr>
    <th>관리번호</th>
    <th>자치구</th>
    <th>와이파이명</th>
    <th>도로명주소</th>
    <th>상세주소</th>
    <th>설치위치(층)</th>
    <th>설치유형</th>
    <th>설치기관</th>
    <th>서비스구분</th>
    <th>망종류</th>
    <th>설치년도</th>
    <th>실내외구분</th>
    <th>비고</th>
    <th>위도</th>
    <th>경도</th>
    <th>작업일시</th>
  </tr>
  </thead>
  <tbody>
  <%
    // 데이터베이스 연결 정보
    String dbUrl = "jdbc:mysql://localhost:3307/wifi_service";
    String dbUser = "root";
    String dbPassword = "hi092787";

    // JDBC 연결
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      String query = "SELECT * FROM wifi_service";
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

      // 테이블 데이터 출력
      while (rs.next()) {
  %>
  <tr>
    <td><%= rs.getString("x_swifi_mgr_no") %></td>
    <td><%= rs.getString("x_swifi_wrdofc") %></td>
    <td><%= rs.getString("x_swifi_main_nm") %></td>
    <td><%= rs.getString("x_swifi_adres1") %></td>
    <td><%= rs.getString("x_swifi_adres2") %></td>
    <td><%= rs.getString("x_swifi_instl_floor") %></td>
    <td><%= rs.getString("x_swifi_instl_ty") %></td>
    <td><%= rs.getString("x_swifi_instl_mby") %></td>
    <td><%= rs.getString("x_swifi_svc_se") %></td>
    <td><%= rs.getString("x_swifi_cmcwr") %></td>
    <td><%= rs.getString("x_swifi_cnstc_year") %></td>
    <td><%= rs.getString("x_swifi_inout_door") %></td>
    <td><%= rs.getString("x_swifi_remars3") %></td>
    <td><%= rs.getDouble("lat") %></td>
    <td><%= rs.getDouble("lnt") %></td>
    <td><%= rs.getString("work_dttm") %></td>
  </tr>
  <%
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null) try { rs.close(); } catch (SQLException e) {}
      if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
      if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
  %>
  </tbody>
</table>
</body>
</html>
