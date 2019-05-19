package com.pay.platform.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ExcelUtil {

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 14);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    /**
     * 从execl获取list
     *
     * @param file
     * @param titleMap
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getListOfExecl(File file, Map<String, String> titleMap) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Workbook workbook = null;
        InputStream fs = null;

        try {
            //解析execl
            fs = new FileInputStream(file);
            String name = file.getName();
            if (name.endsWith(".xlsx"))
                workbook = new XSSFWorkbook(OPCPackage.open(fs));//初始化workbook对象
            else if (name.endsWith(".xls"))
                workbook = new HSSFWorkbook(fs);//初始化workbook对象
            else
                throw new Exception("你的excel版本目前poi解析不了");
            //处理内容
            //仅处理第一个工作本
            Sheet sheet = workbook.getSheetAt(0);
            int size = sheet.getRow(0).getLastCellNum();
            //
            String[] titleArray = new String[size];
            for (int i = 0; i < size; i++) {
                for (String key : titleMap.keySet()) {
                    if (key.equals(sheet.getRow(0).getCell(i).getStringCellValue())) {
                        titleArray[i] = titleMap.get(key);
                    } else {

                    }
                }
                if (titleArray[i] == null) {
                    titleArray[i] = "";
                }
            }
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                Map<String, Object> map = new HashMap<String, Object>();
                for (int j = 0; j < size; j++) {
                    if (row.getCell(j) == null) {
                        //可以注释掉，用null代替
                        map.put(titleArray[j], "");
                    } else {
                        switch (row.getCell(j).getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                //去掉execl链接图片的\ ------>>>  /
                                map.put(titleArray[j], row.getCell(j).getStringCellValue().replaceAll("\\\\", "/"));
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                map.put(titleArray[j], row.getCell(j).getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                map.put(titleArray[j], row.getCell(j).getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                map.put(titleArray[j], row.getCell(j).getNumericCellValue());
                                break;
                            default:
                                map.put(titleArray[j], "");
                        }
                    }


                }
                boolean passflag = false;
                for (String key : map.keySet()) {
                    if (map.get(key) != null && !"".equals(map.get(key))) {
                        passflag = true;
                    }
                }
                if (passflag) {
                    list.add(map);
                } else {
                    return list;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            fs.close();
        }

        return list;
    }

    /**
     * 解析Excel文件
     *
     * @param inStream      Excel文件的输入流
     * @param fieldNameList 属性名集合，顺序与Excel中列的顺序一致
     * @return Map类型的集合，其中Key为属性名，Value可以理解为属性值（实际为Excel中的列值）
     */
    public static List<Map<String, String>> parseExcel(InputStream inStream, List<String> fieldNameList, String fileName) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Workbook book = null;
        try {
            if (fileName.endsWith(".xlsx"))
                book = new XSSFWorkbook(OPCPackage.open(inStream));//初始化workbook对象
            else if (fileName.endsWith(".xls"))
                book = new HSSFWorkbook(inStream);//初始化workbook对象
            else
                throw new Exception("你的excel版本目前poi解析不了");
            Sheet sheet = book.getSheetAt(0);
            // -1：去除第一行标题
            int rows = sheet.getPhysicalNumberOfRows();
            //
            // int columns = sheet.getRow(0).getPhysicalNumberOfCells();
            Row row;
            Map<String, String> map;
            for (int i = 1; i < rows; i++) {
                row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null || !StringUtil.isNotEmpty(row.getCell(0).toString())) {
                    continue;
                }
                map = new HashMap<String, String>();
                for (int j = 0; j < fieldNameList.size(); j++) {

                    if (fieldNameList.get(j) != null && row.getCell(j) != null) {
                        map.put(fieldNameList.get(j), row.getCell(j).toString());
                    } else {
                        map.put(fieldNameList.get(j), "");
                    }

                }
                resultList.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * 解析Excel文件
     *
     * @param file          Excel文件
     * @param fieldNameList 属性名集合，顺序与Excel中列的顺序一致
     * @return Map类型的集合，其中Key为属性名，Value可以理解为属性值（实际为Excel中的列值）
     */
    public static List<Map<String, String>> parseExcel(File file, List<String> fieldNameList) {
        try {
            String fileName = file.getName();
            return parseExcel(new FileInputStream(file), fieldNameList, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建 HSSFWorkbook
     *
     * @param
     * @return HSSFWorkbook
     */
    public static HSSFWorkbook createExcel(String tableTitle, String sheetName, List<String> columns, List<Map<String, Object>> values) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet excel = workbook.createSheet(sheetName);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);               //粗体
        int rowNum = 0;
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        if (tableTitle != null) {
            Font titleFont = workbook.createFont();
            titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);               //粗体
            titleFont.setFontHeightInPoints((short) 20);
            cellStyle.setFont(titleFont);
            //返回空的excel文件，避免页面空白
            excel.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.size() > 0 ? columns.size() - 1 : 10));
            HSSFRow titleRow = excel.createRow(rowNum);
            HSSFCell cell = titleRow.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(tableTitle);
            rowNum++;
        }
        cellStyle.setFont(font);
        //设置背景颜色
        cellStyle.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow headrow = excel.createRow(rowNum);
        rowNum++;
        Map<String, String> map = null;
        int i = 0;
        for (String title : columns) {            //设置表头
            HSSFCell cell = headrow.createCell(i);
            excel.setColumnWidth(i, 20 * 256);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title);
            i++;
        }
        //注入参数
        for (int j = 0; j < values.size(); j++) {                //填值
            Map model = (Map) values.get(j);
            HSSFRow row = excel.createRow(j + rowNum);
            i = 0;
            for (String title : columns) {
                HSSFCell cell = row.createCell(i);
                Object obj = model.get(title);
                cell.setCellValue(obj != null ? obj.toString() : "");
                i++;
            }
        }
        return workbook;
    }

}
