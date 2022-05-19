package com.gxzygygs.iom.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class CodeGenerator {


    /**
     * 数据库链接
     */
    private static String DATA_SOURCE_URL;

    @Value("${spring.datasource.druid.url}")
    public void setDataSourceUrl(String s){
        CodeGenerator.DATA_SOURCE_URL=s;
    }
    /**
     * 账号
     */
    private static String DATA_SOURCE_USERNAME ;

    @Value("${spring.datasource.druid.username}")
    public void setDataSourceUsername(String s){
        CodeGenerator.DATA_SOURCE_USERNAME=s;
    }
    /**
     * 密码
     */
    private static String DATA_SOURCE_PASSWORD ;

    @Value("${spring.datasource.druid.password}")
    public void setDataSourcePassword(String s){
        CodeGenerator.DATA_SOURCE_PASSWORD=s;
    }
    /**
     * 包地址
     */
    private static final String PACKAGE_NAME = "com.gxzygygs.iom";
    /**
     * 实体包的包名
     */
    private static final String ENTITY = "entity.Do";
    /**
     * 作者
     */
    private static final String AUTHOR = "yulin";

    /**
     * 为了用起来更方便，表名在控制台输入
     * @param tip 表名
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public void codeGenerator() {
        //获取当前项目最外层的工作目录大概是(杂七杂八路径../com/fxzygygs/iom)
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\java\\generateCode";

        /**
         * 数据源配置
         */
         DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
                .Builder(DATA_SOURCE_URL, DATA_SOURCE_USERNAME, DATA_SOURCE_PASSWORD)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.enableSwagger()
                            .author(AUTHOR)
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(projectPath);
                })
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_NAME)
                            .entity(ENTITY)
                            .service("service")
                            .serviceImpl("service.impl")
//                            .controller("controller")
                            .mapper("mapper");
                }).strategyConfig((scanner, builder) -> {
                    builder.enableCapitalMode().addInclude(scanner.apply("写你要的表，隔开"));
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
