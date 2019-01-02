package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.mainPage.DAOMainPage;
import website.psuti.fist.model.MainPage;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainPageService {

    @Autowired
    private DAOMainPage daoMainPage;

    @Transactional
    public void save(MainPage mainPage) throws SQLException {
        /*RequestPostConnection.requestions(dataSource);*/
        daoMainPage.insert(mainPage);
    }

    @Transactional
    public List<MainPage> getAll() {
        return daoMainPage.getAllMainPage();
    }

    @Transactional
    public void update(MainPage mainPage) {
        daoMainPage.update(mainPage);
    }
}
