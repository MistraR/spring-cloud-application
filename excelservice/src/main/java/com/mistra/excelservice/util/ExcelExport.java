package com.mistra.excelservice.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:Excel导出工具类，根据已有的Excel模板，修改模板内容生成新的Excel并导出
 */

public class ExcelExport {
    public ExcelExport() {
    }

    public static <T> void exportByModel(HttpServletResponse response, String fileName, List<Map<Integer, Object>> list, String modelPath, int headerLine) throws IOException {
        File file = new File(modelPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = getWorkBook(fileInputStream, modelPath);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.setForceFormulaRecalculation(true);//公式自动计算
        Map<Integer, CellStyleAndType> cellStyleMap = getModelRowCellStyle(sheet, headerLine);
        //sheet.shiftRows(headerLine, headerLine, -1);//删除模板的样式行(预留的第一行数据行)
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(headerLine + i);
            Map<Integer, Object> entityMap = list.get(i);
            for (int j = 0; j < entityMap.size(); j++) {
                Cell cell = row.createCell(j);
                Object obj = entityMap.get(j);
                if (obj != null) {
                    if (obj instanceof Integer) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((int) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Float) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((Float) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    } else if (obj instanceof Boolean) {
                        cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((Date) obj);
                    }
                    else if (obj instanceof Calendar) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((Calendar) obj);
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                        cell.setCellValue((String) obj);
                    }
                }else {
                    cell.setCellStyle(cellStyleMap.get(j).getCellStyle());
                }
            }
        }

        String fileNameSuffix = ".xls";
        if (modelPath.contains(".xlsx")) {
            fileNameSuffix = ".xlsx";
        }
        /*FileOutputStream out = new FileOutputStream("D:/"+fileName+fileNameSuffix);//本地输出
        workbook.write(out);
        out.close();*/
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + fileNameSuffix);
        response.setContentType("application/ms-excel;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * 获取工作簿
     *
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkBook(InputStream in, String fileName) throws IOException {
        return fileName.toLowerCase().endsWith(".xls") ? (new HSSFWorkbook(in))
                : (fileName.toLowerCase().endsWith(".xlsx") ? (new XSSFWorkbook(in)) : (null));
    }

    /**
     * 遍历模板获取单元格样式和类型
     *
     * @param sheet
     * @param headerLine 表头行数
     * @return
     */
    public static Map<Integer, CellStyleAndType> getModelRowCellStyle(Sheet sheet, int headerLine) {
        Map<Integer, CellStyleAndType> cellStyleAndTypeMap = new HashMap<>();
        Row row = sheet.getRow(headerLine);
        Iterator<Cell> cellIterator = row.cellIterator();
        int index = 0;
        while (cellIterator.hasNext()) {
            CellStyleAndType cellStyleAndType = new CellStyleAndType();
            Cell cell = cellIterator.next();
            cellStyleAndType.setCellStyle(cell.getCellStyle());
            cellStyleAndType.setCellType(cell.getCellType());
            cellStyleAndTypeMap.put(index, cellStyleAndType);
            index++;
        }
        return cellStyleAndTypeMap;
    }


    static class CellStyleAndType {
        private CellStyle cellStyle;
        private int cellType;

        public CellStyle getCellStyle() {
            return cellStyle;
        }

        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }

        public int getCellType() {
            return cellType;
        }

        public void setCellType(int cellType) {
            this.cellType = cellType;
        }
    }
}
