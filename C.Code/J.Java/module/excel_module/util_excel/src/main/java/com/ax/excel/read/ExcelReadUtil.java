package com.ax.excel.read;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Excel阅读工具类
 *
 * @author admin
 */
public class ExcelReadUtil {


    /**
     * xls/xlsx都使用的Workbook
     *
     * @param fileName
     * @return
     * @author 吕小布  2018年10月26日
     */
    public static Workbook readExcel(String fileName) {

        Workbook wb = null;

        if (fileName == null) {
            return null;
        }

        String extString = fileName.substring(fileName.lastIndexOf("."));

        InputStream is = null;

        try {

            is = new FileInputStream(fileName);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wb;

    }







}
