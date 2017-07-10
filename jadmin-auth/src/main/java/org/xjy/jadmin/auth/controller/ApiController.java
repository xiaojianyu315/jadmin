package org.xjy.jadmin.auth.controller;

import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.auth.dto.JsonResult;
import org.xjy.jadmin.auth.dto.MenuDTO;
import org.xjy.jadmin.auth.enums.ErrorEnum;
import org.xjy.jadmin.auth.service.UserService;
import org.xjy.jadmin.auth.dto.ResultUtil;

import java.util.List;


public class ApiController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    private UserService userService = new UserService();
    /**
     * 登录
     *
     * @return
     */
    public void login() {
        JsonResult result = new JsonResult();
        try {
            String projectCode = getPara("projectCode");
            String secretKey = getPara("secretKey");
            String userName = getPara("userName");
            String passWord = getPara("passWord");
            result = userService.login(projectCode, secretKey, userName, passWord);
        } catch (Exception e) {
            logger.error("登录异常", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 检查是否有权限
     */
    public void checkPermissions() {
        JsonResult jsonResult = new JsonResult();
        try {
            String projectCode = getPara("projectCode");
            String secretKey = getPara("secretKey");
            String userName = getPara("userName");
            String passWord = getPara("passWord");
            String menuUrl = getPara("menuUrl");
            JsonResult result = userService.login(projectCode, secretKey, userName, passWord);
            if(result.isSuccess()){
                List<MenuDTO> menuList = (List<MenuDTO>) result.getData().get("menuList");
                if(CollectionUtil.isNotEmpty(menuList)){
                    for(MenuDTO menuDTO : menuList){
                        if(menuDTO.getUrl().equals(menuUrl)){
                            ResultUtil.success(jsonResult);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("验证权限异常", e);
            ResultUtil.error(jsonResult, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(jsonResult);
    }
}


