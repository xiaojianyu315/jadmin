package org.xjy.jadmin.auth.model;

import org.xjy.jadmin.auth.model.base.BaseJadminMenu;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class JadminMenu extends BaseJadminMenu<JadminMenu> {
    public static final JadminMenu dao = new JadminMenu().dao();

    public List<JadminMenu> findByIds(String menuIds) {
        return dao.find("select * from jadmin_menu where menuIds in (" + menuIds + ")");
    }

    public List<JadminMenu> findList() {
        return dao.find("select * from jadmin_menu ");
    }

    public List<JadminMenu> findByProjectCode(String projectCode) {
        return dao.find("select * from jadmin_menu where projectCode = ?", projectCode);
    }
}
