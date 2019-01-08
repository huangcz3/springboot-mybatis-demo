package com.neo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Huangcz
 * @date 2018-09-10 16:59
 * @desc
 */
@Data
public class ContractDetailParam implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 页码
     */
    private Integer pn = 1;
    /**
     * 页大小
     */
    private Integer ps = 15;
    /**
     * 版本
     */
    private String version;

    /**
     * 统计类型(0-梯内合同明细 1-梯视合同明细 2-美框合同明细)
     */
    private Integer collectType;

    /**
     * 数据类型（经营日报-DAY_REPORT，合同明细-CONTRACT_DETAIL_REPORT，ACHIEVEMENT_TOTAL_REPORT-业绩统计表）
     */
    private String dataType;

    private Long reportId;

}
