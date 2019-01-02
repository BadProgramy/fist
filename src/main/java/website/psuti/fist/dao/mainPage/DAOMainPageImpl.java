package website.psuti.fist.dao.mainPage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.MainPage;
import java.util.List;

@Primary
@Repository
public class DAOMainPageImpl implements DAOMainPage {

    @Autowired
    private Factory factory;

    @Override
    public List<MainPage> getAllMainPage() {
        List<MainPage> mainPages;
        SqlSession session = factory.getFactory().openSession();
        try {
            mainPages = session.selectList("MainPage.selectAll");
        } finally {
            session.close();
        }
        return mainPages;
    }

    @Override
    public int insert(MainPage mainPage) {
        return 0;
    }

    @Override
    public void update(MainPage mainPage) {

    }

    @Override
    public void delete(int id) {

    }
}
