package com.zerobase.wifiservice.dao.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryVo {

    private String id;
    private Double latitude;
    private Double longitude;
    private String retrieveDate;


}

