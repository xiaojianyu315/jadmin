package org.xjy.jadmin.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.constant.AppConstant;
import org.xjy.jadmin.constant.DbConstant;
import org.xjy.jadmin.dto.JsonResult;
import org.xjy.jadmin.enums.ErrorEnum;
import org.xjy.jadmin.model.JadminMenu;
import org.xjy.jadmin.model.JadminProject;
import org.xjy.jadmin.model.JadminUser;
import org.xjy.jadmin.utils.MD5Util;
import org.xjy.jadmin.utils.ResultUtil;
import org.xjy.jadmin.validator.UserValidator;

import java.util.Date;
import java.util.List;

@Before(UserValidator.class)
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void init() {
        render("/pages/user/user-init.ftl");
    }

    /**
     * 用户列表
     *
     * @return
     */
    public void listInit() {
        JsonResult result = new JsonResult();
        try {
            List<JadminProject> projectList = JadminProject.dao.findProjectList();
            setAttr("projectList", projectList);
            render("/pages/user/user-list-init.ftl");
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
    }

    public void listData() {
        JsonResult result = new JsonResult();
        try {
            JadminUser user = getModel(JadminUser.class);
            Page<JadminUser> userPage = JadminUser.dao.findUserList(user.getProjectCode(), user.getUserName(), getPageNo(), 1);
            setAttr("userPage", userPage);
            keepModel(JadminUser.class);
            render("/pages/user/user-list-data.ftl");
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
    }

    /**
     * 跳转到添加页面
     */
    public void toAdd() {
        List<JadminProject> projectList = JadminProject.dao.findProjectList();
        setAttr("projectList", projectList);
        render("/pages/user/user-add.ftl");
    }

    /**
     * 跳转到修改页面
     */
    public void toUpdate() {
        String id = getPara("id");
        JadminUser user = JadminUser.dao.findById(Long.valueOf(id));
        List<JadminProject> projectList = JadminProject.dao.findProjectList();
        setAttr("user", user);
        setAttr("projectList", projectList);
        render("/pages/user/user-update.ftl");
    }

    /**
     * 用户添加
     */
    public void add() {
        JsonResult result = new JsonResult();
        try {
            JadminUser user = getModel(JadminUser.class);
            JadminUser userDB = JadminUser.dao.findUser(user.getProjectCode(), user.getUserName());
            if (userDB != null) {
                ResultUtil.error(result, ErrorEnum.ERR_USER_EXIST);
                renderJson(result);
                return;
            }
            user.setPassWord(MD5Util.encode(user.getPassWord()));
            user.setUpdateTime(new Date());
            user.setCreateTime(new Date());
            user.setIsEnable(1);
            user.setMenuIds("," + user.getMenuIds() + ",");
            user.save();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 用户修改
     */
    public void update() {
        JsonResult result = new JsonResult();
        try {
            JadminUser user = getModel(JadminUser.class);
            user.setUpdateTime(new Date());
            user.setCreateTime(new Date());
            user.update();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 跳转到修改密码页面
     */
    public void toChangePwd() {
        String id = getPara("id");
        setAttr("id", id);
        render("/pages/user/user-change-pwd.ftl");
    }

    /**
     * 修改密码
     */
    public void changePwd() {
        JsonResult result = new JsonResult();
        String userId = getPara("userId");
        String passwordOld = getPara("passwordOld");
        String passwordOldNew = getPara("passwordOldNew");

        try {
            JadminUser user = JadminUser.dao.findById(Long.parseLong(userId));
            String passWord = user.getPassWord();
            if (!passWord.equals(MD5Util.encode(passwordOld))) {
                ResultUtil.error(result, ErrorEnum.ERR_PASSWORD);
                renderJson(result);
                return;
            }
            user.setUpdateTime(new Date());
            user.setPassWord(MD5Util.encode(passwordOldNew));
            user.update();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 删除用户
     */
    public void remove() {
        JsonResult result = new JsonResult();
        try {
            String id = getPara("id");
            JadminUser user = new JadminUser();
            user.setId(Long.parseLong(id));
            user.setIsEnable(0);
            user.setUpdateTime(new Date());
            user.update();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }
}


