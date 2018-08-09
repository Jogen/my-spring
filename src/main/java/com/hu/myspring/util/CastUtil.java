package com.hu.myspring.util;

/**
 * description
 *
 * @author hujunfei
 * @date 2018-08-03 16:32
 */
public class CastUtil {

    private CastUtil() {}

    /**
     * 返回基本数据类型的空值
     * @param clz
     * @return
     */
    public static Object primitiveNull(Class<?> clz) {
        if (clz.equals(int.class) || clz.equals(double.class) ||
                clz.equals(short.class) || clz.equals(long.class) ||
                clz.equals(byte.class) || clz.equals(float.class)) {
            return 0;
        }
        if (clz.equals(boolean.class)) {
            return false;
        }
        return null;
    }

    /**
     * String 类型转换成对应类型
     * @param clz
     * @param value
     * @return
     */
    public static Object convert(Class<?> clz, String value) {
        if (isPrimitive(clz)) {
            if (ValidateUtil.isEmpty(value)) {
                return primitiveNull(clz);
            }
            if (clz.equals(int.class) || clz.equals(Integer.class)) {
                return Integer.parseInt(value);
            }
            if (clz.equals(float.class) || clz.equals(Float.class)) {
                return Float.parseFloat(value);
            }
            if (clz.equals(double.class) || clz.equals(Double.class)) {
                return Double.parseDouble(value);
            }
            if (clz.equals(long.class) || clz.equals(Long.class)) {
                return Long.parseLong(value);
            }
            if (clz.equals(short.class) || clz.equals(Short.class)) {
                return Short.parseShort(value);
            }
            if (clz.equals(byte.class) || clz.equals(Byte.class)) {
                return Byte.parseByte(value);
            }
            if (clz.equals(boolean.class) || clz.equals(Boolean.class)) {
                return Boolean.parseBoolean(value);
            }
            return value;
        } else {
            throw new RuntimeException("暂不支持非原生");
        }
    }

    public static boolean isPrimitive(Class<?> clz) {
        return clz == boolean.class
                || clz == Boolean.class
                || clz == double.class
                || clz == Double.class
                || clz == float.class
                || clz == Float.class
                || clz == short.class
                || clz == Short.class
                || clz == int.class
                || clz == Integer.class
                || clz == long.class
                || clz == Long.class
                || clz == String.class
                || clz == byte.class
                || clz == Byte.class
                || clz == char.class
                || clz == Character.class;
    }
}
