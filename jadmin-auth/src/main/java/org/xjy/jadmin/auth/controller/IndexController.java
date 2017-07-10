package org.xjy.jadmin.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndexController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 登录
     *
     * @return
     */
    public void index() {

        render("/pages/index.ftl");
    }



}


