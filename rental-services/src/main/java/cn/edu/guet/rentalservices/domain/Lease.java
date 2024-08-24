package cn.edu.guet.rentalservices.domain;

import java.io.Serializable;

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
    @Column(name = "license_id")
    private String licenseId;

    /**
     * 
     */
    @Column(name = "rental_time")
    private String rentalTime;

    /**
     * 
     */
    @Column(name = "return_time")
    private String returnTime;

    private static final long serialVersionUID = 1L;
}