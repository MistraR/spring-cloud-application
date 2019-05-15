package com.mistra.base.utils.enumutils;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-09 11:30
 * @ Description:
 */
public class EnumUtil {

    /**
     * 根据名称或序号获取枚举
     *
     * @param clz 枚举类类型
     * @param val 名称或序号
     * @param <T> 枚举类
     * @return 枚举
     */
    public static <T> T getEnum(Class<? extends T> clz, Object val) throws Exception {
        T[] enums = clz.getEnumConstants();
        for (T enumObj : enums) {
            Enum enumVal = (Enum) enumObj;
            if (val instanceof String) {
                // 匹配名字
                if (enumVal.name().equals(val)) {
                    return enumObj;
                }
            } else if (val instanceof Integer) {
                int ord = (int) val;
                if (ord < 0 || ord > enums.length) {
                    throw new Exception("parse enum " + clz.getName() + " error, value : " + val);
                }
                return enums[ord];
            }
        }
        return null;
    }

    /**
     * 根据名称或字符串序号获取枚举
     *
     * @param clz 枚举类类型
     * @param val 名称或序号
     * @param <T> 枚举类
     * @return 枚举
     */
    public static <T> T getEnum(Class<? extends T> clz, String val) {
        T[] enums = clz.getEnumConstants();
        for (T enumObj : enums) {
            Enum enumVal = (Enum) enumObj;
            // 匹配名字
            if (enumVal.name().equals(val)) {
                return enumObj;
            }
            // 匹配序号
            if (String.valueOf(enumVal.ordinal()).equals(val)) {
                return enumObj;
            }
        }
        return null;
    }

}
