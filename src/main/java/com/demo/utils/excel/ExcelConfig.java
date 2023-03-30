package com.demo.utils.excel;

import com.demo.utils.request.StatisticDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.List;

import static com.demo.utils.excel.FileConfig.PATH_TEMPLATE;

@Component
@Slf4j
public class ExcelConfig {
    public static ByteArrayInputStream exportCustomer(List<StatisticDTO>  statistics, String fileName) throws  Exception {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        //Get file  -> not found --> create new file
        File file;

        FileInputStream fileInputStream;

        try {
            file = ResourceUtils.getFile(PATH_TEMPLATE + fileName);
            fileInputStream = new FileInputStream(file);
        } catch (Exception e)
        {
            log.info("FILE NOT FOUND");
            file = FileConfig.createFile(fileName, xssfWorkbook);
            fileInputStream = new FileInputStream(file);
        }

        //create freeze in excel file
        XSSFSheet newSheet = xssfWorkbook.createSheet("Statistic Invoice");

        newSheet.createFreezePane(4, 2, 4, 2);

        // create font for the title
        XSSFFont titleFont = xssfWorkbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 15);

        //create style for cell of title and apply font to cell
        XSSFCellStyle titleCellStyle = xssfWorkbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillForegroundColor(IndexedColors.AQUA.index);
        titleCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        titleCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        titleCellStyle.setBorderRight(BorderStyle.MEDIUM);
        titleCellStyle.setBorderTop(BorderStyle.MEDIUM);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setWrapText(true);

        //create font for data
        XSSFFont dataFont = xssfWorkbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setBold(false);
        dataFont.setFontHeightInPoints( (short) 11);

        //create style for cell data and apply font to cell
        XSSFCellStyle dataCellStyle = xssfWorkbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setFont(dataFont);
        dataCellStyle.setWrapText(true);

        //insert fieldName as title to excel
        insertFieldNameAsTitleToWorkBook(ExportConfig.exportConfig.getCellExportConfigs(),
                newSheet, titleCellStyle);
        // insert data of fieldName to excel
        insertDataToWorkBook(xssfWorkbook, ExportConfig.exportConfig, statistics, dataCellStyle);

        //return
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xssfWorkbook.write(outputStream);

        //close resource
        outputStream.close();
        fileInputStream.close();

        log.info("Done");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private static<T> void insertDataToWorkBook(Workbook workbook,
                                                ExportConfig exportConfig,
                                                List<T>datas,
                                                XSSFCellStyle dataCellStyle)
    {
        int startRowIndex = exportConfig.getStartRow(); // start from row 2

        int sheetIndex = exportConfig.getSheetIndex(); // the sheet index is 1

        Class  CLass = exportConfig.getDataClass();

        List<CellConfig> cellConfigs = exportConfig.getCellExportConfigs();

        Sheet sheet = workbook.getSheetAt(sheetIndex);

        int currentRowIndex = startRowIndex;

        for(T data : datas)
        {
            Row currentRow = sheet.getRow(currentRowIndex);
            if(ObjectUtils.isEmpty(currentRow))
            {
                currentRow = sheet.createRow(currentRowIndex);
            }
            //insert data to row
            insertDataToCell(data, currentRow, cellConfigs, CLass, sheet, dataCellStyle);
            ++currentRowIndex;
        }

    }

    private static <T> void insertFieldNameAsTitleToWorkBook(List<CellConfig> cellConfigs,
                                                             Sheet sheet,
                                                             XSSFCellStyle titleCellStyle)
    {
        //title -> first row of excel -> get top row
        int currentRow = sheet.getTopRow();
        Row row = sheet.createRow(currentRow);

        //create row
        int index = 0;

        //resize fix text in each cell
        sheet.autoSizeColumn(currentRow);

        //insert fieldName to cell
        for(CellConfig cellConfig : cellConfigs)
        {
            Cell currentCell = row.createCell(index);
            String fieldName = cellConfig.getFieldName();
            currentCell.setCellValue(fieldName);
            currentCell.setCellStyle(titleCellStyle);
            sheet.autoSizeColumn(index);
            ++index;
        }
    }

    private static <T> void insertDataToCell(T data,
                                             Row currentRow,
                                             List<CellConfig> cellConfigs,
                                             Class CLass,
                                             Sheet sheet,
                                             XSSFCellStyle dataStyle)
    {
        for(CellConfig cellConfig : cellConfigs)
        {
            Cell currentCell = currentRow.getCell(cellConfig.getColumnIndex());
            if(ObjectUtils.isEmpty(currentCell))
            {
                currentCell = currentRow.createCell(cellConfig.getColumnIndex());
            }

            //get data from cell
            String cellValue = getCellValue(data, cellConfig, CLass);

            //set data
            currentCell.setCellValue(cellValue);

            sheet.autoSizeColumn(cellConfig.getColumnIndex());

            currentCell.setCellStyle(dataStyle);
        }
    }

    private static <T> String getCellValue(T data, CellConfig cellConfig, Class cLass) {
        String fieldName = cellConfig.getFieldName();
        try
        {
            Field field = getDeclaredField(cLass, fieldName);

            if(!ObjectUtils.isEmpty(field))
            {
                field.setAccessible(true);
                return !ObjectUtils.isEmpty(field.get(data)) ? field.get(data).toString() : "";
            }
            return "";
        }
        catch (Exception e)
        {
            log.info("" + e);
            return "";
        }
    }

    private static Field getDeclaredField(Class cLass, String fieldName) {
        if(ObjectUtils.isEmpty(cLass) || ObjectUtils.isEmpty(fieldName))
        {
            return null;
        }
        do {
            try
            {
                Field field = cLass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            }
            catch (Exception e)
            {
                log.info("" + e);
            }
        }
        while ((cLass = cLass.getSuperclass()) != null);

        return null;
    }
}
