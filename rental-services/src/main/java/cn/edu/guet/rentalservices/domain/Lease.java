package cn.edu.guet.rentalservices.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName lease
 */
@Table(name="lease")
@Data
public class Lease implements Serializable {
    /**
     * 
     */

    @Column(name="id")
    private int id;

    @Column(name="car_name")
    private String carName;

    @Column(name="car_type")
    private String carType;

    /**
     *
     */
    @Column(name = "brand")
    private String brand;



    @Column(name = "seats")
    private Integer seats;

    @Column(name = "hour_price")
    private BigDecimal hourPrice;

    @Column(name = "license_plate")
    private String licensePlate;
    /**
     * 
     */
    @Column(name = "rental_time")
    private Date rentalTime;

    /**
     * 
     */
    @Column(name = "return_time")
    private Date returnTime;

    @Column(name = "sta")
    private String sta;


    private static final long serialVersionUID = 1L;



}