package com.example.vehicle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.dto.CardetailQueryDto;


import java.util.List;

public interface CarsdetailService {

    List<Cardetail> getCarStateDetails();

}
