<%--
  Created by IntelliJ IDEA.
  User: son
  Date: 25. 1. 12.
  Time: 오후 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 0;
        height: 100vh;
        justify-content: flex-start;
        padding-top: 0px;
    }
    .message {
        font-size: 20pt;
        font-weight: bold;
        color: black;
        text-align: center;
    }
    .home-link {
        font-size: 12pt;
        margin-top: 10px;
    }
</style>
</head>
<body>
<p class="message">${apiWifiTotalCount}개의 WIFI정보를 정상적으로 저장하였습니다.</p>
<a href="/view/index.jsp" class="btn btn-info home-link" role="button">홈으로 가기</a>
<h1>



</h1>
</body>
</html>
