package com.zerobase.wifiservice.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BaseDto {

    @SerializedName("TbPublicWifiInfo")
    private WifiDto tbPublicWifiInfo;
}
