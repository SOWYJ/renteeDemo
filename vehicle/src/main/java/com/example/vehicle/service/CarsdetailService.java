package com.example.vehicle.service;

import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.domain.Carstate;


import java.util.List;

public interface CarsdetailService {

    List<Cardetail> getCarStateDetails();

    List<Cardetail> obqueryCars(String carName);

}
