package org.xjy.jadmin.auth.dto;


import org.xjy.jadmin.auth.dto.JsonResult;
import org.xjy.jadmin.auth.enums.ErrorEnum;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes"})
public class ResultUtil {
    private static String encoding = "UTF-8";

    private ResultUtil() {
    }

    public static JsonResult success(JsonResult ro) {
        ro.setCode(ErrorEnum.SUCCESS.getCode());
        ro.setMessage("操作成功");
        ro.setData(new HashMap<String, Object>());
        return ro;
    }

    public static JsonResult success(JsonResult ro, Map<String, Object> data) {
        ro.setCode(ErrorEnum.SUCCESS.getCode());
        ro.setMessage("操作成功");
        ro.setData(data);
        return ro;
    }

    public static JsonResult error(JsonResult ro, ErrorEnum e) {
        ro.setCode(e.getCode());
        ro.setMessage(e.getMsg());
        return ro;
    }


    public static JsonResult error(JsonResult ro, ErrorEnum e, Map<String, Object> data) {
        ro.setCode(e.getCode());
        ro.setMessage(e.getMsg());
        ro.setData(data);
        return ro;
    }


}
