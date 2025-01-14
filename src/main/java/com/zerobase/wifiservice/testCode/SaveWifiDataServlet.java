package com.zerobase.wifiservice.testCode;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zerobase.wifiservice.JDBCRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/saveWifiData")
public class SaveWifiDataServlet extends HttpServlet {
    JDBCRepository jdbcRepository = new JDBCRepository();

    private static final String API_URL = "https://openapi.seoul.go.kr:8088/43744d7749626f6e3632644f774a78/json/TbPublicWifiInfo/1/20/";
    private static final String DB_URL = "jdbc:mysql://localhost:3307/wifi_service";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hi092787";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalInsertedCount = 0; // 저장된 데이터의 총 개수

        try {
            // API 호출
            OkHttpClient client = new OkHttpClient();
            Request apiRequest = new Request.Builder().url(API_URL).build();
            Response apiResponse = client.newCall(apiRequest).execute();
            String jsonResponse = apiResponse.body().string();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray wifiArray = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");

            // 데이터베이스 저장
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO wifi_service(x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
                        "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, x_swifi_instl_ty, " +
                        "x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, x_swifi_cnstc_year, " +
                        "x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                for (int i = 0; i < wifiArray.size(); i++) {
                    JsonObject wifi = wifiArray.get(i).getAsJsonObject();
                    pstmt.setString(1, wifi.get("X_SWIFI_MGR_NO").getAsString());
                    pstmt.setString(2, wifi.get("X_SWIFI_WRDOFC").getAsString());
                    pstmt.setString(3, wifi.get("X_SWIFI_MAIN_NM").getAsString());
                    pstmt.setString(4, wifi.get("X_SWIFI_ADRES1").getAsString());
                    pstmt.setString(5, wifi.get("X_SWIFI_ADRES2").getAsString());
                    pstmt.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    pstmt.setString(7, wifi.get("X_SWIFI_INSTL_TY").getAsString());
                    pstmt.setString(8, wifi.get("X_SWIFI_INSTL_MBY").getAsString());
                    pstmt.setString(9, wifi.get("X_SWIFI_SVC_SE").getAsString());
                    pstmt.setString(10, wifi.get("X_SWIFI_CMCWR").getAsString());
                    pstmt.setString(11, wifi.get("X_SWIFI_CNSTC_YEAR").getAsString());
                    pstmt.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").getAsString());
                    pstmt.setString(13, wifi.get("X_SWIFI_REMARS3").getAsString());
                    pstmt.setDouble(14, wifi.get("LAT").getAsDouble());
                    pstmt.setDouble(15, wifi.get("LNT").getAsDouble());
                    pstmt.setString(16, wifi.get("WORK_DTTM").getAsString());
                    pstmt.addBatch();
                }

                totalInsertedCount = pstmt.executeBatch().length; // 저장된 총 개수
            }

            // JSP로 데이터 전달
            request.setAttribute("apiWifiTotalCount", totalInsertedCount);
            request.getRequestDispatcher("loadWifi.jsp").forward(request, response); // 결과 페이지로 포워드

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("와이파이 데이터 저장 실패");
        }
    }

}
