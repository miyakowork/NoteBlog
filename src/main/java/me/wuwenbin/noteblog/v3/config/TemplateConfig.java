package me.wuwenbin.noteblog.v3.config;

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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by Wuwenbin on 2018/1/17 at 19:57
 */
@Slf4j
@Configuration
public class TemplateConfig {

    @Autowired
    private Environment environment;

    //==========================jpa配置========================
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        String dbPath = environment.getProperty("db.path");
        if (StringUtils.isEmpty(dbPath)) {
            throw new IllegalArgumentException("数据库文件路径未正确配置！");
        }
        druidDataSource.setUrl("jdbc:h2:" + dbPath + ";IGNORECASE=TRUE;MODE=MySQL;");
        return druidDataSource;
    }

    @Bean
    public DataSourceX dataSourceX(DruidDataSource druidDataSource) {
        DataSourceX dataSourceX = new DataSourceX();
        dataSourceX.setDataSource(druidDataSource);
        dataSourceX.setInitDbType(DbType.H2);
        return dataSourceX;
    }

    @Bean
    public DaoFactory daoFactory(DataSourceX dataSourceX) {
        DaoFactory daoFactory = new DaoFactory();
        Map<String, DataSourceX> multiDao = new ConcurrentHashMap<>(2);
        String defaultDao = "noteblogv3_2_0_default_dao";
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
