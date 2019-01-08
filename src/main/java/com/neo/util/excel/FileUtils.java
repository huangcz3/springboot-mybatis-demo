package com.neo.util.excel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Huangcz
 * @date 2018-12-10 13:56
 * @desc xxx
 */
public class FileUtils {

	/**
	 * 生产带时间戳的文件格式名
	 *
	 * @param orgFileName
	 * @return
	 */
	public static String buildExportFileName(String orgFileName) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		StringBuffer fileName = new StringBuffer();
		fileName.append(orgFileName).append("_").append(dateFormat.format(new Date())).append(".xlsx");
		return fileName.toString();
	}


	/**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			return file.delete();
		} else {
			return false;
		}
	}
}
