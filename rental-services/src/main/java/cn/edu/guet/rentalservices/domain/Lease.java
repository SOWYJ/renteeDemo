package cn.edu.guet.rentalservices.domain;

import java.io.Serializable;
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

    @Column(name="renter")
    private String renter;


    @Column(name = "license_id")
    private String licenseId;

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

    private static final long serialVersionUID = 1L;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Date getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Date rentalTime) {
        this.rentalTime = rentalTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }
}