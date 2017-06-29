package org.xjy.jadmin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.constant.AppConstant;
import org.xjy.jadmin.dto.MenuDTO;
import org.xjy.jadmin.dto.ProjectDTO;
import org.xjy.jadmin.dto.UserDTO;

import java.util.List;

/**
 * Created by xiaojianyu on 17/3/26.
 */
public class PermissionsInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PermissionsInterceptor.class);


    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String actionKey = inv.getActionKey();
        String[] igUrls = AppConstant.PIGNORE_CHECK_PERMISSIONS_URL.split(",");
        for (String igUrl : igUrls) {
            if (actionKey.equals(igUrl)) {
                inv.invoke();
                return;
            }
        }

        String loginFlag = (String) controller.getSession().getAttribute("loginFlag");

        if (StrUtil.isBlank(loginFlag)|| !"true".equals(loginFlag)) {//判断用户是否登录
            controller.forwardAction("/login");// 为登录时跳转回登录页面
            return;
        }
        inv.invoke();
    }
}
