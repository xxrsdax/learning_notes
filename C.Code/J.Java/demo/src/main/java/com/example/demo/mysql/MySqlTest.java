package com.example.demo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ax
 */
public class MySqlTest {

//    public static void main(String[] args) throws Exception {
//        //1.注册数据库的驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        //2.获取数据库连接（里面内容依次是："jdbc:mysql://主机名:端口号/数据库名","用户名","登录密码"）
//        Connection connection = DriverManager.getConnection("jdbc:mysql://119.23.12.250:3306/demo?useSSL=false&serverTimezone=UTC", "root", "123456");
//
//        //3.需要执行的sql语句（?是占位符，代表一个参数）
//        String sql = "insert into person(name,pwd,age) values(?,?,?)";
//
//        //4.获取预处理对象，并依次给参数赋值
//        PreparedStatement statement = connection.prepareCall(sql);
//        statement.setInt(1, 12); //数据库字段类型是int，就是setInt；1代表第一个参数
//        statement.setString(2, "小明");    //数据库字段类型是String，就是setString；2代表第二个参数
//        statement.setInt(3, 16); //数据库字段类型是int，就是setInt；3代表第三个参数
//
//        //5.执行sql语句（执行了几条记录，就返回几）
//        int i = statement.executeUpdate();
//        System.out.println(i);
//
//        //6.关闭jdbc连接
//        statement.close();
//        connection.close();
//    }


    public static void main(String[] args) throws ClassNotFoundException {

        long start = System.currentTimeMillis();
        conn();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000 + "秒");
    }

    public static void conn() throws ClassNotFoundException {
        //1.导入驱动jar包
        //2.注册驱动(mysql5之后的驱动jar包可以省略注册驱动的步骤)
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3.获取数据库连接对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //"&rewriteBatchedStatements=true",一次插入多条数据，只插入一次
            conn = DriverManager.getConnection("jdbc:mysql://119.23.12.250:3306/demo?useSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8" + "&rewriteBatchedStatements=true", "root", "123456");

            //4.定义sql语句
            String sql = "insert into person (name,pwd,age)values(?,?,?)";

            //5.获取执行sql的对象PreparedStatement
            pstmt = conn.prepareStatement(sql);

            //6.不断产生sql
            for (int i = 0; i < 10000000; i++) {
                pstmt.setString(1, (int) (Math.random() * 1000000) + "name");
                pstmt.setString(2, (int) (Math.random() * 1000000) + "pwd");
                pstmt.setInt(3, (int) (Math.random() * 100));
                pstmt.addBatch();
            }

            //7.往数据库插入一次数据
            pstmt.executeBatch();
            System.out.println("添加10000000条信息成功！");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //8.释放资源
            //避免空指针异常
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}


