package com.example.vehicle.service;

import com.example.vehicle.domain.Cardetail;


import java.util.List;

public interface CarsdetailService {

    List<Cardetail> getCarStateDetails();

    List<Cardetail> obqueryCars(String carName);
}
