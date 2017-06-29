package org.xjy.jadmin.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

@SuppressWarnings({"rawtypes"})
public class JsonUtil {

    /**
     * 将java类型的对象转换为JSON格式的字符串
     *
     * @param object java类型的对象
     * @return JSON格式的字符串
     */
    public static <T> String toJson(T object) {
        return JSON.toJSONString(object);
    }


    /**
     * 将JSON格式的字符串转换成任意Java类型的对象
     *
     * @param json  JSON格式的字符串
     * @param clazz 任意Java类型
     * @return 任意Java类型的对象
     */

    public static <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("key1", 1);

        String jsonStr = JsonUtil.toJson(map);
        System.out.println(jsonStr);
        System.out.println("=======================");
        System.out.println(JsonUtil.toObject(jsonStr, HashMap.class));

    }
}
