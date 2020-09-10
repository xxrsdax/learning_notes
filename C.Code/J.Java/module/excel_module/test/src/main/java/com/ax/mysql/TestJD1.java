package com.ax.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassName : TestJD1  //类名
 * @Description :   //描述
 * @Author : luokun
 * @Date: 2020/9/9  16:55
 */
public class TestJD1 {
    //static int count = 0;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        conn();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start)/1000 + "秒");
    }

    public static void conn(){
        //1.导入驱动jar包
        //2.注册驱动(mysql5之后的驱动jar包可以省略注册驱动的步骤)
        //Class.forName("com.mysql.jdbc.Driver");
        //3.获取数据库连接对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        {
            try {
                //"&rewriteBatchedStatements=true",一次插入多条数据，只插入一次
                conn = DriverManager.getConnection("jdbc:mysql://119.23.12.250:3306/demo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8" + "&rewriteBatchedStatements=true","root","123456");
                //4.定义sql语句
                String sql = "insert into person values(default,?,?,?)";
                //5.获取执行sql的对象PreparedStatement
                pstmt = conn.prepareStatement(sql);
                //6.不断产生sql
                for (int i = 0; i < 10000000; i++) {
                    pstmt.setString(1,(int)(Math.random()*1000000)+"username");
                    pstmt.setString(2,(int)(Math.random()*1000000)+"password");
                    pstmt.setString(3,(int)(Math.random()*100)+"password");
                    pstmt.addBatch();
                }
                //7.往数据库插入一次数据
                pstmt.executeBatch();
                System.out.println("添加10000000条信息成功！");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //8.释放资源
                //避免空指针异常
                if(pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
