package com.kvn.mockj.v2;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://github.com/nuysoft/Mock/blob/refactoring/src/mock/parser.js
 * Created by wangzhiyuan on 2019/9/26
 */
public class Parser {


    /**
     *     GUID: 1,
     *     RE_KEY: /(.+)\|(?:\+(\d+)|([\+\-]?\d+-?[\+\-]?\d*)?(?:\.(\d+-?\d*))?)/,
     *     RE_RANGE: /([\+\-]?\d+)-?([\+\-]?\d+)?/,
     *     RE_PLACEHOLDER: /\\*@([^@#%&()\?\s]+)(?:\((.*?)\))?/g
     * @param name
     * @return
     */
    public static Rule parseRule(String name){
        name = name == null ? "" : name;
        Matcher matcher = Pattern.compile("(.+)\\|(?:\\+(\\d+)|([\\+\\-]?\\d+-?[\\+\\-]?\\d*)?(?:\\.(\\d+-?\\d*))?)").matcher(name);
        List<String> parameters = new ArrayList<>();
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                parameters.add(matcher.group(i));
            }
        }

        boolean range = false;
        Integer min = null;
        Integer max = null;
        Integer count = null;
        if (parameters.size() > 0 && StringUtils.isNotEmpty(parameters.get(2))) {
            Matcher matcher1 = Pattern.compile("([\\+\\-]?\\d+)-?([\\+\\-]?\\d+)?").matcher(parameters.get(2));
            range = matcher1.matches();
            if (range) {
                min = Integer.parseInt(matcher1.group(1));
                max = StringUtils.isNotEmpty(matcher1.group(2)) ? Integer.parseInt(matcher1.group(2)) : null;
                count = max == null ? min : RandomUtils.nextInt(min, max);
            }
        }

        boolean decimal = false;
        Integer dmin = null;
        Integer dmax = null;
        Integer dcount = null;
        if (parameters.size() > 0 && StringUtils.isNotEmpty(parameters.get(3))) {
            Matcher matcher1 = Pattern.compile("([\\+\\-]?\\d+)-?([\\+\\-]?\\d+)?").matcher(parameters.get(3));
            decimal = matcher1.matches();
            if (decimal) {
                dmin = Integer.parseInt(matcher1.group(1));
                dmax = StringUtils.isNotEmpty(matcher1.group(2)) ? Integer.parseInt(matcher1.group(2)) : null;
                dcount = dmax == null ? dmin : RandomUtils.nextInt(dmin, dmax);
            }
        }


        Rule rule = new Rule(parameters,range,min,max,count,decimal,dmin,dmax,dcount);
        return rule;
    }

    public static Class parseType(Object template) {
        return template.getClass();
    }
}
