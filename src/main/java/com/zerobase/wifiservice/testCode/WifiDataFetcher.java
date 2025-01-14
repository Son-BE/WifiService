package com.zerobase.wifiservice.testCode;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class WifiDataFetcher {
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/43744d7749626f6e3632644f774a78/json/TbPublicWifiInfo/1/1000";
    private static final String DB_URL = "jdbc:mysql://localhost:3307/wifi_service";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hi092787";

    public static void main(String[] args) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(API_URL).build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray wifiArray = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO wifi_service(x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
                        "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, x_swifi_instl_ty, " +
                        "x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, x_swifi_cnstc_year, " +
                        "x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                for (int i = 0; i < wifiArray.size(); i++) {
                    JsonObject wifi = wifiArray.get(i).getAsJsonObject();

                    preparedStatement.setString(1, wifi.get("X_SWIFI_MGR_NO").getAsString());
                    preparedStatement.setString(2, wifi.get("X_SWIFI_WRDOFC").getAsString());
                    preparedStatement.setString(3, wifi.get("X_SWIFI_MAIN_NM").getAsString());
                    preparedStatement.setString(4, wifi.get("X_SWIFI_ADRES1").getAsString());
                    preparedStatement.setString(5, wifi.get("X_SWIFI_ADRES2").getAsString());
                    preparedStatement.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    preparedStatement.setString(7, wifi.get("X_SWIFI_INSTL_TY").getAsString());
                    preparedStatement.setString(8, wifi.get("X_SWIFI_INSTL_MBY").getAsString());
                    preparedStatement.setString(9, wifi.get("X_SWIFI_SVC_SE").getAsString());
                    preparedStatement.setString(10, wifi.get("X_SWIFI_CMCWR").getAsString());
                    preparedStatement.setString(11, wifi.get("X_SWIFI_CNSTC_YEAR").getAsString());
                    preparedStatement.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").getAsString());
                    preparedStatement.setString(13, wifi.get("X_SWIFI_REMARS3").getAsString());
                    preparedStatement.setString(14, wifi.get("LAT").getAsString());
                    preparedStatement.setString(15, wifi.get("LNT").getAsString());
                    preparedStatement.setString(16, wifi.get("WORK_DTTM").getAsString());
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
                System.out.println("데이터 저장 성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
