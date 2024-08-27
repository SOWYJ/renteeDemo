package com.example.activityservices.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName coupons
 */
@Table(name="coupons")
@Data
public class Coupons implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "coupon_id")
    private String couponId;

    /**
     * 
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * 
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * 
     */
    @Column(name = "is_kill")
    private String isKill;

    /**
     * 
     */
    @Column(name = "is_special")
    private String isSpecial;

    /**
     * 
     */
    @Column(name = "coupon_img")
    private String couponImg;

    /**
     * 
     */
    @Column(name = "coupon_content")
    private String couponContent;

    /**
     * 
     */
    @Column(name = "first_price")
    private Double firstPrice;

    /**
     * 
     */
    @Column(name = "current_price")
    private Double currentPrice;

    /**
     * 
     */
    @Column(name = "total_num")
    private Integer totalNum;

    /**
     * 
     */
    private Integer sales;

    private static final long serialVersionUID = 1L;
}