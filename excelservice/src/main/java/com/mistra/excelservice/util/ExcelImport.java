package com.mistra.excelservice.util;

import com.mistra.base.exception.ServiceErrorException;
import com.mistra.excelservice.annotation.ExcelCell;
import com.mistra.excelservice.annotation.ExcelCellType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:Excel导入工具类
 */

public class ExcelImport {
    public ExcelImport() {
    }

    public static <T> List<T> excelTransformationEntityList(Class<T> clazz, InputStream in, String fileName, int headerLine, int stopReadCondition)
            throws IOException, ParseException {
        //1.获取工作簿
        Workbook book = getWorkBook(in, fileName);
        //2.获取所有工作表
        List<Sheet> sheets = getSheets(book);
        //3.对所有工作表进行操作
        List<T> list = sheetIterator(clazz, sheets, headerLine, stopReadCondition);
        return list;
    }

    /**
     * 获取工作簿
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
     * 获取所有工作表
     * @param book
     * @return
     */
    private static List<Sheet> getSheets(Workbook book) {
        int numberOfSheets = book.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(book.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * 对所有工作表进行操作
     * @param sheets
     * @param headerLine        表头行数
     * @param stopReadCondition 停止读取的条件（判断某个cell为空时就停止读取）
     * @return
     */
    private static <T> List<T>  sheetIterator(Class<T> clazz, List<Sheet> sheets, int headerLine, int stopReadCondition) throws ParseException {
        List<T> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Sheet sheet = sheets.get(0);
        Iterator<Row> iterator = sheet.iterator();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //循环遍历所有单元格
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            //nextRow.getRowNum()获取行数，过滤掉表头
            if (nextRow.getRowNum() < headerLine) {
                continue;
            }
            //判断是否还有数据，没有就停止读取，返回List
            if (nextRow.getCell(stopReadCondition).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                return list;
            }
            try {
                T entity = clazz.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    //isAnnotationPresent()指定注释类型的注释存在于此元素上，则返回true，否则返回false
                    boolean fieldHasAnnotation = field.isAnnotationPresent(ExcelCell.class);
                    if (fieldHasAnnotation) {
                        ExcelCell fieldAnnotation = field.getAnnotation(ExcelCell.class);
                        //输出注解属性
                        int columnIndex = fieldAnnotation.index();
                        ExcelCellType cellType = fieldAnnotation.value();
                        Cell cell = nextRow.getCell(columnIndex);
                        //CELL_TYPE_BLANK = 3
                        if (cell.getCellType() == 3){
                            continue;
                        }
                        switch (cellType) {
                            case CELL_TYPE_STRING:
                                field.set(entity, setStringValue(cell));
                                break;
                            case CELL_TYPE_NUMERIC:
                                field.set(entity, cell.getNumericCellValue());
                                break;
                            case CELL_TYPE_DECIMAL:
                                field.set(entity, new BigDecimal(cell.getNumericCellValue()));
                                break;
                            case CELL_TYPE_DATE:
                                if (cell.getCellType()==1){
                                    field.set(entity,simpleDateFormat.parse(cell.getStringCellValue().replace(".","-")));
                                }else if (cell.getCellType()==0){
                                    field.set(entity,simpleDateFormat.parse(String.valueOf(cell.getNumericCellValue()).replace(".","-")));
                                }
                                break;
                            case CELL_TYPE_BOOLEAN:
                                field.set(entity, cell.getBooleanCellValue());
                                break;
                            case CELL_TYPE_FORMULA:
                                field.set(entity, cell.getCellFormula());
                                break;
                            case CELL_TYPE_BLANK:
                            default:
                                break;
                        }
                    }
                }
                list.add(entity);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String setStringValue(Cell cell){
        String value;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    value=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    break;
                } else {
                    value = new DecimalFormat("##########.###").format(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                try {
                    value = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    public static Workbook generateWorkbook(MultipartFile multipartFile){
        Workbook wb;
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream fileInputStream = multipartFile.getInputStream();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (XLS.equalsIgnoreCase(suffix)) {
                wb = new HSSFWorkbook(fileInputStream);
            } else if (XLSX.equalsIgnoreCase(suffix)) {
                wb = new XSSFWorkbook(fileInputStream);
            } else {
                throw new ServiceErrorException("上传文件格式不支持！");
            }
        } catch (Exception e) {
            throw new ServiceErrorException("上传文件解析错误");
        }
        return wb;
    }
}
