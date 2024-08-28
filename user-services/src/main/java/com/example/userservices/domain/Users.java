package com.example.userservices.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName users
 */
@Table(name="users")
@Data
public class Users implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 
     */
    private String password;

    private static final long serialVersionUID = 1L;
}