package com.mudxx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

/**
 * 代码自动生成器
 *
 * @author laiw
 * @date 2023/4/6 18:02
 */
public class Generator {
    /**
     * 数据库连接字段配置
     */
//    private static final String JDBC_URL = "jdbc:mysql://120.26.37.73:3306/mudxx-mall-learning?useUnicode=true&characterEncoding=utf-8";
//    private static final String JDBC_USER_NAME = "root";
//    private static final String JDBC_PASSWORD = "alimysql123456";

    private static final String JDBC_URL = "jdbc:mysql://rm-gs5q573fvex10s40x.mysql.singapore.rds.aliyuncs.com:3306/payment_system?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC&useSSL=false";
    private static final String JDBC_USER_NAME = "agilewingadmin";
    private static final String JDBC_PASSWORD = "fZ1etRWXUpTQk8VEmI";

    /**
     * 包名和模块名
     */
    private static final String PACKAGE_NAME = "com.mudxx";
    private static final String MODULE_NAME = "demo";

    /**
     * 表名,多个表使用英文逗号分割
     */
    private static final String[] TBL_NAMES = {"tb_test_order"};

    /**
     * 表名的前缀,从表生成代码时会去掉前缀
     */
    private static final String TABLE_PREFIX = "tb_";


    public static void main(String[] args) {

        //获取当前工程路径(这里无需修改)
//        String projectPath = System.getProperty("user.dir");
        String projectPath = "/Users/laiwen/Desktop/work/develop/workspace/mudxx/mall-learning-projects/mudxx-demo/mudxx-demo-boot-generator-plus";


        /**
         * 1.数据库配置(设置数据源)
         配置数据库连接以及需要使用的字段
         */
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(
                JDBC_URL,
                JDBC_USER_NAME,
                JDBC_PASSWORD)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());


        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder);


        /**
         * 2.全局配置
         */
        fastAutoGenerator.globalConfig(
                globalConfigBuilder -> globalConfigBuilder
                        .fileOverride()     // 覆盖已生成文件
                        .disableOpenDir()   // 不打开生成文件目录
                        .outputDir(projectPath + "/src/main/java") // 指定输出目录,注意斜杠的表示
                        .author("laiwen") // 设置注释的作者
                        .commentDate("yyyy-MM-dd HH:mm") // 设置注释的日期格式
                        .dateType(DateType.TIME_PACK)   // 使用java8新的时间类型
                        .enableSwagger()    // 开启swagger文档
        );

        /**
         日期类型 DateType
         DateType.ONLY_DATE 使用 java.util.date包下的 Date
         DateType.SQL_PACK 使用 java.sql包下的 Date
         DateType.TIME_PACK   因为会使用 java.time.LocalDateTime jdk1.8以上才支持  (推荐使用)
         */


        /**
         * 3.包配置
         */
        fastAutoGenerator.packageConfig(
                packageConfigBuilder -> packageConfigBuilder
                        .parent(PACKAGE_NAME)   // 设置父包名
                        .moduleName(MODULE_NAME) // 设置父包模块名
                        .entity("dao") // 设置MVC下各个模块的包名
                        .mapper("dao.mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .xml("dao.xml") // 设置XML资源文件的目录
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")) //路径配置信息,就是配置各个文件模板的路径信息,这里以mapper.xml为例
        );

        /**
         * 4.模板配置(第五部分会说明)
         */
        fastAutoGenerator.templateConfig(
                templateConfigBuilder -> templateConfigBuilder
                        .entity("/templates/yuan/entity.java")   // 设置实体模板路径(JAVA)
                        .mapper("/templates/yuan/mapper.java")   // 设置mapper模板路径
                        .service("/templates/yuan/service.java") // 设置service模板路径
                        .serviceImpl("/templates/yuan/serviceImpl.java") // 设置serviceImpl模板路径
                        .xml("/templates/yuan/mapper.xml")   // 设置mapperXml模板路径
                        .controller("/templates/yuan/controller.java")   // 设置controller模板路径
        );

        /**
         * 5.注入配置 TODO
         */
//        fastAutoGenerator.injectionConfig(builder -> {
//            Map<String, String> customFile = new HashMap<>(16);
//            customFile.put("Bo.java", "/templates/yuan/entityBO.java.vm");
//            builder.customFile(customFile);
//        });


        /**
         * 6.策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder
                        .enableCapitalMode()    // 开启大写命名
                        .enableSkipView()   // 开启跳过视图
                        .disableSqlFilter() // 禁用sql过滤
                        .addInclude(TBL_NAMES)  // 设置需要生成的表名
                        .addTablePrefix(TABLE_PREFIX)   // 设置过滤表前缀
        );


        /**
         * 6.1 Entity策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.entityBuilder()
                        .enableTableFieldAnnotation()   // 生成实体时生成字段的注解，包括@TableId注解等---
                        .naming(NamingStrategy.underline_to_camel)  // 数据库表和字段映射到实体的命名策略,为下划线转驼峰
                        .columnNaming(NamingStrategy.underline_to_camel)
//                        .idType(IdType.AUTO)    // 全局主键类型为AUTO(自增)
                        .idType(IdType.ASSIGN_UUID)
                        .enableLombok() // 支持lombok开启注解
                        .logicDeleteColumnName("deleted")   // 逻辑删除字段名(数据库)
                        .logicDeletePropertyName("deleted") // 逻辑删除属性名(实体)
//                        .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置  create_time  update_time 两种方式
//                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        .versionColumnName("version")   // 开启乐观锁
                        .disableSerialVersionUID()  // 禁用生成 serialVersionUID，默认值:true
                        .enableChainModel() // 开启实体类链式编程
                        .formatFileName("%s") // 实体名称格式化为XXXEntity   formatFileName("%sEntity")
        );

        /**
         * 6.2 Controller策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.controllerBuilder()
                        .enableRestStyle()  // 开启生成@RestController控制器
                        .enableHyphenStyle()    // 开启驼峰转连字符 localhost:8080/hello_id_2
        );

        /**
         * 6.3 Service策略配置
         格式化service接口和实现类的文件名称，去掉默认的ServiceName前面的I ----
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl"));

        /**
         * 6.4 Mapper策略配置
         格式化 mapper文件名,格式化xml实现类文件名称
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.mapperBuilder()
                        .enableMapperAnnotation()   // 开启 @Mapper 注解
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper"));

        /** 7.生成代码
         *
         */
        // fastAutoGenerator.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        fastAutoGenerator.execute();

    }
}



