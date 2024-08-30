package com.example.vehicle.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName carstate
 */
@Table(name="Carstate")
@Data
public class Carstate implements Serializable {
    /**
     * 
     */
    @Id
   @Column(name = "id")
    private Integer id;

    /**
     * 
     */
    @Column(name = "hour_price")
    private BigDecimal hourPrice;

    /**
     * 
     */
    @Column(name = "drop_location")
    private String dropLocation;

    /**
     * 
     */
    @Column(name = "drop_date")
    private LocalDateTime dropDate;

    /**
     * 
     */
    @Column(name = "car_status")
    private Object carstatus;

    private static final long serialVersionUID = 1L;

}