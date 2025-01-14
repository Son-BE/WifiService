<%@ page import="com.zerobase.wifiservice.JDBCRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>WiFi 데이터 개수</title>
    <style>
        body {
            text-align: center; /* 중앙 정렬 */
            margin: 0;
            padding: 20px;
            font-family: Arial, sans-serif; /* 글꼴 설정 */
        }
        h2 {
            font-size: 24pt; /* 30pt 크기 설정 */
        }
        a {
            font-size: 12pt; /* 12pt 크기 설정 */
            display: block; /* 링크를 블록 요소로 설정하여 중앙에 위치시킴 */
            margin-top: 20px; /* 상단 여백 설정 */
        }
    </style>
</head>
<body>
<%
    JDBCRepository jdbcRepository = new JDBCRepository(); // JDBCRepository 객체 생성
    int totalCount = 0; // 총 개수 초기화

    try {
        totalCount = jdbcRepository.countTotalWifiRecords(); // 저장된 와이파이 정보 카운트
%>
<h2><%= totalCount %>개의 와이파이 정보를 성공적으로 저장했습니다.</h2>
<%
} catch (Exception e) {
    e.printStackTrace();
%>
<h2>데이터를 가져오는 중 오류가 발생했습니다.</h2>
<%
    }
%>
<a href="index.jsp">홈</a>
</body>
</html>
