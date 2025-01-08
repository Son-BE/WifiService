package com.zerobase.wifiservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WifiEntity {


    private Integer wifiId;
    private Integer localId;

    private double distance; // 거리
    private String manageNumber; // 관리번호
    private double borough; // 자치구
    private String wifiName; // 와이파이명
    private String loadAddress; // 도로명주소
    private String fullAddress; // 상세 주소
    private String constructedLocation; // 설치위치(층)
    private String constructor; // 설치기관
    private String serviceType; // 서비스 구분
    private String networkType; // 망 종류
    private Integer constructedYear; // 설치년도
    private boolean isIndoor; // 실 내외구분
    private double wifiLat; //x좌표
    private double wifiLnt; //y좌표
    private String workedDate; // 작업일자
}
