package me.wuwenbin.noteblog.v4.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.jpa.factory.DaoFactory;
import me.wuwenbin.modules.jpa.factory.business.DataSourceX;
import me.wuwenbin.modules.jpa.factory.business.DbType;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.repository.registry.RepositoryRegistry;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/17 at 19:57
 * @author wuwenbin
 */
@Slf4j
@Configuration
public class TemplateConfig {

    private final Environment environment;

    @Autowired
    public TemplateConfig(Environment environment) {
        this.environment = environment;
    }

    //==========================jpa配置,多数据库类型========================

    @Bean
    public DruidDataSource dataSource() {
        String dbType = environment.getProperty("db.type");
        if (StringUtils.isEmpty(dbType)) {
            throw new IllegalArgumentException("请正确配置数据库的类型！");
        } else {
            DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
            if ("h2".equals(dbType)) {
                String driverClassName = "org.h2.Driver";
                String dbPath = environment.getProperty("db.path");
                if (StringUtils.isEmpty(dbPath)) {
                    throw new IllegalArgumentException("数据库文件路径未正确配置！");
                }
                druidDataSource.setDbType("h2");
                druidDataSource.setDriverClassName(driverClassName);
                druidDataSource.setUrl("jdbc:h2:" + dbPath + ";IGNORECASE=TRUE;MODE=MySQL;");
            } else {
                String dbIp = environment.getProperty("db.mysql.ip", "127.0.0.1");
                String dbPort = environment.getProperty("db.mysql.port", "3306");
                String dbName = environment.getProperty("db.name", "noteblogv3");
                String url = "jdbc:mysql://" + dbIp + ":" + dbPort + "/" + dbName + "?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
                String driverClassName = "com.mysql.jdbc.Driver";
                druidDataSource.setDbType("mysql");
                druidDataSource.setDriverClassName(driverClassName);
                druidDataSource.setUrl(url);
            }
            return druidDataSource;
        }
    }

    @Bean
    public DataSourceX dataSourceX(DruidDataSource druidDataSource) {
        DataSourceX dataSourceX = new DataSourceX();
        dataSourceX.setDataSource(druidDataSource);
        String dbType = environment.getProperty("db.type");
        if (StringUtils.isEmpty(dbType)) {
            throw new IllegalArgumentException("请正确配置数据库的类型！");
        }
        if ("h2".equals(dbType)) {
            dataSourceX.setInitDbType(DbType.H2);
        } else {
            dataSourceX.setInitDbType(DbType.Mysql);
        }
        return dataSourceX;
    }

    @Bean
    public DaoFactory daoFactory(DataSourceX dataSourceX) {
        DaoFactory daoFactory = new DaoFactory();
        Map<String, DataSourceX> multiDao = new HashMap<>(2);
        String defaultDao = "noteblogv3_3_0_default_dao";
        multiDao.put(defaultDao, dataSourceX);
        daoFactory.setDataSourceMap(multiDao);
        daoFactory.setDefaultDao(dataSourceX);
        return daoFactory;
    }

    //==========================repository配置==================

    @Bean
    public static RepositoryRegistry repositoryRegistry() {
        return new RepositoryRegistry("me.wuwenbin");
    }

    @Bean
    public RepositoryFactory repositoryFactory(ApplicationContext applicationContext) {
        RepositoryFactory factory = new RepositoryFactory();
        factory.setApplicationContext(applicationContext);
        return factory;
    }


    //=========================validation信息模板配置=====================

    @Bean
    public RTemplate rTemplate(MessageSource messageSource) {
        return new RTemplate(messageSource);
    }
}
