# 1.导入依赖

```xml

        <!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>4.1.2</version>
        </dependency>
```



# 2.工具代码

其流程:  针对不同后缀结尾的文件 ，生成不同的workbook

```java
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

```



# 3.使用案例

```java
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

```







# 4.说明

使用前，请先阅读工具代码模块和使用案例模块，

在使用案例模块中  还存在着一些问题  如果后期修改后 记得更新案例代码