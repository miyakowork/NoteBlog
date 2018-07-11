package me.wuwenbin.noteblog.v4.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * created by Wuwenbin on 2018/2/14 at 12:30
 */
@Configuration
public class SSLConfig {

    @Autowired
    private Environment environment;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected void postProcessContext(Context context) {
                if (environment.getProperty("server.ssl.enabled", Boolean.class, Boolean.FALSE)) {
                    SecurityConstraint constraint = new SecurityConstraint();
                    constraint.setUserConstraint("CONFIDENTIAL");
                    SecurityCollection collection = new SecurityCollection();
                    collection.addPattern("/*");
                    constraint.addCollection(collection);
                    context.addConstraint(constraint);
                } else {
                    super.postProcessContext(context);
                }
            }
        };
        if (environment.getProperty("server.ssl.enabled", Boolean.class, Boolean.FALSE)) {
            tomcat.addAdditionalTomcatConnectors(httpConnector());
        }
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(environment.getProperty("server.http.port", Integer.class, 80));
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(environment.getProperty("server.port", Integer.class, 443));
        return connector;
    }
}
