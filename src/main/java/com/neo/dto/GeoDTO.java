package com.neo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Huangcz
 * @date 2018-10-24 15:21
 * @desc
 */
@Data
public class GeoDTO {

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
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

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
