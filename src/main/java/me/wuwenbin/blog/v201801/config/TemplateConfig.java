package me.wuwenbin.blog.v201801.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import me.wuwenbin.modules.jpa.factory.DaoFactory;
import me.wuwenbin.modules.jpa.factory.business.DataSourceX;
import me.wuwenbin.modules.jpa.factory.business.DbType;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by Wuwenbin on 2018/1/17 at 19:57
 */
@Configuration
public class TemplateConfig {

    //==========================jpa配置========================
    @Bean
    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceX dataSourceX(DruidDataSource druidDataSource) {
        DataSourceX dataSourceX = new DataSourceX();
        dataSourceX.setDataSource(druidDataSource);
        dataSourceX.setInitDbType(DbType.Mysql);
        return dataSourceX;
    }

    @Bean
    public DaoFactory daoFactory(DataSourceX dataSourceX) {
        DaoFactory daoFactory = new DaoFactory();
        Map<String, DataSourceX> multiDao = new ConcurrentHashMap<>(2);
        String defaultDao = "blog_v201801_default_dao";
        multiDao.put(defaultDao, dataSourceX);
        daoFactory.setDataSourceMap(multiDao);
        daoFactory.setDefaultDao(dataSourceX);
        return daoFactory;
    }

    //==========================repository配置==================
    @Bean
    public RepositoryFactory repositoryFactory(ApplicationContext applicationContext) {
        RepositoryFactory factory = new RepositoryFactory();
        factory.setApplicationContext(applicationContext);
        return factory;
    }

    //=========================validation配置=====================
    @Bean
    public RTemplate rTemplate(MessageSource messageSource) {
        return new RTemplate(messageSource);
    }
}
