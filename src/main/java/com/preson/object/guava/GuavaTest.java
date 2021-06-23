package com.preson.object.guava;

import com.google.common.base.Ascii;
import org.junit.Test;

public class GuavaTest {

    @Test
    public void asciiTest() {
        System.out.println(Ascii.BS);
        // 将字符串包含大写字符串变成小写字符串
        System.out.println(Ascii.toLowerCase("BhihUYOshjjkkdVHGSAVjhgjdajdqGGSAKJDGIDBWN"));
        // 将字符串包含小写字符串转换成大写字符串
        System.out.println(Ascii.toUpperCase("BhihUYOshjjkkdVHGSAVjhgjdajdqGGSAKJDGIDBWN"));
        // 判断一个字符是大写还是小写 大写返回true 小写返回false
        System.out.println(Ascii.isUpperCase('a'));
        // 判断一个字符是大写还是小写 大写返回false 小写返回true
        System.out.println(Ascii.isLowerCase('a'));
        // 判断两个字符串是否相等
        System.out.println(Ascii.equalsIgnoreCase("1", "1"));
        // 第一个参数是给定得参数 、 第二个参数是需要截取字符串得长度 、 第三个字符串是在后缀添加得字符串添加得字符串会
        // 覆盖掉前面得字符串
        System.out.println(Ascii.truncate("123456789", 6, "7889"));

    }

}
