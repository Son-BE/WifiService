package com.zerobase.wifiservice.controller;

import com.zerobase.wifiservice.JDBCRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/wifiCount")
public class WifiApiServlet extends HttpServlet {
    private final JDBCRepository jdbcRepository = new JDBCRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int totalCount = jdbcRepository.countTotalWifiRecords();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>WiFi 데이터 개수</title></head><body>");
        out.println("<h2 style='text-align: center; font-size: 24pt;'>" + totalCount + "개의 와이파이 정보를 성공적으로 저장했습니다.</h2>");
        out.println("<a href='index.jsp' style='display: block; text-align: center; font-size: 12pt; margin-top: 20px;'>홈</a>");
        out.println("</body></html>");
    }
}
