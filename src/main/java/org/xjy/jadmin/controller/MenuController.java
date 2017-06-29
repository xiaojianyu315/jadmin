package org.xjy.jadmin.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.constant.DbConstant;
import org.xjy.jadmin.dto.JsonResult;
import org.xjy.jadmin.enums.ErrorEnum;
import org.xjy.jadmin.model.JadminMenu;
import org.xjy.jadmin.model.JadminProject;
import org.xjy.jadmin.model.JadminUser;
import org.xjy.jadmin.utils.ResultUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 菜单树列表
     *
     * @return
     */
    public void list() {
        try {
            List<JadminProject> projectList = JadminProject.dao.findProjectList();
            setAttr("projectList", projectList);
            render("/pages/menu/menu-list.ftl");
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
        }
    }

    public void queryMenuList() {
        JsonResult result = new JsonResult();
        try {
            String projectCode = getPara("projectCode");
            List<JadminMenu> menuList = JadminMenu.dao.findByProjectCode(projectCode);
            renderJson(menuList);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("menuList", menuList);
            ResultUtil.success(result, data);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }


    /**
     * 菜单详细
     *
     * @return
     */
    public void detail() {
        try {
            JadminMenu menu = JadminMenu.dao.findById(getParaToLong("id"));
            JadminProject project = JadminProject.dao.findByCode(menu.getProjectCode());
            setAttr("menu", menu);
            setAttr("project", project);
            render("/pages/menu/menu-update.ftl");
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
        }
    }


    /**
     * 跳转到菜单页面
     *
     * @return
     */
    public void toAdd() {
        JadminMenu menu = JadminMenu.dao.findById(getParaToLong("id"));
        JadminProject project = JadminProject.dao.findByCode(menu.getProjectCode());
        setAttr("menu", menu);
        setAttr("project", project);
        render("/pages/menu/menu-add.ftl");
    }

    /**
     * 跳转到菜单页面
     *
     * @return
     */
    public void add() {
        JsonResult result = new JsonResult();
        try {
            JadminMenu jadminMenu = getModel(JadminMenu.class);
            List<JadminMenu> jadminMenus = JadminMenu.dao.findByProjectCode(jadminMenu.getProjectCode());
            if (CollectionUtil.isNotEmpty(jadminMenus)) {
                for (JadminMenu menu : jadminMenus) {
                    if (menu.getUrl().equals(jadminMenu.getUrl())) {
                        ResultUtil.error(result, ErrorEnum.ERR_PROJECT_URL_EXIST);
                        renderJson(result);
                        return;
                    }
                    if (menu.getCode().equals(jadminMenu.getCode())) {
                        ResultUtil.error(result, ErrorEnum.ERR_PROJECT_CODE_EXIST);
                        renderJson(result);
                        return;
                    }
                }
            }

            jadminMenu.setIsEnable(DbConstant.INT_TRUE);
            jadminMenu.setCreateTime(new Date());
            jadminMenu.setUpdateTime(new Date());
            jadminMenu.save();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 修改菜单
     *
     * @return
     */
    public void update() {
        JsonResult result = new JsonResult();
        try {
            int isEnable = "on".equals(getPara("isEnable")) ? 1 : 0;
            JadminMenu jadminMenu = getModel(JadminMenu.class);
            jadminMenu.setUpdateTime(new Date());
            jadminMenu.setIsEnable(isEnable);
            jadminMenu.update();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }


    /**
     * 删除菜单
     *
     * @return
     */
    public void remove() {
        JsonResult result = new JsonResult();
        final Long id = getParaToLong("id");
        try {
            boolean tx = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    // 设置菜单为无效
                    JadminMenu menu = JadminMenu.dao.findById(id);
                    menu.setIsEnable(DbConstant.INT_FALSE);
                    menu.setUpdateTime(new Date());
                    menu.update();

                    // 获取用户表中绑定了这个菜单id的用户，然后移除

                    JadminUser.removeMenuId(id);
                    return true;
                }
            });

            if (tx) {
                ResultUtil.success(result);
            } else {
                ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
            }
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }



}


