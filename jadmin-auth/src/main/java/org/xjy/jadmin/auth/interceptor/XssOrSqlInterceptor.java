package org.xjy.jadmin.auth.interceptor;

import org.xjy.jadmin.auth.dto.JsonResult;
import org.xjy.jadmin.auth.enums.ErrorEnum;
import org.xjy.jadmin.common.utils.RequestUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by xiaojianyu on 17/3/26.
 */
public class XssOrSqlInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(XssOrSqlInterceptor.class);
    private String sqlKeyStr = "union|all|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|or|create|case|if|drop|show";

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        // 防止SQL注入,暂不使用
        Map paraMap = RequestUtil.getParamMap(controller.getRequest());
        if (checkSqlInject(paraMap)) {
            renderError(controller);
            return;
        }
        inv.invoke();
    }


    /**
     * token失效，返回信息
     *
     * @param controller
     */
    private void renderError(Controller controller) {
        JsonResult rj = new JsonResult();
        rj.setCode(ErrorEnum.ERR_PARAM.getCode());
        rj.setMessage(ErrorEnum.ERR_PARAM.getMsg());
        controller.renderJson(rj);
    }
    /**
     * 判断传递的参数是否有sql关键词
     *
     * @param paraMap 请求参数集合参数
     * @return boolean
     */
    public boolean checkSqlInject(Map paraMap) {
        String[] sqlKeyArray = sqlKeyStr.split("\\|");
        for (Object param : paraMap.keySet()) {
            String inputParam = String.valueOf(paraMap.get(param));

            for (String sqlKey : sqlKeyArray) {
                String compareString = sqlKey + " ";
                if (inputParam.toLowerCase().contains(compareString)) {
                    logger.info("SQL注入异常数据：param = " + paraMap.toString());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkXSSInject(String inputParam) {
        if (inputParam.indexOf("<") > -1 || inputParam.indexOf(">") > -1) {
            return true;
        }
        return false;
    }
}
