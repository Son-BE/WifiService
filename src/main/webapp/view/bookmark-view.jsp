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
<h1>위치 히스토리 목록</h1>
<p>
    <a href="http://localhost:8080/view/index.jsp">홈</a> |
    <a href="http://localhost:8080/view/history_list.jsp">위치 히스토리 목록</a> |
    <a href="http://localhost:8080/view/loadWifi.jsp">Open API 정보 가져오기</a> |
    <a href="http://localhost:8080/view/history_bookmark_view.jspa">즐겨찾기 보기</a> |
    <a href="http://localhost:8080/view/history_bookmark_group.jsp">즐겨찾기 관리</a>
</p>



<form action="view/bookmark_group_add.jsp" method="get">
    <button type="submit">북마크 그룹이름 추가</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>

    </tr>
    </thead>
    <tbody id="wifiData">
    <tr id="initialMessage">
        <td colspan="30" style="text-align: center; background-color: white; color: black;">
            정보가 존재하지 않습니다.
        </td>
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
