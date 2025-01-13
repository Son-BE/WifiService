package com.zerobase.wifiservice.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class WifiDto {

    @SerializedName("list_total_count")
    private int listTotalCount;

    @SerializedName("RESULT")
    private ResultInfoDto result;

    @SerializedName("ROW")
    private List<RowDto> wifiRow;

}
