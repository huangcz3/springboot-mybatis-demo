package com.neo.mapper.secondary;

import com.neo.domain.GeoDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Huangcz
 * @date 2018-10-24 15:26
 * @desc
 */
@Repository
public interface GeoMapper {

    /**
     * 查询需要更新的经纬度
     *
     * @return 集合列表
     */
    List<GeoDO> findByCondition();

    /**
     * 更新单个地址的经纬度
     *
     * @param geoDO 地址经纬度
     */
    void updateLngLat(GeoDO geoDO);


}
