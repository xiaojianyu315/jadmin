package org.xjy.jadmin.dto;

import org.xjy.jadmin.enums.ErrorEnum;

import java.util.HashMap;
import java.util.Map;

public class JsonResult {
    private boolean isSuccess = false;
    private String code;
    private String message;
    private Map<String, Object> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if(ErrorEnum.SUCCESS.getCode().equals(code)){
            this.isSuccess = true;
        }
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data == null ? new HashMap<String, Object>() : data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return this.isSuccess;
    }

}
