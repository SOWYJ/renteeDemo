package com.example.userservices.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cars implements Serializable {
    private Integer id;
    private String carName;
    private String carType;
    private String brand;
    private String color;
    private Integer seats;
    private String licensePlate;

    private static final long serialVersionUID = 1L;

}