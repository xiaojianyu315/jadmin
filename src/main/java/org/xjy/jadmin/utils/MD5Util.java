package org.xjy.jadmin.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String key="7a2516327e42205564b1b0d3c16fe255";

    public static String encode(String origin, String charsetname) {
        String resultString = null;
        resultString = new String(origin + key);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (charsetname == null || "".equals(charsetname)) {
            resultString = Hex.encodeHexString(md.digest(resultString.getBytes()));
        } else {
            try {
                resultString = Hex.encodeHexString(md.digest(resultString.getBytes(charsetname)));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return resultString;
    }

    public static String encode(String origin) {
        return MD5Util.encode(origin, "utf-8");
    }

    public static void main(String[] args) {

        String s = ",2,3,4,";
        System.out.println(s.substring(1,s.length()-1));
    }

}