package website.psuti.fist.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import website.psuti.fist.FistApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class Factory {
    private SqlSessionFactory factory;

    public Factory() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("configuration/spring-config.xml");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfigLocation(ctx.getResource("configuration/config.xml"));
        //factory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
        try {
            factory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        if (FistApplication.args.length >= 4)
        {
            dataSource.setDriverClassName(FistApplication.args[0].split("=")[1]);
            dataSource.setUrl(FistApplication.args[1].split("=")[1]);
            dataSource.setUsername(FistApplication.args[2].split("=")[1]);
            dataSource.setPassword(FistApplication.args[3].split("=")[1]);
        } else {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "application.properties";
            Properties appProps = new Properties();
            try {
                appProps.load(new FileInputStream(appConfigPath));
                dataSource.setDriverClassName(appProps.getProperty("spring.datasource.driver-class-name"));
                dataSource.setUrl(appProps.getProperty("spring.datasource.url"));
                dataSource.setUsername(appProps.getProperty("spring.datasource.username"));
                dataSource.setPassword(appProps.getProperty("spring.datasource.password"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
}
