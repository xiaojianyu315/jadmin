package org.xjy.jadmin.auth.config;

import com.jfinal.config.*;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import org.apache.log4j.Logger;
import org.xjy.jadmin.auth.constant.AppConstant;
import org.xjy.jadmin.auth.constant.DbConstant;
import org.xjy.jadmin.auth.controller.*;
import org.xjy.jadmin.auth.handler.FreeMarkerHandler;
import org.xjy.jadmin.auth.interceptor.ParamPrintInterceptor;
import org.xjy.jadmin.auth.interceptor.PermissionsInterceptor;
import org.xjy.jadmin.auth.interceptor.XssOrSqlInterceptor;

import javax.sql.DataSource;

/**
 * 核心配置文件
 */
public class MainConfig extends JFinalConfig {

    private static final Logger logger = Logger.getLogger(MainConfig.class);

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        me.setDevMode(AppConstant.DEV_MODE);
        me.setEncoding(AppConstant.ENCODING);
        me.setViewType(ViewType.FREE_MARKER);
        me.setError404View("/pages/common/404.ftl");
        me.setError500View("/pages/common/500.ftl");

    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add("/api" , ApiController.class);
        me.add("/" , LoginController.class);
        me.add("/login" , LoginController.class);
        me.add("/index" , IndexController.class);
        me.add("/user" , UserController.class);
        me.add("/menu" , MenuController.class);
        me.add("/project" , ProjectController.class);
    }

    public void configEngine(Engine engine) {

    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {

        // 数据源插件
        DataSource dataSource = DataSourceInit.initDbSource(
                DbConstant.JADMIN_URL,
                DbConstant.JADMIN_USERNAME,
                DbConstant.JADMIN_PASSWORD,
                DbConstant.DATA_SOURCE_JADMIN,
                me);

        // spring插件
//		me.add(new SpringPlugin("classpath*:applicationContext.xml"));
    }

    /**
     * 配置全局拦截
     */
    public void configInterceptor(Interceptors me) {
        me.add(new ParamPrintInterceptor());
        me.add(new PermissionsInterceptor());
        me.add(new XssOrSqlInterceptor());
        logger.info("拦截器启动完毕");
    }

    /**
     * 配置处理
     */
    public void configHandler(Handlers me) {
        me.add(new FreeMarkerHandler());
        logger.info("配置初始化完毕");

    }


}
