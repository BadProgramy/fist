package website.psuti.fist.service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class RequestPostConnection {

    public static void requestions(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statementOne = connection.prepareStatement("set character set utf8");
        PreparedStatement statementTwo = connection.prepareStatement("set names utf8");
        statementOne.execute();
        statementTwo.execute();
        statementOne.close();
        statementTwo.close();
        connection.close();
    }
}
