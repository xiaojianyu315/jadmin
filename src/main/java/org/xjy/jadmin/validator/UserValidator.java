package org.xjy.jadmin.validator;

import org.xjy.jadmin.enums.ErrorEnum;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.xiaoleilu.hutool.util.StrUtil;
import org.xjy.jadmin.model.JadminUser;

/**
 * Created by xiaojianyu on 17/5/2.
 */
public class UserValidator extends Validator {

    protected void validate(Controller c) {
        if (getActionKey().equals("/user/add")){
            JadminUser user = c.getModel(JadminUser.class);
            if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPassWord())) {
                addError("errorMsg", ErrorEnum.ERR_PARAM.getMsg());
                addError("errorCode", ErrorEnum.ERR_PARAM.getCode()+"");
                return;
            }

        }else if(getActionKey().equals("/user/update")){

        }



    }

    protected void handleError(Controller c) {
        if (getActionKey().equals("/user/save")){

        }else if(getActionKey().equals("/user/update")){

        }
    }
}