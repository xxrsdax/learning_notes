package com.ax.regex;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author admin
 */
public class RegexUtil {

    /**
     * 可以用于测试 MAC 地址
     */
    public static boolean regexMac(String mac) {

        String patternStr = "([0-9A-Fa-f][0-9A-Fa-f]:){5}[0-9A-Fa-f]{2}";

        boolean matches = Pattern.matches(patternStr, mac);

        return matches;

    }


    public static void main(String[] args) {

        boolean b = RegexUtil.regexMac("44:55:66:11:Af:33");
        System.out.println(b);
        boolean b1 = RegexUtil.regexMac("44:55:66:11:Z2:33:44");
        System.out.println(b1);
        boolean b2 = RegexUtil.regexMac("44:55:66:11:A2:3");
        System.out.println(b2);
        boolean b3 = RegexUtil.regexMac("44:55:66:11:22:");
        System.out.println(b3);
        boolean b4 = RegexUtil.regexMac("44:55:66:11:22");
        System.out.println(b4);
        boolean b5 = RegexUtil.regexMac("44:55:66:11:222");
        System.out.println(b5);

        boolean b6 = RegexUtil.regexMac("B8:AE:ED:9D:E1:D9");
        System.out.println(b6);
    }

}
