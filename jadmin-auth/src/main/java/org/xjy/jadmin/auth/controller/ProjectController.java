package org.xjy.jadmin.auth.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xjy.jadmin.auth.constant.AppConstant;
import org.xjy.jadmin.auth.constant.DbConstant;
import org.xjy.jadmin.auth.dto.JsonResult;
import org.xjy.jadmin.auth.enums.ErrorEnum;
import org.xjy.jadmin.auth.model.JadminMenu;
import org.xjy.jadmin.auth.model.JadminProject;
import org.xjy.jadmin.auth.dto.ResultUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class ProjectController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    public void init() {
        render("/pages/project/project-init.ftl");
    }

    /**
     * 项目列表
     *
     * @return
     */
    public void listInit() {
        render("/pages/project/project-list-init.ftl");
    }

    /**
     * 项目列表
     *
     * @return
     */
    public void listData() {
        try {
            JadminProject project = getModel(JadminProject.class);
            Page<JadminProject> projectPage = JadminProject.dao.findProjectPage(project.getCode(), getPageNo(), AppConstant.PAGE_SIZE);
            List<JadminProject> projectList = JadminProject.dao.findProjectList();
            setAttr("projectPage", projectPage);
            setAttr("projectList", projectList);
            render("/pages/project/project-list-data.ftl");
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
        }
    }

    /**
     * 跳转到添加页面
     */
    public void toAdd() {
        render("/pages/project/project-add.ftl");
    }

    /**
     * 跳转到修改页面
     */
    public void toUpdate() {
        JadminProject project = JadminProject.dao.findById(getParaToLong("id"));
        setAttr("project", project);
        render("/pages/project/project-update.ftl");
    }

    /**
     * 项目添加
     */
    public void add() {
        JsonResult result = new JsonResult();
        try {
            boolean tx = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    JadminProject project = getModel(JadminProject.class);
                    project.setUpdateTime(new Date());
                    project.setCreateTime(new Date());
                    project.setIsEnable(1);
                    boolean saveProject = project.save();

                    // 默认添加一个系统菜单的根菜单
                    JadminMenu menu = new JadminMenu();
                    menu.setCode("CDGL");
                    menu.setName("菜单管理");
                    menu.setLevel(0);
                    menu.setSort(1);
                    menu.setCreateTime(new Date());
                    menu.setUpdateTime(new Date());
                    menu.setProjectCode(project.getCode());
                    menu.setType(0);
                    menu.setUrl("--");
                    menu.setPid(0L);
                    boolean saveMenu = menu.save();
                    return saveProject && saveMenu;
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

    /**
     * 项目修改
     * 修改项目code的同时，把菜单表中的老项目code也替换成新的
     */
    public void update() {
        JsonResult result = new JsonResult();
        try {
            JadminProject project = getModel(JadminProject.class);
            JadminProject projectDB = JadminProject.dao.findById(project.getId());

            List<JadminMenu> menuList = JadminMenu.dao.findByProjectCode(projectDB.getCode());
            if (CollectionUtil.isNotEmpty(menuList)) {
                for (JadminMenu menu : menuList) {
                    menu.setProjectCode(project.getCode());
                    menu.setUpdateTime(new Date());
                    menu.update();
                }
            }

            project.setUpdateTime(new Date());
            project.update();
            ResultUtil.success(result);
        } catch (Exception e) {
            logger.error("UNKNOWN ERROR.", e);
            ResultUtil.error(result, ErrorEnum.ERR_UNKNOWN);
        }
        renderJson(result);
    }

    /**
     * 项目用户
     */
    public void remove() {
        JsonResult result = new JsonResult();
        try {
            JadminProject user = new JadminProject();
            user.setId(getParaToLong("id"));
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


