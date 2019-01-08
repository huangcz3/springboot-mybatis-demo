package com.neo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Huangcz
 * @date 2018-10-24 16:41
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoServiceTest {

    @Autowired
    private IGeoService geoService;

    @Test
    public void test(){

        long startTime = System.currentTimeMillis();

        geoService.dealAbnormalData();

        long endTime = System.currentTimeMillis();

        System.out.println("耗时：" + (endTime - startTime));
    }

}
