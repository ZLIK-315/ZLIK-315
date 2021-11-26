package com.markerhub;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author zhang Bowen
 * @date 2021-09-30 17:29
 * mp代码生成
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Bowen Zhang");
        //生成后是否打开资源管理器
        gc.setOpen(false);
        //重新生成时文件是否覆盖
        gc.setFileOverride(false);
        //去掉Service接口的首字母I
        gc.setServiceName("%sService");
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName(null);
        pc.setParent("com.vueadminjava");
        pc.setController("sys.controller");
        pc.setEntity("sys.entity");
        pc.setService("sys.service");
        pc.setMapper("sys.mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        //对那一张表生成代码
        strategy.setInclude("sys_dept");
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成实体时去掉表前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");
        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        //设置公共父类
        strategy.setSuperEntityColumns("id","created","updated","state");
        // 6、执行
        mpg.execute();

    }
}
