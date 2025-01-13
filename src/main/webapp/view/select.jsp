<%--
  Created by IntelliJ IDEA.
  User: son
  Date: 25. 1. 12.
  Time: 오후 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <style>
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
    tbody tr {
      background-color: white;
    }
    tbody tr:hover {
      background-color: #f0f0f0;
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
  </style>
</head>



<body>
<h1>위치 히스토리 목록</h1>

<nav>
  <a href="/" class="btn btn-info" role="button" >홈</a>|
  <a href="/LocationServlet" class="btn btn-warning" role="button">  위치 히스토리 목록</a>|
  <a href="/ApiWifiController" class="btn btn-success" role="button">  Open API 와이파이 정보 로딩하기  </a>

</nav>


<table class="table">

  <thead>
  <tr class ="danger">
    <th>ID</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>조회일자</th>
    <th>비고</th>
  </tr>
  </thead>

  <tbody>

  <c:forEach items="${selectAll}" var="history">
    <tr class ="Info">
      <td><c:out value="${history.id}"/></td>
      <td><c:out value="${history.lat}"/></td>
      <td><c:out value="${history.lnt}"/></td>
      <td><c:out value="${history.date}"/></td>
      <td> <button type ="button" class="button">삭제</button></td>
    </tr>

  </c:forEach>

  </tbody>

</table>


<script>
  $(".button").click(function () {

            var checkBtn = $(this);
            var tr = checkBtn.parent().parent();
            var td = tr.children();

            var deleteIdnumber = td.eq(0).text();

            $.ajax({
              type:"POST",
              url: "http://localhost:8080/removeOneData?deleteIdnumber=" + deleteIdnumber

            }).done(function (){
              location.reload();
            })
          }
  )
</script>




</body>
</html>
