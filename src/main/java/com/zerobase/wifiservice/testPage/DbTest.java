package com.zerobase.wifiservice.testPage;

import com.zerobase.wifiservice.dao.Dao;
import com.zerobase.wifiservice.dto.WifiDto;

public class DbTest {
    public static void main(String[] args) {

        WifiDto wifiDto = new WifiDto();

        WifiApiJsonService wifiApiJsonService = new WifiApiJsonService();
        int pageCount = 0;
        int count = 0;

        try {
            pageCount = wifiApiJsonService.getTotalPageCount();
            count = wifiApiJsonService.getTotalCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("페이지 카운트 : " + pageCount);
        System.out.println("토탈 카운트 : " + count);

        Dao dao = new Dao();
        int start = 0;
        int end = 999;

        for (int i = 0; i < pageCount; i++) {
            try {
                dao.saveAllWifiList
                        (wifiApiJsonService.requestApiResponseToDto(start,end).getWifiRow());

                        start += 1000;
                        end += 1000;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
