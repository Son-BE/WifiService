//package com.zerobase.wifiservice.service;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.zerobase.wifiservice.dao.Dao;  // DAO 클래스 import
//import com.zerobase.wifiservice.dao.vo.WifiVo;
//import com.zerobase.wifiservice.dto.RowDto;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class WifiService {
//    private static final OkHttpClient client = new OkHttpClient();
//    private static final String API_KEY = "43744d7749626f6e3632644f774a78";
//    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/";
//
//    private Dao dao; // DAO 객체 추가
//
//    public WifiService() {
//        this.dao = new Dao(); // DAO 인스턴스 생성
//    }
//
//    public List<WifiVo> fetchWifiData(int start, int end) throws IOException {
//        String url = BASE_URL + API_KEY + "/json/TbPublicWifiInfo/" + start + "/" + end;
//
//        Request request = new Request.Builder().url(url).build();
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful() && response.body() != null) {
//                String responseBody = response.body().string();
//                return parseJsonResponse(responseBody);
//            } else {
//                throw new IOException("Request failed: " + response.message());
//            }
//        }
//    }
//
//    private List<WifiVo> parseJsonResponse(String responseBody) {
//        List<WifiVo> wifiList = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
//        JsonObject tbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
//        JsonArray rows = tbPublicWifiInfo.getAsJsonArray("row");
//
//        for (JsonElement element : rows) {
//            JsonObject wifiInfo = element.getAsJsonObject();
//
//            WifiVo wifi = new WifiVo();
//            wifi.setDistance("");
//            wifi.setManageNumber(wifiInfo.get("X_SWIFI_MGR_NO").getAsString());
//            wifi.setBorough(wifiInfo.get("X_SWIFI_WRDOFC").getAsString());
//            wifi.setWifiName(wifiInfo.get("X_SWIFI_MAIN_NM").getAsString());
//            wifi.setRoadAddress(wifiInfo.get("X_SWIFI_ADRES1").getAsString());
//            wifi.setFullAddress(wifiInfo.get("X_SWIFI_ADRES2").getAsString());
//            wifi.setConstructedLocation(wifiInfo.get("X_SWIFI_INSTL_FLOOR").getAsString());
//            wifi.setConstructor(wifiInfo.get("X_SWIFI_INSTL_TY").getAsString());
//            wifi.setServiceType(wifiInfo.get("X_SWIFI_SVC_SE").getAsString());
//            wifi.setNetworkType(wifiInfo.get("X_SWIFI_CMCWR").getAsString());
//            wifi.setConstructedYear(wifiInfo.get("X_SWIFI_CNSTC_YEAR").getAsString());
//            wifi.setIsIndoor((wifiInfo.get("X_SWIFI_INOUT_DOOR").getAsString()));
//            wifi.setWifiLat(wifiInfo.get("LAT").getAsString());
//            wifi.setWifiLnt(wifiInfo.get("LNT").getAsString());
//            wifi.setWorkedDate(wifiInfo.get("WORK_DTTM").getAsString());
//
//            wifiList.add(wifi);
//        }
//
//        return wifiList;
//    }
//
//    public void saveWifiDataToDatabase(List<RowDto> wifiData) {
//        try {
//            dao.saveAllWifiList(wifiData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
