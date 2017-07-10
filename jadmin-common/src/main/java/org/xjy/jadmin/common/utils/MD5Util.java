package org.xjy.jadmin.common.utils;

import com.xiaoleilu.hutool.crypto.SecureUtil;

public class MD5Util {
    private static final String key="7a2516327e42205564b1b0d3c16fe255";

    public static String encode(String str) {
        return SecureUtil.md5(str + key);
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("111111"));
    }


}