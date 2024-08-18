package com.example.userservices.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @TableName menu
 */
@Table(name="menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "menu_id")
    private String menuId;

    /**
     * 
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 
     */
    @Column(name = "menu_url")
    private String menuUrl;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    @Column(name = "is_parent")
    private String isParent;

    /**
     * 
     */
    @Column(name = "parent_menu_id")
    private String parentMenuId;

    /**
     * 
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}