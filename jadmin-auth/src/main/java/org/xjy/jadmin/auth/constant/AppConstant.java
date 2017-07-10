package org.xjy.jadmin.auth.constant;

import org.xjy.jadmin.common.utils.PropUtil;

/**
 * Created by haihao on  2016/7/9.
 */
public class AppConstant {

    public static final int PAGE_SIZE = 20;

    public static final String ENCODING = "UTF-8";
    public static final boolean DEV_MODE = new Boolean(PropUtil.getValue("devMode"));

    public static final String CONTEXT_PATH_NAME = "ctx";
    public static final String PUBLISH_URL = PropUtil.getValue("publish.url");

    public static final String PROJECT_CODE = PropUtil.getValue("project.code");
    public static final String PROJECT_KEY = PropUtil.getValue("project.key");
    public static final String PIGNORE_CHECK_PERMISSIONS_URL = PropUtil.getValue("ignore.check.permissions.url");
    public static final String USERNAME = PropUtil.getValue("username");
    public static final String PASSWORD = PropUtil.getValue("password");


}
