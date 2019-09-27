package com.kvn.mockj;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * 扩展函数
 * Created by wangzhiyuan on 2018/9/17
 */
public class $Function {
    private static final Random RANDOM = new Random();
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";


    public static boolean $boolean(String paramStr){
        return RANDOM.nextBoolean();
    }

    public static Integer $natural(String paramStr){
        return Math.abs($integer(paramStr));
    }

    public static Integer $integer(String paramStr){
        if (StringUtils.isBlank(paramStr)) {
            return RANDOM.nextInt();
        }

        String[] params = paramStr.split(",");
        if (params.length == 1) {
            return RANDOM.nextInt(Integer.parseInt(params[0].trim()));
        }

        if (params.length == 2) {
            return RandomUtils.nextInt(Integer.parseInt(params[0].trim()), Integer.parseInt(params[1].trim()) + 1);
        }

        return RANDOM.nextInt();
    }

    public static String $date(String paramStr) {
        if (StringUtils.isBlank(paramStr)) {
            return new Date().toInstant().toString();
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(paramStr));
    }

    public static char $character(String paramStr){
        String baseChars = paramStr == null ? LETTERS : paramStr;
        return LETTERS.charAt(RANDOM.nextInt(baseChars.length()));
    }

}
