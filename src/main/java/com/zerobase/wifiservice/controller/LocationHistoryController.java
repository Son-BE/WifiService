package com.zerobase.wifiservice.controller;

//import com.zerobase.wifiservice.dao.WIfiDAO;
import com.zerobase.wifiservice.dao.HistoryDao;
import com.zerobase.wifiservice.dao.WifiDAO;
import com.zerobase.wifiservice.dao.vo.WifiVo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/LocationServlet")
public class LocationHistoryController extends HttpServlet {

    private final WifiDAO dao = new WifiDAO();
    private final HistoryDao historyDao;


    public LocationHistoryController() {
        this.historyDao = new HistoryDao();
//        this.dao = new WifiDAO();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Double latDouble = Double.valueOf(request.getParameter("lat-input"));
        Double lonDouble = Double.valueOf(request.getParameter("lon-input"));

        System.out.println(latDouble);
        System.out.println(lonDouble);


        List<WifiVo> searchList = dao.search(latDouble, lonDouble);
        System.out.println(searchList);

        historyDao.saveData(latDouble, lonDouble);

        request.setAttribute("searchList", latDouble);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        dispatcher.forward(request, response);
    }
}
