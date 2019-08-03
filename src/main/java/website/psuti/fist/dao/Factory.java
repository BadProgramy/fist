package website.psuti.fist.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import website.psuti.fist.FistApplication;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class Factory {
    private SqlSessionFactory factory;

    public Factory() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("configuration/spring-config.xml");
        factory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SqlSessionFactory factory) {
        this.factory = factory;
    }
}
