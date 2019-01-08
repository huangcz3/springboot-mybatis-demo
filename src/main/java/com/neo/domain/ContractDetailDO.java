package com.neo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 梯内合同明细数据
 *
 * @author zhangjian
 * @date 2018/8/29 10:40
 * @since V1.0
 */
@Data
public class ContractDetailDO implements Serializable {

	private static final long serialVersionUID = 6572723915725499245L;

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 上传文件id
	 */
	private Long reportId;
	/**
	 * 业绩城市大区
	 */
	private String largeArea;
	/**
	 * 合同编号
	 */
	private String contractNumber;
	/**
	 * 签约日期
	 */
	private String signingDate;
	/**
	 * 刊播城市
	 */
	private String publishCity;
	/**
	 * 业绩计入城市
	 */
	private String achievementCity;
	/**
	 * 年度
	 */
	private String year;
	/**
	 * 月份
	 */
	private String month;
	/**
	 * 月周
	 */
	private String week;
	/**
	 * 上刊日期
	 */
	private String publicationDate;
	/**
	 * 下刊日期
	 */
	private String revokeDate;
	/**
	 * 客户全称
	 */
	private String customerName;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 屏幕
	 */
	private String screen;
	/**
	 * 时长
	 */
	private String lenghtTime;
	/**
	 * 频次
	 */
	private String frequency;
	/**
	 * 购买台数
	 */
	private String buyNums;
	/**
	 * 上刊广告位数
	 */
	private String positionNums;
	/**
	 * 合同金额
	 */
	private String contractAmount;
	/**
	 * 上刊金额
	 */
	private String publicationAmount;
	/**
	 * 签约上刊金额
	 */
	private String signAmount;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 销售人员
	 */
	private String saleName;
	/**
	 * 经营部
	 */
	private String businessDepartment;
	/**
	 * 事业部
	 */
	private String causeDepartment;
	/**
	 * 签约城市
	 */
	private String signCity;
	/**
	 * 付款方式
	 */
	private String paymentMethod;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 二级客户类型
	 */
	private String secondCustomerType;

	/**
	 * 是否双方盖章（新增）
	 */
	private String sealedEachOther;

	/**
	 * 备注
	 */
	private String remarks;
}
