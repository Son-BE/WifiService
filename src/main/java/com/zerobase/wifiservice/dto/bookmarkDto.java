package com.zerobase.wifiservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class bookmarkDto {
    private String bookmarkName;
    private int bookmarkOrder;
    private LocalDate registeredAt;
    private LocalDate updatedAt;
}
