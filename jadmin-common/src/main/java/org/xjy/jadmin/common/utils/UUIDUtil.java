package org.xjy.jadmin.common.utils;


import java.util.UUID;

@SuppressWarnings({"rawtypes"})
public class UUIDUtil {

    private UUIDUtil() {
    }

    public static String get() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
