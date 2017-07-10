package org.xjy.jadmin.common.utils;

import com.xiaoleilu.hutool.util.StrUtil;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 配置文件的基本操作
 */
public class PropUtil {
    private static final Logger logger = Logger.getLogger(PropUtil.class);
    private static PropUtil instance = null;
    private PropertiesConfiguration config = null;

    private static synchronized PropUtil getInstatance() {
        if (instance == null) {
            instance = new PropUtil();
        }
        return instance;
    }

    private PropUtil() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            config = new PropertiesConfiguration("config.properties");
            FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
            config.setReloadingStrategy(strategy);
            config.setEncoding("UTF-8");
            config.addConfigurationListener(new ConfigurationListener() {
                public void configurationChanged(ConfigurationEvent e) {
                    if (PropertiesConfiguration.EVENT_RELOAD == e.getType()) {
                        logger.error("config.properties load success ");
                    }
                }
            });
        } catch (Exception e) {
            logger.error("读取Properties文件异常 " + e);
        }
    }

    /**
     * 根据key获取对应value
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        String[] valueArr = getInstatance().config.getStringArray(key);
        return StringUtils.join(valueArr, ",");
    }

    /**
     * 根据key获取对应value，如果为空则返回默认的value
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue) {
        String[] valueArr = getInstatance().config.getStringArray(key);
        String value = StringUtils.join(valueArr, ",");
        return StrUtil.isEmpty(value) ? defaultValue : value;
    }

    public static void main(String[] args) {
        System.out.println(PropUtil.getValue("devMode"));
    }
}
