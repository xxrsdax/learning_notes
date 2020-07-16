package com.ax.excel.read;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 测试Excel阅读工具类
 *
 * @author admin
 */
public class TestExcelReadUtil {


    public static void main(String[] args) {

        // 获得excel文件对象workbook
        Workbook wb = ExcelReadUtil.readExcel("C:\\Users\\11786\\Desktop\\test.xlsx");

        // 获取指定工作表<这里获取的是第一个>
        Sheet s = wb.getSheetAt(0);

        //循环行  sheet.getPhysicalNumberOfRows()是获取表格的总行数
        for (int i = 0; i < s.getPhysicalNumberOfRows(); i++) {
            System.out.println("第" + (i + 1) + "行内容:");

            // 取出第i行  getRow(index) 获取第(index+1)行
            Row row = s.getRow(i);

            // getPhysicalNumberOfCells() 获取当前行的总列数
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                //getCell 获取单元格中的内容
                Cell cell = row.getCell(j);

                Double value1 = null;

                if (cell != null) {
                    value1 = cell.getNumericCellValue();
                }
                System.out.print(i + "行" + j + "列:" + value1 + "\t");
            }

            System.out.println("");

        }

    }

}
