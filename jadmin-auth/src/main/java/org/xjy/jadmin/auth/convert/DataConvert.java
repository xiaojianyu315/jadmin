package org.xjy.jadmin.auth.convert;

import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.xjy.jadmin.auth.dto.MenuDTO;
import org.xjy.jadmin.auth.dto.ProjectDTO;
import org.xjy.jadmin.auth.dto.UserDTO;
import org.xjy.jadmin.auth.model.JadminMenu;
import org.xjy.jadmin.auth.model.JadminProject;
import org.xjy.jadmin.auth.model.JadminUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaojianyu on 17/5/9.
 */
public class DataConvert {

    public static ProjectDTO convert(JadminProject project) {
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtil.copyProperties(projectDTO, project);
        return projectDTO;
    }

    public static UserDTO convert(JadminUser user) {
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);
        return userDTO;
    }

    public static List<MenuDTO> convert(List<JadminMenu> menuList) {
        List<MenuDTO> mList = new ArrayList<MenuDTO>();
        if(CollectionUtil.isNotEmpty(menuList)){
            MenuDTO menuDTO = null;
            for(JadminMenu menu : menuList){
                menuDTO = new MenuDTO();
                BeanUtil.copyProperties(menu, menuDTO);
                mList.add(menuDTO);
            }
        }
        return mList;
    }
}
