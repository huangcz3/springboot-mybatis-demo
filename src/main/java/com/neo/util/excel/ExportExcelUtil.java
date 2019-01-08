package com.neo.util.excel;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author User
 * @date 2018-08-07 22:46
 * @desc
 */
public class ExportExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

	/**
	 * 下载最大值
	 */
	private static final long DOWNLOAD_MAX_SIZE = 10000;

	public static CellStyle format(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		//下边框
		cellStyle.setBorderBottom(BorderStyle.THIN);
		//左边框
		cellStyle.setBorderLeft(BorderStyle.THIN);
		//上边框
		cellStyle.setBorderTop(BorderStyle.THIN);
		//右边框
		cellStyle.setBorderRight(BorderStyle.THIN);

		//居中
		//水平居中
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		//垂直居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		//设置字体
		//HSSFFont font = wb.createFont();
		//font.setFontName("华文行楷");//设置字体名称
		//font.setFontHeightInPoints((short)28);//设置字号
		//font.setItalic(false);//设置是否为斜体
		//font.setBold(true);//设置是否加粗
		//font.setColor(IndexedColors.RED.index);//设置字体颜色
		//cellStyle.setFont(font);
		return cellStyle;
	}

	/**
	 * 将数据写入指定模板Excel文件中
	 *
	 * @param wb         工作空间
	 * @param sheetIndex sheet号
	 * @param dataList   需要写入的数据list
	 * @param xStart     x开始坐标
	 * @param xSize      表格x结束位置
	 * @param yStart     y开始坐标
	 * @param ySize      表格y结束位置
	 * @param flag       是否合并表格
	 */
	public static void updateExcelWrite(Workbook wb, int sheetIndex, List<Map<String, Object>> dataList, int xStart, int xSize, int yStart, int ySize, boolean flag) {

		/**
		 * 循环写入数据
		 */
		if (CollectionUtil.isNotEmpty(dataList)) {
			List<String[]> data = getDataList(dataList);
			xSize = data.size() + xStart - 1;
			ySize = data.get(0).length + yStart - 1;
			Sheet sheet = wb.getSheetAt(sheetIndex);
			CellStyle cellStyle = format(wb);
			int tmp = 0;
			for (int x = xStart; x <= xSize; x++) {
				int tmp2 = 0;
				//创建一行
				Row row = sheet.createRow(x);
				//一行的所有值
				String[] strings = data.get(tmp);
				for (int y = yStart; y <= ySize; y++) {
					//值从第一个数据获取
					String value = strings[tmp2];
					Cell cell = row.createCell(y);
					cell.setCellValue(value);
					cell.setCellStyle(cellStyle);
					tmp2++;
				}
				tmp++;
			}

		}
	}

	/**
	 * 将数据写入指定模板Excel文件中
	 *
	 * @param file     excel文件
	 * @param dataList 需要写入的数据list
	 * @param xStart   x开始坐标
	 * @param xSize    表格x结束位置
	 * @param yStart   y开始坐标
	 * @param ySize    表格y结束位置
	 * @param flag     是否合并表格
	 *                 //* @param colspan  合并列
	 *                 //* @param a        需要合并的表格行号
	 *                 //* @param b        需要合并的表格数据行号   , int colspan, int[] a, int[] b
	 */
	public static Workbook updateExcelWrite(File file, List<Map<String, Object>> dataList, int xStart, int xSize, int yStart, int ySize, boolean flag) {
		Workbook wb = null;

		/**
		 * 1.判断文件是否存在
		 * 2.根据文件后缀（xls/xlsx）进行判断
		 * 3.若导出数据量大于DOWNLOAD_MAX_SIZE，则使用SXSSFWorkbook，否则使用XSSFWorkbook
		 */
		if (file.isFile() && file.exists()) {
			//.是特殊字符，需要转义！！！！！
			String[] split = file.getName().split("\\.");
			try (FileInputStream fis = new FileInputStream(file)) {
				//根据文件后缀（xls/xlsx）进行判断
				if ("xls".equals(split[1])) {
					wb = new HSSFWorkbook(fis);
				} else if ("xlsx".equals(split[1])) {
					XSSFWorkbook tmpWorkbook = new XSSFWorkbook(file);
					if (dataList.size() > DOWNLOAD_MAX_SIZE) {
						wb = new SXSSFWorkbook(tmpWorkbook);
					} else {
						wb = tmpWorkbook;
					}
				} else {
					logger.error("文件类型错误!");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 循环写入数据
		 */
		if (CollectionUtil.isNotEmpty(dataList)) {
			List<String[]> data = getDataList(dataList);
			xSize = data.size() + xStart - 1;
			ySize = data.get(0).length + yStart - 1;
			Sheet sheet = wb.getSheetAt(0);
			// 自适应列宽度
			sheet.autoSizeColumn(1, true);

			CellStyle cellStyle = format(wb);
			int tmp = 0;
			for (int x = xStart; x <= xSize; x++) {
				int tmp2 = 0;
				//创建一行
				Row row = sheet.createRow(x);
				//一行的所有值
				String[] strings = data.get(tmp);
				for (int y = yStart; y <= ySize; y++) {
					//值从第一个数据获取
					String value = strings[tmp2];
					Cell cell = row.createCell(y);
					cell.setCellValue(value);
					cell.setCellStyle(cellStyle);
					tmp2++;
				}
				tmp++;
			}
		}
		return wb;
	}

	/**
	 * 读取Excel文件中的workbook，转换为workbook对象
	 * 1.判断文件是否存在
	 * 2.根据文件后缀（xls/xlsx）进行判断
	 * 3.使用SXSSFWorkbook，导出大数据量的Excel
	 *
	 * @param file Excel文件
	 *             //	 * @param wb   workbook对象
	 * @return wb
	 */
	public static Workbook fileToWorkbook(File file) {
		Workbook wb = null;
		if (file.isFile() && file.exists()) {
			//.是特殊字符，需要转义！！！！！
			String[] split = file.getName().split("\\.");
			try (FileInputStream fis = new FileInputStream(file)) {
				//根据文件后缀（xls/xlsx）进行判断
				if ("xls".equals(split[1])) {
					wb = new HSSFWorkbook(fis);
				} else if ("xlsx".equals(split[1])) {
					XSSFWorkbook tmpWorkbook = new XSSFWorkbook(file);
					wb = new SXSSFWorkbook(tmpWorkbook);
					// 压缩临时文件，很重要，否则磁盘很快就会被写满
					((SXSSFWorkbook) wb).setCompressTempFiles(true);
				} else {
					logger.error("文件类型错误!");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return wb;
	}


	/**
	 * 将List<Map>转换为List<String>
	 *
	 * @param dataList 表名
	 * @return
	 */
	private static List<String[]> getDataList(List<Map<String, Object>> dataList) {
		List<String[]> resultList = new ArrayList<>();
		for (Map entry : dataList) {
			List<String> tmpList = new ArrayList<>();

			if (CollectionUtil.isNotEmpty(entry)) {
				entry.forEach((k, v) -> {
					if (Objects.nonNull(v)) {
						String tmp = String.valueOf(v);
						tmpList.add(tmp);
					} else {
						tmpList.add("");
					}
				});
				Set<String> sets = entry.keySet();
				resultList.add(tmpList.toArray(new String[sets.size()]));
			}
		}
		return resultList;
	}

}
