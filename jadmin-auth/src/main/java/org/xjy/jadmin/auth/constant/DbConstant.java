package org.xjy.jadmin.auth.constant;

import org.xjy.jadmin.common.utils.PropUtil;

/**
 * Created by zhangjie on 16/6/20.
 */
public class DbConstant {

    /**
     * jadmin
     */
    public static final String DATA_SOURCE_JADMIN = "jadmin";
    public static final String JADMIN_URL = PropUtil.getValue("jadmin.url");
    public static final String JADMIN_USERNAME = PropUtil.getValue("jadmin.username");
    public static final String JADMIN_PASSWORD = PropUtil.getValue("jadmin.password");

    public static final int INT_TRUE = 1;
    public static final int INT_FALSE = 0;



}
