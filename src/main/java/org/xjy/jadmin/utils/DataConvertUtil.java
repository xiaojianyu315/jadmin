package org.xjy.jadmin.utils;

import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.xjy.jadmin.dto.MenuDTO;
import org.xjy.jadmin.dto.ProjectDTO;
import org.xjy.jadmin.dto.UserDTO;
import org.xjy.jadmin.model.JadminMenu;
import org.xjy.jadmin.model.JadminProject;
import org.xjy.jadmin.model.JadminUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaojianyu on 17/5/9.
 */
public class DataConvertUtil {

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
