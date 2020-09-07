package com.ax.se8;

import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 方法引用 测试
 *
 * @author ax
 */
public class MethodReference {

    public static void main(String[] args) {

        //定期执行服务
        //        Timer t = new Timer(1000,System.out::println);

        //
        String[] strings = "a,dd".split(",");
        Arrays.sort(strings, String::compareToIgnoreCase);


    }

}
