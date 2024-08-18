package com.example.userservices.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName roles
 */
@Table(name="roles")
@Data
public class Roles implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "role_id")
    private String roleId;

    /**
     * 
     */
    @Column(name = "role_name")
    private String roleName;

    private static final long serialVersionUID = 1L;
}