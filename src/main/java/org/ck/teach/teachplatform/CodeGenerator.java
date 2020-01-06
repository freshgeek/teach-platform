package org.ck.teach.teachplatform;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.Request;
import org.ck.teach.teachplatform.common.Response;

import java.util.*;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/11/20 17:21
 * @description
 */
public class CodeGenerator {
    public static String uncapFirst(String str) {
        if (str.length() > 0) {
            return String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1);
        } else {
            return str;
        }
    }

    public static void main(String[] args) {
   //     assert (false) : "代码生成属于危险操作，请确定配置后取消断言执行代码生成！";
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("cc \n    qq: 3570632401  " + "\n     淘宝链接:[https://m.tb.cn/h.ewA2rg5?sm=60123d]");
        gc.setAuthor("");
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        // 是否覆盖同名文件，默认是false
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDaoMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://192.168.35.3:3306/ck_db");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix("t_");
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
         strategy.setInclude("t_contest"); // 需要生成的表
        strategy.setSkipView(false);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass(Request.class.getCanonicalName());
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass(BaseController.class.getCanonicalName());
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityLombokModel(true);
        strategy.setVersionFieldName(new Date() + "");
        strategy.setRestControllerStyle(true);
        strategy.setEntitySerialVersionUID(true);

        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(CodeGenerator.class.getPackage().getName());
        // pc.setModuleName("test");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc","-mp");
                map.put("rspPackage", Response.class.getName());
                setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();
        // 列表生成
        focList.add(new FileOutConfig("/templates/html/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir") + "/src/main/resources/static/admin/" + uncapFirst(tableInfo.getEntityName()) + "/" + uncapFirst(tableInfo.getEntityName()) + "List.htm";
            }
        });
        //列表js生成
        focList.add(new FileOutConfig("/templates/html/list.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir") + "/src/main/resources/static/admin/" + uncapFirst(tableInfo.getEntityName()) + "/" + uncapFirst(tableInfo.getEntityName()) + "List.js";
            }
        });

        //增加页面生成
        focList.add(new FileOutConfig("/templates/html/add.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir") + "/src/main/resources/static/admin/" + uncapFirst(tableInfo.getEntityName()) + "/" + uncapFirst(tableInfo.getEntityName()) + "Add.htm";
            }
        });
        //修改页面生成
        focList.add(new FileOutConfig("/templates/html/edit.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir") + "/src/main/resources/static/admin/" + uncapFirst(tableInfo.getEntityName()) + "/" + uncapFirst(tableInfo.getEntityName()) + "Edit.htm";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        //
        // // 调整 xml 生成目录演示
        // focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
        // @Override
        // public String outputFile(TableInfo tableInfo) {
        // return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
        // }
        // });
        // cfg.setFileOutConfigList(focList);
        // mpg.setCfg(cfg);
        //
        // // 关闭默认 xml 生成，调整生成 至 根目录
        // TemplateConfig tc = new TemplateConfig();
        // tc.setXml(null);
        // mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        //TemplateConfig tc = new TemplateConfig();
        //  tc.setController("templates/controller.java.ftl");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        //    mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        // System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
