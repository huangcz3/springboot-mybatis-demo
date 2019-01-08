package com.neo.mapper.primary;

import com.neo.param.ContractDetailParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-08-04 15:40
 * @desc
 */
public interface TestMapper {
    List<Map> getArea();

	/**
	 * 获取文件上传id列表
	 * @param contractDetailParam
	 * @return
	 */
	List<Long> getFileUploadIds(ContractDetailParam contractDetailParam);

	/**
	 * 可供下载合同明细报表的数据
	 *
	 * @param paramMap 参数map
	 * @return
	 */
	List<Map<String, Object>> contractDetailPoDataList(Map paramMap);

	/**
	 * 根据reportId查询合同明细
	 *
	 * @param reportId
	 * @return
	 */
	List<Map<String, Object>> contractDetailPoDataListByReportId(@Param("reportId") long reportId);


	/**
	 * 可供下载 梯视合同明细报表的数据
	 *
	 * @param paramMap 参数map
	 * @return
	 */
	List<Map<String, Object>> contractDetailTVPoDataList(Map paramMap);

	/**
	 * 根据reportId查询 梯视合同明细
	 *
	 * @param reportId 报表id
	 * @return
	 */
	List<Map<String, Object>> contractDetailTVPoDataListByReportId(@Param("reportId") long reportId);

	/**
	 * 可供下载 梯视合同明细报表的数据
	 *
	 * @param paramMap 参数map
	 * @return
	 */
	List<Map<String, Object>> contractDetailBoxPoDataList(Map paramMap);

	/**
	 * 根据reportId查询 梯视合同明细
	 *
	 * @param reportId 报表id
	 * @return
	 */
	List<Map<String, Object>> contractDetailBoxPoDataListByReportId(@Param("reportId") long reportId);

	int queryContractDetailCount(Map paramMap);

	int queryContractDetailTVCount(Map paramMap);

	int queryContractDetailBoxCount(Map paramMap);

}
