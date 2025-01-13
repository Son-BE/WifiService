package com.zerobase.wifiservice.testPage;

import com.google.gson.Gson;
import com.zerobase.wifiservice.dto.BaseDto;
import com.zerobase.wifiservice.dto.WifiDto;

public class WifiJsonParser {
    private Gson gson = new Gson();

    public WifiDto parse(String json) {
        try {
            BaseDto baseDto = gson.fromJson(json, BaseDto.class);

            return baseDto.getTbPublicWifiInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
