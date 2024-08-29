package com.example.vehicle.dto;

import lombok.Data;

@Data
public class CardetailQueryDto {
    private String carName;
    private Long current; // 当前页码
    private Long size;    // 每页记录数
}
