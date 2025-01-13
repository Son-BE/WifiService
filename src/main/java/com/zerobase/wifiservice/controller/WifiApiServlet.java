package com.zerobase.wifiservice.controller;

import com.zerobase.wifiservice.dao.Dao;
import com.zerobase.wifiservice.dto.RowDto;
import com.zerobase.wifiservice.dto.WifiDto;
import com.zerobase.wifiservice.testPage.WifiApiJsonService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ApiWifiController")
public class WifiApiServlet extends HttpServlet {

    WifiApiJsonService wifiApiJsonService = new WifiApiJsonService();
    Dao dao = new Dao();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        try {
            int count = wifiApiJsonService.getTotalPageCount();

            int start = 0;
            int end = 999;

            dao.removeAllData();

            for (int i = 0; i < count; i++) {
                System.out.println(start + " , " + end);
                WifiDto wifiDto = wifiApiJsonService.requestApiResponseToDto(start,end);

                List<RowDto> rowDtoList = wifiDto.getWifiRow();
                dao.saveAllWifiList(rowDtoList);

                start += 1000;
                end += 1000;
            }

            request.setAttribute("apiWifiTotalCount", wifiApiJsonService.getTotalCount());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/loadWifi.jsp");
            dispatcher.forward(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
