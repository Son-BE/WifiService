package com.zerobase.wifiservice;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class OpenApiTest {

    // 관리번호, 자치구, 와이파이명, 도로명주소, 상세주소, 설치유형, 설치기관
    OkHttpClient client = new OkHttpClient();

    String apiKey = "43744d7749626f6e3632644f774a78";
    String url = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo/1/20";

    public void fetchWifiData() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    parseJsonResponse(responseBody);
                } else {
                    System.out.println("요청 실패: " + response.message());
                }
            }
        });
    }

    private void parseJsonResponse(String responseBody) {
        Gson gson = new Gson();

        // JSON 파싱
        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
        JsonObject tbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");

        // 총 데이터 수
        String listTotalCount = tbPublicWifiInfo.get("list_total_count").getAsString();
        System.out.println("총 데이터 수: " + listTotalCount);

        // 와이파이 정보
        JsonArray rows = tbPublicWifiInfo.getAsJsonArray("row");
        for (int i = 0; i < rows.size(); i++) {
            JsonObject wifiInfo = rows.get(i).getAsJsonObject();

            System.out.println("와이파이 이름: " + wifiInfo.get("X_SWIFI_MAIN_NM").getAsString());
            System.out.println("관리번호: " + wifiInfo.get("X_SWIFI_MGR_NO").getAsString());
            System.out.println("자치구: " + wifiInfo.get("X_SWIFI_WRDOFC").getAsString());
            System.out.println("도로명주소: " + wifiInfo.get("X_SWIFI_ADRES1").getAsString());
            System.out.println("상세주소: " + wifiInfo.get("X_SWIFI_ADRES2").getAsString());
            System.out.println("설치위치(층): " + wifiInfo.get("X_SWIFI_INSTL_FLOOR").getAsString());
            System.out.println("설치유형: " + wifiInfo.get("X_SWIFI_INSTL_TY").getAsString());
            System.out.println();

        }
    }

    public static void main(String[] args) {
        OpenApiTest openApiTest = new OpenApiTest();
        openApiTest.fetchWifiData();
    }
}
