package com.admin4j.dict.core;

import java.math.BigInteger;

/**
 * 类型转化工具类
 *
 * @author andanyang
 * @since 2022/7/24 16:55
 */
public class ObjectCast {

    private ObjectCast() {
    }

    public static Long castToLong(Object obj) {
        Long code;
        if (obj instanceof Long) {
            code = ((Long) obj);
        } else if (obj instanceof Integer) {
            code = Long.valueOf((Integer) obj);
        } else if (obj instanceof BigInteger) {
            code = ((BigInteger) obj).longValue();
        } else {
            code = Long.parseLong(obj.toString());
        }
        return code;
    }

    public static Integer castToInteger(Object obj) {
        int code;
        if (obj instanceof Number) {
            code = ((Number) obj).intValue();
        } else {
            code = Integer.parseInt(obj.toString());
        }
        return code;
    }

    public static String castToString(Object obj) {

        if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj.toString();
        }
    }

    public static <T> T cast(Object object, Class<T> aClass) {

        if (object.getClass().equals(aClass)) {
            return (T) object;
        }
        if (aClass.isAssignableFrom(Integer.class)) {
            return (T) castToInteger(object);
        } else if (aClass.isAssignableFrom(Long.class)) {
            return (T) castToLong(object);
        } else if (aClass.isAssignableFrom(String.class)) {
            return (T) castToString(object);
        }

        throw new UnsupportedOperationException("Unsupported this Class:" + aClass.getName());
    }
}
