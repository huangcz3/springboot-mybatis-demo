package com.neo.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author 27351
 */
@Data
public class GeoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 高德地图-位置     （经度,纬度）
     */
    private String gdLocation;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态（0：正常，1：异常）
     */
    private Integer status;

    /**
     * 详细地址
     */
    private String detail;


}