package org.xjy.jadmin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.constant.AppConstant;
import org.xjy.jadmin.dto.JsonResult;
import org.xjy.jadmin.enums.ErrorEnum;
import org.xjy.jadmin.service.UserService;
import org.xjy.jadmin.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService = new UserService();
    private static final String projectCode = AppConstant.PROJECT_CODE;
    private static final String projectKey = AppConstant.PROJECT_KEY;

    /**
     * 本地项目登录
     *
     * @return
     */

    public void index() {
        render("/pages/login.ftl");
    }

    public void doLogin() {
        JsonResult result = new JsonResult();
        try {
            String username = getPara("username");
            String passowrd = getPara("password");

            if(!AppConstant.USERNAME.equals(username)){
                ResultUtil.error(result,ErrorEnum.ERR_USER_NO_EXIST);
                renderJson(result);
                return;
            }
            if(!AppConstant.PASSWORD.equals(passowrd)){
                ResultUtil.error(result,ErrorEnum.ERR_PASSWORD);
                renderJson(result);
                return;
            }
            getSession().setAttribute("loginFlag","true");
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("登录异常", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    public void logout() {
        getSession().removeAttribute("loginFlag");
        render("/pages/login.ftl");
    }

}


