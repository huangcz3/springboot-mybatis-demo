package com.neo.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.domain.GeoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huangcz
 * @date 2018-10-24 15:38
 * @desc
 */
@Component
public class ApiClient {

    @Value("${map.api.baidu.key}")
    private String baiDuKey;

    @Value("${map.api.baidu.geocoder}")
    private String baiDuGeoCoderUrl;

    @Value("${map.api.baidu.geoconv}")
    private String baiDuGeoConvUrl;

    @Value("${map.api.gaode.key}")
    private String gaoDeKey;

    @Value("${map.api.gaode.geocode}")
    private String gaoDeGeoCodeUrl;

    /**
     * 请求高德地图获取经纬度
     *
     * @param geo 地图经纬度实体列表
     */
    public void gdGeoCode(GeoDO geo) {
        // 拼接参数
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key", gaoDeKey);
        parameters.put("address", geo.getDetail());

        // 请求url
        String lines = HttpUtil.sendGet(gaoDeGeoCodeUrl, parameters);
        if (StringUtils.isNotEmpty(lines)) {
            JSONObject jsonObject = JSONObject.parseObject(lines);
            if ("OK".equals(jsonObject.getString("info"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("geocodes");

                if (CollectionUtil.isNotEmpty(jsonArray)){
                    JSONObject obj = jsonArray.getJSONObject(0);
                    String location = obj.getString("location");
                    // 高德地图经度
                    geo.setGdLocation(location);
                }
            }
        }
    }


    public void transferBaiDuStandard(GeoDO geo) {
        // 拼接参数
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("ak", baiDuKey);
        parameters.put("coords", geo.getGdLocation());
        parameters.put("from", "3");
        parameters.put("to", "5");

        // 请求url
        String lines = HttpUtil.sendGet(baiDuGeoConvUrl, parameters);

        if (StringUtils.isNotEmpty(lines)) {
            JSONObject jsonObject = JSONObject.parseObject(lines);
            if (jsonObject.getInteger("status") == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject obj = jsonArray.getJSONObject(0);

                geo.setLongitude(obj.getDouble("x"));
                geo.setLatitude(obj.getDouble("y"));
            }
        }
    }
}



