package com.example.vehicle.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;


/**
 * 
 * @TableName cars
 */
@TableName("cars")
@Data
public class Cars implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     *
     */
    @Column(name = "car_name")
    private String carName;

    /**
     *
     */
    @Column(name = "car_type")
    private String carType;

    /**
     *
     */
    @Column(name = "brand")
    private String brand;

    /**
     *
     */
    @Column(name = "color")
    private String color;

    /**
     *
     */
    @Column(name = "seats")
    private Integer seats;

    /**
     *
     */
    @Column(name = "license_plate")
    private String licensePlate;

    private static final long serialVersionUID = 1L;


}