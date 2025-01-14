<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
  <title>WiFi 정보 서비스</title>
  <style type="text/css">
    a { text-decoration: none }
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
    }
    nav {
      margin-bottom: 20px;
    }
    nav a {
      margin-right: 15px;
      text-decoration: none;
      color: black;
    }
    nav a:hover {
      text-decoration: underline;
    }
    table {
      background-color: green;
      border-collapse: collapse;
      width: 100%;
    }
    th, td {
      border: 1px solid #cccccc;
      padding: 8px;
      text-align: center;
    }
    th {
      background-color: forestgreen;
      color: white;
    }
    form {
      margin-bottom: 20px;
    }
    form input[type="text"] {
      width: 150px;
      padding: 5px;
      margin-right: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    form button {
      padding: 5px 10px;
      background-color: lightgray;
      color: black;
      border: black;
      border-radius: 2px;
      cursor: pointer;
    }
    form button:hover {
      background-color: antiquewhite;
    }
  </style>

</head>
<body>
<h1>와이파이 정보 구하기</h1>
<nav>
  <a href="index.jsp">홈</a> |
  <a href="history_list.jsp">위치 히스토리 목록</a> |
  <a href="loadWifi.jsp">Open API 와이파이 정보 가져오기</a> |
  <a href="bookmark_view.jsp">즐겨 찾기 보기</a> |
  <a href="bookmark_group.jsp">즐겨찾기 그룹 관리</a>
</nav>

<form action="wifi_list.jsp" method="get">
  <label for="lat">LAT:</label>
  <input type="text" id="lat" name="lat" placeholder="위도 입력">
  <label for="lnt">LNT:</label>
  <input type="text" id="lnt" name="lnt" placeholder="경도 입력">
  <button type="button" onclick="getCurrentLocation()">내 위치 가져오기</button>
  <button type="submit">근처 WIFI 정보 보기</button>
</form>


<table border="1">
  <thead>
  <tr>
    <th>거리(km)</th>
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
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>작업일자</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="wifi" items="${wifiList}">
    <tr>
      <td>${wifi.distance}</td>
      <td>${wifi.X_SWIFI_MGR_NO}</td>
      <td>${wifi.X_SWIFI_WRDOFC}</td>
      <td>${wifi.X_SWIFI_MAIN_NM}</td>
      <td>${wifi.X_SWIFI_ADRES1}</td>
      <td>${wifi.X_SWIFI_ADRES2}</td>
      <td>${wifi.X_SWIFI_INSTL_FLOOR}</td>
      <td>${wifi.X_SWIFI_INSTL_TY}</td>
      <td>${wifi.X_SWIFI_INSTL_MBY}</td>
      <td>${wifi.X_SWIFI_SVC_SE}</td>
      <td>${wifi.X_SWIFI_CMCWR}</td>
      <td>${wifi.X_SWIFI_CNSTC_YEAR}</td>
      <td>${wifi.X_SWIFI_INOUT_DOOR}</td>
      <td>${wifi.LAT}</td>
      <td>${wifi.LNT}</td>
      <td>${wifi.WORK_DTTM}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<script>
  function getCurrentLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
              function (position) {

                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                document.getElementById("lat").value = latitude.toFixed(6);
                document.getElementById("lnt").value = longitude.toFixed(6);
              },
              function (error) {
                switch (error.code) {
                  case error.PERMISSION_DENIED:
                    alert("위치 정보 접근이 거부되었습니다.");
                    break;
                  case error.POSITION_UNAVAILABLE:
                    alert("위치 정보를 사용할 수 없습니다.");
                    break;
                  case error.TIMEOUT:
                    alert("위치 정보를 가져오는 데 시간이 초과되었습니다.");
                    break;
                  default:
                    alert("알 수 없는 오류가 발생했습니다.");
                    break;
                }
              }
      );
    } else {
      alert("브라우저가 위치 정보를 지원하지 않습니다.");
    }
  }

</script>
</body>
</html>
