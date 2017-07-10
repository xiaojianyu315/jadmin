package org.xjy.jadmin.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.auth.dto.JsonResult;
import org.xjy.jadmin.auth.enums.ErrorEnum;
import org.xjy.jadmin.auth.model.JadminMenu;
import org.xjy.jadmin.auth.model.JadminProject;
import org.xjy.jadmin.auth.model.JadminUser;
import org.xjy.jadmin.auth.convert.DataConvert;
import org.xjy.jadmin.common.utils.MD5Util;
import org.xjy.jadmin.auth.dto.ResultUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaojianyu on 17/4/28.
 */
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public JsonResult login(String projectCode, String secretKey, String username, String password) {
        JsonResult result = new JsonResult();

        // 验证项目
        JadminProject project = JadminProject.dao.findByCode(projectCode);
        JsonResult checkProject = checkProject(project, projectCode, secretKey);
        if (!ErrorEnum.SUCCESS.getCode().equals(checkProject.getCode())) {
            return checkProject;
        }

        // 验证用户
        JadminUser user = JadminUser.dao.findUser(projectCode, username);
        JsonResult checkUser = checkUser(user, projectCode, username, password);
        if (!ErrorEnum.SUCCESS.getCode().equals(checkUser.getCode())) {
            return checkUser;
        }

        // 获取菜单
        List<JadminMenu> menuList = JadminMenu.dao.findByIds(user.getMenuIds().substring(1,user.getMenuIds().length()-1));
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("project", DataConvert.convert(project));
        dataMap.put("user", DataConvert.convert(user));
        dataMap.put("menuList", DataConvert.convert(menuList));
        return ResultUtil.success(result, dataMap);
    }

    private JsonResult checkProject(JadminProject project, String projectCode, String secretKey) {
        JsonResult result = new JsonResult();
        if (project == null) {
            return ResultUtil.error(result,ErrorEnum.ERR_PROJECT_NO_EXIST);
        }
        int isEnable = project.getIsEnable();
        if (isEnable == 0) {
            return ResultUtil.error(result,ErrorEnum.ERR_PROJECT_INVALID);
        }
        String key = project.getSecretKey();
        if (!key.equals(secretKey)) {
            return ResultUtil.error(result,ErrorEnum.ERR_PROJECT_KEY);
        }
        return ResultUtil.success(result);
    }


    private JsonResult checkUser(JadminUser user, String projectCode, String username, String password) {
        JsonResult result = new JsonResult();
        if (user == null) {
            return ResultUtil.error(result,ErrorEnum.ERR_USER_NO_EXIST);
        }
        String dbpwd = user.getPassWord();
        String inputPwd = MD5Util.encode(password);
        if (!dbpwd.equals(inputPwd)) {
            return ResultUtil.error(result,ErrorEnum.ERR_PASSWORD);
        }
        return ResultUtil.success(result);
    }
}
