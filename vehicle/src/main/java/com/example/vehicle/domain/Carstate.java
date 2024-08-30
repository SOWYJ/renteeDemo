package com.example.vehicle.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 
 * @TableName carstate
 */
@Table(name="carstate")
@Data
public class Carstate implements Serializable {
    /**
     * 
     */
    @Id
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
    @Column(name = "cars_status")
    private Object carStatus;

    private static final long serialVersionUID = 1L;


}