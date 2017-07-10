package org.xjy.jadmin.auth.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.common.utils.RequestUtil;

/**
 * Created by xiaojianyu on 17/3/26.
 */
public class ParamPrintInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(ParamPrintInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String actionKey = inv.getActionKey();
        // 获取到请求方法和所有请求参数及参数值，并记录日志，便于排查问题
        logger.error("input ==> method name = " + actionKey + "; params = " + RequestUtil.getParamMap(controller.getRequest()));
        inv.invoke();
    }
}
