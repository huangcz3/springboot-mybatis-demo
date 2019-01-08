package com.neo.util;


import jxl.Workbook.*;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Huangcz
 * @date 2018-09-07 10:18
 * @desc
 */
public class ExcelUtil {

    /**
     *
     * @param workbookSheet sheet页
     * @param dataList  需要写入的数据list
     * @param xStart x开始坐标
     * @param xSize 表格x结束位置
     * @param yStart y开始坐标
     * @param ySize 表格y结束位置
     * @param flag 是否合并表格
     * @param clospan 合并列
     * @param a 需要合并的表格行号
     * @param b 需要合并的表格数据行号
     */
    public void updateExcleWrite(WritableSheet workbookSheet, List<Map> dataList, int xStart, int xSize, int yStart , int ySize, boolean flag, int clospan, int[] a, int[] b){
        List<String[]> list = getDataList(dataList);
        try {
            int tmp=0;
            String content="";
            for (int y = yStart; y <= ySize; y++) {
                String contents = workbookSheet.getCell(xStart, y).getContents();

                for (int x =xStart ; x < xSize; x++) {
                    for (int i = 0; i < list.size(); i++) {
                        content  = list.get(i)[2];
                        if (contents.equals(content)) {
                            // System.out.println(contents + ":" + content);
                            tmp = i;
                            break;
                        } else {
                            tmp = 100;
                        }
                    }
                    CellFormat cellFormat = workbookSheet.getCell(x + 1, y).getCellFormat();
                    if (tmp!=100) {
                        workbookSheet.addCell(new Label(x + 1, y, list.get(tmp)[x], cellFormat));
                    }else {
                        workbookSheet.addCell(new Label(x + 1, y, "0",cellFormat));
                        //  System.out.println("----------------------------->"+tmp);
                    }
                }
            }

            if(flag){
                for (int i = 0; i <a.length ; i++) {
                    workbookSheet.mergeCells(clospan,a[i],clospan,b[i]);
                    workbookSheet.mergeCells(clospan+1,a[i],clospan+1,b[i]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 将List<Map>转换为List<String>
     *
     * @param dataList 表名
     * @return
     */
    public List<String[]> getDataList(List<Map> dataList) {
        List<String[]> resultList = new ArrayList<>();
        for (Map enty : dataList) {
            List<String> tmp = new ArrayList<>();
            Set<String> sets = enty.keySet();
            sets.forEach(x -> tmp.add(String.valueOf(enty.get(x))));
            resultList.add(tmp.toArray(new String[sets.size()]));
        }
        return resultList;
    }


}
