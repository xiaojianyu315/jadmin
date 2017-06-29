package org.xjy.jadmin.enums;

/**
 * Created by xiaojianyu on 17/3/8.
 */
public enum ErrorEnum {

    SUCCESS("SUCCESS", "操作成功"),
    ERR_UNKNOWN("ERR_UNKNOWN", "未知错误"),
    ERR_PARAM("ERR_PARAM", "参数异常"),

    ERR_PROJECT_NO_EXIST("ERR_PROJECT_NO_EXIST", "项目不存在"),
    ERR_PROJECT_INVALID("ERR_PROJECT_INVALID", "项目无效"),
    ERR_PROJECT_KEY("ERR_PROJECT_KEY", "项目密钥错误"),
    ERR_PROJECT_URL_EXIST("ERR_URL_EXIST", "菜单路径已存在"),
    ERR_PROJECT_CODE_EXIST("ERR_CODE_EXIST", "菜单编码已存在"),

    ERR_USER_NO_EXIST("ERR_USER_NO_EXIST", "用户不存在"),
    ERR_USER_EXIST("ERR_USER_EXIST", "用户已存在"),
    ERR_USER_INVALID("ERR_USER_INVALID", "用户无效"),
    ERR_PASSWORD("ERR_PASSWORD", "密码错误"),

    ERR_MENU_PID_NO_DELETE("ERR_MENU_PID_NO_DELETE", "根菜单不能删除"),

    ;


    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
