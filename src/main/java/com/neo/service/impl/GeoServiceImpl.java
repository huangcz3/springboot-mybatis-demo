package com.neo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.neo.domain.GeoDO;
import com.neo.mapper.secondary.GeoMapper;
import com.neo.service.IGeoService;
import com.neo.util.ApiClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Huangcz
 * @date 2018-10-24 15:19
 * @desc
 */
@Service
public class GeoServiceImpl implements IGeoService {

    private static final Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    @Autowired
    private GeoMapper geoMapper;

    @Autowired
    private ApiClient apiClient;

    /**
     * 处理异常数据
     * <p>
     * 1.查询需要处理的楼盘数据
     * 2.请求高德地图获取经纬度
     * 3.请求百度地图转换为百度地图标准的经纬度
     * 4.更新数据
     * </p>
     */
    @Override
    public void dealAbnormalData() {

        logger.info("= = = = = = = 开始处理经纬度异常的楼盘数据 = = = = = = =");

        // 需要处理的楼盘数据
        List<GeoDO> geoDOList = geoMapper.findByCondition();
        if (CollectionUtil.isNotEmpty(geoDOList)) {
            geoDOList.stream().filter(Objects::nonNull).forEach(geoDO -> {
                //logger.info("= = = = = = = 处理id：{}，地址：{}  楼盘数据 = = = = = = =", geoDO.getId(), geoDO.getDetail());
                // 请求高德地图获取经纬度
                apiClient.gdGeoCode(geoDO);
                // 请求百度地图转换为百度地图标准的经纬度
                if (StringUtils.isNotEmpty(geoDO.getGdLocation())){
                    apiClient.transferBaiDuStandard(geoDO);
                }
                // 更新数据
                geoMapper.updateLngLat(geoDO);
                //logger.info("= = = = = = = 处理id：{}，地址：{}  楼盘数据 = = = = = = = success = = = = = = = ", geoDO.getId(), geoDO.getDetail());
            });
        }

    }
}
