package com.zerobase.wifiservice.testPage;

import com.zerobase.wifiservice.dto.WifiDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URL;

public class WifiApiJsonService {
    WifiJsonParser wifiJsonParser = new WifiJsonParser();

    public WifiDto requestApiResponseToDto(int start, int end) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = "http://openapi.seoul.go.kr:8088/43744d7749626f6e3632644f774a78/json/TbPublicWifiInfo/"
                + start + "/" + end;

        URL urlRequest = new URL(url);
        Request request = new Request.Builder()
                .url(urlRequest)
                .build();

        Response response = client.newCall(request).execute();

        String jsonData = response.body().string();

        return wifiJsonParser.parse(jsonData);
    }

    public int getTotalPageCount() throws Exception {
        WifiDto dto = requestApiResponseToDto(0, 1);
        int totalCount = dto.getListTotalCount();

        int count = (totalCount / 1000);

        if(totalCount % 1000 > 0) {
            count += 1;
        }

        return count;
    }

    public int getTotalCount() throws Exception {
        WifiDto dto = requestApiResponseToDto(0, 1);
        int totalCount = dto.getListTotalCount();
        return totalCount;
    }

}
