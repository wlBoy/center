package com.xn.hk.common.utils.string;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: HumpUtil
 * @Package: com.xn.hk.common.utils.string
 * @Description: 驼峰下划线互转工具类
 * @Author: wanlei
 * @Date: 2020年11月26日 上午11:44:23
 */
public class HumpUtil {

    private HumpUtil() {
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */
    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */
    public static String humpToUnderline(String para) {
        if (para == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(para);
        //定位
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /***
     * 驼峰命名转为下划线命名,小写
     * 单独添加小写方法，influxdb字段为小写，与postgres数据库字段区分开来
     *
     * @param para
     *        驼峰命名的字符串
     */
    public static String humpToUnderlineLowerCase(String para) {
        para = humpToUnderline(para);
        return para == null ? null : para.toLowerCase();
    }

    /**
     * 将list集合字段转成下划线的表字段
     */
    public static List<String> getUnderlineList(List<String> list) {
        List<String> underlineList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (String humpString : list) {
                underlineList.add(HumpUtil.humpToUnderline(humpString));
            }
        }
        return underlineList;
    }

    /**
     * 将list集合字段转成下划线的表字段,小写
     */
    public static List<String> getUnderlineLowerCaseList(List<String> list) {
        List<String> underlineList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (String humpString : list) {
                underlineList.add(HumpUtil.humpToUnderlineLowerCase(humpString));
            }
        }
        return underlineList;
    }


    /**
     * 将map集合的key字段转成下划线的表字段
     */
    public static <T> Map<String, T> getUnderlineKeyMap(Map<String, T> fields) {
        Map<String, T> underlineMap = new HashMap<>(32);
        if (MapUtils.isNotEmpty(fields)) {
            for (Map.Entry<String, T> entry : fields.entrySet()) {
                underlineMap.put(HumpUtil.humpToUnderline(entry.getKey()), entry.getValue());
            }
        }
        return underlineMap;
    }


    /**
     * 将map集合的key字段转成下划线的表字段,小写
     */
    public static <T> Map<String, T> getUnderlineLowerCaseKeyMap(Map<String, T> fields) {
        Map<String, T> underlineMap = new HashMap<>(32);
        if (MapUtils.isNotEmpty(fields)) {
            for (Map.Entry<String, T> entry : fields.entrySet()) {
                underlineMap.put(HumpUtil.humpToUnderlineLowerCase(entry.getKey()), entry.getValue());
            }
        }
        return underlineMap;
    }

    /**
     * 将map集合的value字段（list集合)转成下划线的表字段
     */
    public static Map<String, List<String>> getUnderlineValueMap(Map<String, List<String>> fields) {
        Map<String, List<String>> underlineValueMap = new HashMap<>(32);
        if (MapUtils.isNotEmpty(fields)) {
            for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (value != null) {
                    List<String> underlineList = new ArrayList<>(value.size());
                    for (String humpString : value) {
                        underlineList.add(HumpUtil.humpToUnderline(humpString));
                    }
                    underlineValueMap.put(key, underlineList);
                }
            }
        }
        return underlineValueMap;
    }

    /**
     * 将map集合的value字段（list集合)转成下划线的表字段,小写
     */
    public static Map<String, List<String>> getUnderlineLowerCaseValueMap(Map<String, List<String>> fields) {
        Map<String, List<String>> underlineValueMap = new HashMap<>(32);
        if (MapUtils.isNotEmpty(fields)) {
            for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (value != null) {
                    List<String> underlineList = new ArrayList<>(value.size());
                    for (String humpString : value) {
                        underlineList.add(HumpUtil.humpToUnderlineLowerCase(humpString));
                    }
                    underlineValueMap.put(key, underlineList);
                }
            }
        }
        return underlineValueMap;
    }

    /**
     * 驼峰式转中横线小写
     * <p>
     * spring的properties中常用到
     *
     * @param para
     * @return
     */
    public static String toHorizontalLineLowerCase(String para) {
        if (para == null) {
            return null;
        }
        return humpToUnderline(para).replace("_", "-").toLowerCase();
    }
}
