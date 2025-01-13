package com.zerobase.wifiservice.controller;

import com.zerobase.wifiservice.dao.HistoryDao;
import com.zerobase.wifiservice.dao.vo.HistoryVo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/HistorySelect")
public class HistorySearchController extends HttpServlet {

    private HistoryDao historyDao;

    HistorySearchController() {
        this.historyDao = new HistoryDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<HistoryVo> list = historyDao.HistorySelectAll();
    }
}
