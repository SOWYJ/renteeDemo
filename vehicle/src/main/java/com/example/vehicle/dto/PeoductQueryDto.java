package com.example.vehicle.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PeoductQueryDto extends Page {
    private String carName;
}
