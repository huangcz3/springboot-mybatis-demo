package com.neo.service;

import com.neo.param.ContractDetailParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-08-04 15:06
 * @desc
 */
public interface ITestService {
    List<Map> getLogs();

    List<Map> getArea();

    /**
     * 下载合同明细数据
     *
     * @param response
     * @param contractDetailParam
     */
    void downloadContractDetail(HttpServletResponse response, ContractDetailParam contractDetailParam);

}
