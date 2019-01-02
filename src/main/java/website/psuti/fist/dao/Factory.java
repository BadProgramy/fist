package website.psuti.fist.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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
