package com.zerobase.wifiservice.controller;

import com.zerobase.wifiservice.dao.HistoryDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/removeOneData")
public class HistoryRemoveOneDateController extends HttpServlet {

    private HistoryDao historyDao;

    public HistoryRemoveOneDateController() {
        this.historyDao = new HistoryDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws IOException{

        int removeId = Integer.parseInt(request.getParameter("deleteIdNumber"));

        historyDao.removeOneData(removeId);
    }

}
