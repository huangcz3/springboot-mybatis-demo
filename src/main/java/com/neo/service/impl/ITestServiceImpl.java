package com.neo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.neo.mapper.primary.TestMapper;
import com.neo.mapper.primary.UserMapper;
import com.neo.param.ContractDetailParam;
import com.neo.service.ITestService;
import com.neo.util.excel.ExportExcelUtil;
import com.neo.util.excel.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author User
 * @date 2018-08-04 15:06
 * @desc
 */
@Service
public class ITestServiceImpl implements ITestService {

	private static final Logger logger = LoggerFactory.getLogger(ITestServiceImpl.class);


	private static final int ROW_MAX_COUNT = 30000;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private TestMapper testMapper;

	@Override
	public List<Map> getLogs() {
		return userMapper.getLogs();
	}

	@Override
	public List<Map> getArea() {
		return testMapper.getArea();
	}


	@Override
	public void downloadContractDetail(HttpServletResponse response, ContractDetailParam contractDetailParam) {
		long startTime = System.currentTimeMillis();
		String exportFileName = FileUtils.buildExportFileName("合同明细");

		try {
			/** 1.创建临时文件夹  */
			File temDir = new File("tmp/" + UUID.randomUUID().toString().replaceAll("-", ""));
			if (!temDir.exists()) {
				temDir.mkdirs();
			}

			String tmpFileName = temDir + File.separator + exportFileName;
			File tmpFile = new File(tmpFileName);
			FileInputStream sourceFileInputStream = new FileInputStream(new File("D:\\tmp\\合同明细表模板.xlsx"));
			org.apache.commons.io.FileUtils.copyInputStreamToFile(sourceFileInputStream, tmpFile);

			Workbook wb = ExportExcelUtil.fileToWorkbook(tmpFile);

			List<Long> fileUploadIds = new ArrayList<>();
			// 上传报表文件 ID 集合
			if (contractDetailParam.getReportId() == null) {
				fileUploadIds = testMapper.getFileUploadIds(contractDetailParam);
			} else {
				fileUploadIds.add(contractDetailParam.getReportId());
			}

			if (CollectionUtil.isNotEmpty(fileUploadIds)) {
				exportContractDetail(wb, fileUploadIds);
				exportContractDetailTV(wb, fileUploadIds);
				exportContractDetailBox(wb, fileUploadIds);
			}


			String name = temDir + File.separator  + FileUtils.buildExportFileName("合同明细");
			File file = new File(name);
			file.createNewFile();
			OutputStream stream = org.apache.commons.io.FileUtils.openOutputStream(file);

			wb.write(stream);

			stream.flush();
			stream.close();
			File zipFile = null;
			if (file.length() / 1048576 >= 20){
				// 调用压缩方法
				zipFile = ZipUtil.zip(file.getAbsolutePath());
			} else {
				zipFile = file;
			}


			response.reset();

			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(zipFile.getName().getBytes("gb2312"), "ISO8859-1"));
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(zipFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;


			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);

			sourceFileInputStream.close();
			wb.close();

			/** 5.删除临时文件和文件夹 */
			File[] listFiles = temDir.listFiles();
			for (int j = 0; j < listFiles.length; j++) {
				listFiles[j].delete();
			}
			temDir.delete();

		} catch (Exception e) {
			logger.error(exportFileName + "，导出失败！", e);
		}
	}

	@Async("excelAsync")
	public void exportContractDetail(Workbook workbook, List<Long> fileUploadIds) {
		if (CollectionUtil.isNotEmpty(fileUploadIds)) {
			//查询参数
			Map paramMap = new HashMap();
			paramMap.put("reportIds", fileUploadIds);

			//查询记录数
			int allRowNumbers = testMapper.queryContractDetailCount(paramMap);

			//是否大数据量（超过6W）
			if (allRowNumbers > ROW_MAX_COUNT) {
				int tempSize = (allRowNumbers % ROW_MAX_COUNT) == 0 ? allRowNumbers / ROW_MAX_COUNT : allRowNumbers / ROW_MAX_COUNT + 1;
				for (int i = 0; i < tempSize; i++) {
					paramMap.put("startNum", i * ROW_MAX_COUNT);
					paramMap.put("size", ROW_MAX_COUNT);
					// 梯内合同明细表 数据
					List<Map<String, Object>> contractDetailPoDataList = testMapper.contractDetailPoDataList(paramMap);
					// 梯内合同明细表
					ExportExcelUtil.updateExcelWrite(workbook, 0, contractDetailPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);
				}
			} else {
				paramMap.put("startNum", 0);
				paramMap.put("size", allRowNumbers);
				// 梯内合同明细表 数据
				List<Map<String, Object>> contractDetailPoDataList = testMapper.contractDetailPoDataList(paramMap);
				// 梯内合同明细表
				ExportExcelUtil.updateExcelWrite(workbook, 0, contractDetailPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);
			}
		}
	}

	@Async("excelAsync")
	public void exportContractDetailTV(Workbook workbook, List<Long> fileUploadIds) {
		if (CollectionUtil.isNotEmpty(fileUploadIds)) {
			//查询参数
			Map paramMap = new HashMap();
			paramMap.put("reportIds", fileUploadIds);

			//查询记录数
			int allRowNumbers = testMapper.queryContractDetailTVCount(paramMap);

			//是否大数据量（超过6W）
			if (allRowNumbers > ROW_MAX_COUNT) {
				int tempSize = (allRowNumbers % ROW_MAX_COUNT) == 0 ? allRowNumbers / ROW_MAX_COUNT : allRowNumbers / ROW_MAX_COUNT + 1;
				for (int i = 0; i < tempSize; i++) {
					paramMap.put("startNum", i * ROW_MAX_COUNT);
					paramMap.put("size", ROW_MAX_COUNT);
					// 梯视合同明细表 数据
					List<Map<String, Object>> contractDetailTVPoDataList = testMapper.contractDetailTVPoDataList(paramMap);
					// 梯内合同明细表
					ExportExcelUtil.updateExcelWrite(workbook, 1, contractDetailTVPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);
				}
			} else {
				paramMap.put("startNum", 0);
				paramMap.put("size", allRowNumbers);
				// 梯视合同明细表 数据
				List<Map<String, Object>> contractDetailTVPoDataList = testMapper.contractDetailTVPoDataList(paramMap);
				// 梯视合同明细表
				ExportExcelUtil.updateExcelWrite(workbook, 1, contractDetailTVPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);

			}
		}
	}

	@Async("excelAsync")
	public void exportContractDetailBox(Workbook workbook, List<Long> fileUploadIds) {
		if (CollectionUtil.isNotEmpty(fileUploadIds)) {
			//查询参数
			Map paramMap = new HashMap();
			paramMap.put("reportIds", fileUploadIds);

			//查询记录数
			int allRowNumbers = testMapper.queryContractDetailBoxCount(paramMap);
			//是否大数据量（超过6W）
			if (allRowNumbers > ROW_MAX_COUNT) {
				int tempSize = (allRowNumbers % ROW_MAX_COUNT) == 0 ? allRowNumbers / ROW_MAX_COUNT : allRowNumbers / ROW_MAX_COUNT + 1;
				for (int i = 0; i < tempSize; i++) {
					paramMap.put("startNum", i * ROW_MAX_COUNT);
					paramMap.put("size", ROW_MAX_COUNT);
					// 梯内合同明细表 数据
					List<Map<String, Object>> contractDetailBoxPoDataList = testMapper.contractDetailBoxPoDataList(paramMap);
					// 梯内合同明细表
					ExportExcelUtil.updateExcelWrite(workbook, 2, contractDetailBoxPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);
				}
			} else {
				paramMap.put("startNum", 0);
				paramMap.put("size", allRowNumbers);
				// 梯内合同明细表 数据
				List<Map<String, Object>> contractDetailBoxPoDataList = testMapper.contractDetailBoxPoDataList(paramMap);
				// 梯内合同明细表
				ExportExcelUtil.updateExcelWrite(workbook, 2, contractDetailBoxPoDataList, (int) paramMap.get("startNum") + 1, 0, 0, 0, false);
			}
		}
	}


}
