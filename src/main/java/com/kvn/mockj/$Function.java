package com.kvn.mockj;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * 扩展函数。当 baseValue 的值是以 $ 开头时，会调用 $Function 中的方法。
 * Created by wangzhiyuan on 2018/9/17
 */
public class $Function {
    private static final Random RANDOM = new Random();
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    public static String $date(String paramStr) {
        if (StringUtils.isBlank(paramStr)) {
            return new Date().toInstant().toString();
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(paramStr)).toString();
    }

    public static char $char(String paramStr){
        String baseChars = paramStr == null ? LETTERS : paramStr;
        return LETTERS.charAt(RANDOM.nextInt(baseChars.length()));
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString());
    }
}
