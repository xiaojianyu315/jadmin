package org.xjy.jadmin.auth.config;

import org.xjy.jadmin.auth.model.CoreMappingKit;
import org.xjy.jadmin.common.utils.PropUtil;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * Created by xiaojianyu on 17/3/21.
 */
public class DataSourceInit {
    /**
     * 初始化数据源
     *
     * @param url
     * @param user
     * @param pwd
     * @param dbName
     * @param me
     */
    public static DataSource initDbSource(String url, String user, String pwd,
                                          String dbName, Plugins me) {
        DruidPlugin druidPlugin = new DruidPlugin(url, user, pwd);
        //druidPlugin.setFilters("stat,wall");
        // 初始化连接池数量
        druidPlugin.setInitialSize(1);
        // 最小数量
        druidPlugin.setMinIdle(1);
        // 最大数量
        druidPlugin.setMaxActive(30);
        // 配置获取连接等待超时的时间
        druidPlugin.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidPlugin.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidPlugin.setMinEvictableIdleTimeMillis(300000);
        // 是否开启强制关闭连接
        druidPlugin.setRemoveAbandoned(true);
        // 10分钟未close则强制关闭
        druidPlugin.setRemoveAbandonedTimeoutMillis(10 * (1000 * 60));
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dbName, druidPlugin);
        CoreMappingKit.mapping(arp);
        arp.setShowSql(Boolean.valueOf(PropUtil.getValue("showSql")));
        me.add(arp);
        return druidPlugin.getDataSource();
    }
}
