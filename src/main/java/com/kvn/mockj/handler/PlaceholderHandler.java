package com.kvn.mockj.handler;

import com.kvn.mockj.$Function;
import com.kvn.mockj.Options;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangzhiyuan on 2019/9/27
 */
public class PlaceholderHandler {
    private static final Pattern pattern = Pattern.compile("(@\\w+)(\\(.*?\\))?");

    public static Object doGenerate(Options options) {

        String methodName = null;
        String paramStr = null;

        Matcher matcher = pattern.matcher(options.getTemplate().toString());
        StringBuffer sbRtn = new StringBuffer();
        boolean hasFind = false;
        while (matcher.find()) {
            hasFind = true;
            int groupCount = matcher.groupCount();
            if (groupCount == 1) {
                methodName = matcher.group(1);
            } else if (groupCount == 2) {
                methodName = matcher.group(1);
                String group2 = matcher.group(2);
                paramStr = group2 == null ? null : group2.substring(1, group2.length() - 1);
            }
            try {
                methodName = "$" + methodName.substring(1);
                Object invokeRlt = $Function.class.getMethod(methodName, String.class).invoke(null, paramStr);
                matcher.appendReplacement(sbRtn, invokeRlt.toString());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("$Function 中不存在方法" + methodName, e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return hasFind ? sbRtn.toString() : options.getTemplate();
    }

    public static void main(String[] args) {
        Matcher matcher = pattern.matcher("@EMAIL==@EXX==5@ERR ");
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            System.out.println(matcher.group());
            matcher.appendReplacement(sb, "11");
        }

        System.out.println(sb.toString());
    }

}
