package org.xjy.jadmin.dto.resp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.xjy.jadmin.dto.MenuDTO;
import org.xjy.jadmin.dto.ProjectDTO;
import org.xjy.jadmin.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 用户审核
 * Created by xiaojianyu on 17/3/29.
 */
public class RespLoginDTO implements Serializable {

    private static final long serialVersionUID = -8699145142511344061L;

    private ProjectDTO project;
    private UserDTO user;
    private List<MenuDTO> menuList;

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
