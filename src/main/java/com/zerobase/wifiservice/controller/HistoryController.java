package com.zerobase.wifiservice.controller;

import com.zerobase.wifiservice.dao.HistoryDao;
import com.zerobase.wifiservice.dao.vo.HistoryVo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet("/HomeServlet")
public class HistoryController extends HttpServlet {

    HistoryDao historyDao = new HistoryDao();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<HistoryVo> list = historyDao.HistorySelectAll();
        System.out.println(list);

        request.setAttribute("selectAll", list);

        RequestDispatcher dispatcher = request.getRequestDispatcher("select.jsp");
        dispatcher.forward(request, response);
    }

}
