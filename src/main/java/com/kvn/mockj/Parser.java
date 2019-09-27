package com.kvn.mockj;

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

    public static final String RE_KEY = "(.+)\\|(?:\\+(\\d+)|([\\+\\-]?\\d+-?[\\+\\-]?\\d*)?(?:\\.(\\d+-?\\d*))?)";
    public static final String RE_RANGE = "([\\+\\-]?\\d+)-?([\\+\\-]?\\d+)?";

    public static final Pattern RE_KEY_MATCHER = Pattern.compile(RE_KEY);
    public static final Pattern RE_RANGE_MATCHER = Pattern.compile(RE_RANGE);


    /**
     * RE_KEY: /(.+)\|(?:\+(\d+)|([\+\-]?\d+-?[\+\-]?\d*)?(?:\.(\d+-?\d*))?)/,
     * RE_RANGE: /([\+\-]?\d+)-?([\+\-]?\d+)?/,
     *
     * @param name
     * @return
     */
    public static Rule parseRule(String name) {
        name = name == null ? "" : name;

        Matcher matcher = RE_KEY_MATCHER.matcher(name);
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
            Matcher matcher1 = RE_RANGE_MATCHER.matcher(parameters.get(2));
            range = matcher1.matches();
            if (range) {
                min = Integer.parseInt(matcher1.group(1));
                max = StringUtils.isNotEmpty(matcher1.group(2)) ? Integer.parseInt(matcher1.group(2)) : null;
                count = max == null ? min : RandomUtils.nextInt(min, max + 1);
            }
        }

        boolean decimal = false;
        Integer dmin = null;
        Integer dmax = null;
        Integer dcount = null;
        if (parameters.size() > 0 && StringUtils.isNotEmpty(parameters.get(3))) {
            Matcher matcher1 = RE_RANGE_MATCHER.matcher(parameters.get(3));
            decimal = matcher1.matches();
            if (decimal) {
                dmin = Integer.parseInt(matcher1.group(1));
                dmax = StringUtils.isNotEmpty(matcher1.group(2)) ? Integer.parseInt(matcher1.group(2)) : null;
                dcount = dmax == null ? dmin : RandomUtils.nextInt(dmin, dmax + 1);
            }
        }

        return new Rule(parameters, range, min, max, count, decimal, dmin, dmax, dcount);
    }

    public static Class parseType(Object template) {
        return template.getClass();
    }
}
