package org.xjy.jadmin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.xjy.jadmin.utils.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaojianyu on 17/3/26.
 */
public class CORSInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(CORSInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        controller.getResponse().setHeader("Access-Control-Allow-Origin", PropUtil.getValue("cors.allow.list"));
//        if ("OPTIONS".equals(controller.getRequest().getMethod()))
//        {
//            controller.getResponse().setHeader("Access-Control-Allow-Methods", "*");
//            controller.getResponse().setHeader("Access-Control-Allow-Headers", "*");
//        }else{
            controller.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET");
            controller.getResponse().setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with");
//        }
        controller.getResponse().setHeader("Access-Control-Max-Age", "3600");
        inv.invoke();
    }
}
