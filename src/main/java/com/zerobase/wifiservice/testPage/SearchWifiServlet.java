package com.zerobase.wifiservice.testPage;

import com.zerobase.wifiservice.dao.WifiDAO;
import com.zerobase.wifiservice.dao.vo.WifiVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchWifi")
public class SearchWifiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        WifiDAO wifiDAO = new WifiDAO();
        List<WifiVo> wifiList = wifiDAO.search(Double.parseDouble(lat), Double.parseDouble(lnt));

        request.setAttribute("list", wifiList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/wifi_list.jsp");
        dispatcher.forward(request, response);
    }
}


