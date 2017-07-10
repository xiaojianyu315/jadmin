package org.xjy.jadmin.auth.generator;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MappingKitGenerator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * MyGenerator
 */
public class MyGenerator {


    public static DataSource getDataSource(String url, String username, String pwd) {
        DruidPlugin druidPlugin = new DruidPlugin(url, username, pwd);
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void gen() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/jadmin";
        String username = "root";
        String pwd = "root";
        DataSource dataSource = getDataSource(jdbcUrl, username, pwd);

        String baseModelPackageName = "org.xjy.jadmin.auth.model.base";
        String baseModelOutputDir = PathKit.getWebRootPath() + "/db/generator/model/base/";
        String modelPackageName = "org.xjy.jadmin.auth.model";
        String modelOutputDir = PathKit.getWebRootPath() + "/db/generator/model/";
        String mappingKitPackageName = "org.xjy.jadmin.auth.model";
        String mappingKitOutputDir = PathKit.getWebRootPath() + "/db/generator/model/";
        String mappingKitClassName = "CoreMappingKit";
        //String dictOutputDir = PathKit.getWebRootPath() + "/db";
        //String dictFileName = "ent_core_table.txt";

        MappingKitGenerator mkg = new MappingKitGenerator(mappingKitPackageName, mappingKitOutputDir);
        mkg.setMappingKitClassName(mappingKitClassName);

        //DataDictionaryGenerator gdd = new DataDictionaryGenerator(dataSource, dictOutputDir);
        //gdd.setDataDictionaryFileName(dictFileName);

        Generator gernerator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        gernerator.setMappingKitGenerator(mkg);
        // 设置数据库方言
        gernerator.setDialect(new MysqlDialect());
        // 添加不需要生成的表名
//		gernerator.addExcludedTable("adv");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
//        gernerator.setGenerateDataDictionary(false);
//        gernerator.setDataDictionaryGenerator(gdd);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//		gernerator.setRemovedTableNamePrefixes("t_");
        // 生成
        gernerator.generate();
    }




    public static void main(String[] args) {
        gen();
    }
}




