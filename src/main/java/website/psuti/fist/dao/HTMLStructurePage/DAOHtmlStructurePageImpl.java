package website.psuti.fist.dao.HTMLStructurePage;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.HTMLStructurePage;
import website.psuti.fist.model.TypeHtmlCode;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOHtmlStructurePageImpl implements DAOHtmlStructurePage {
    public final Logger logger = LoggerFactory.getLogger(DAOHtmlStructurePageImpl.class);

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<HTMLStructurePage> getAll() {
        List<HTMLStructurePage> htmlStructurePages;
        SqlSession session = factory.getFactory().openSession();
        try {
            htmlStructurePages = session.selectList("HTMLStructurePage.selectAll");
        } finally {
            session.close();
        }
        return htmlStructurePages;
    }

    @Override
    public long insert(HTMLStructurePage htmlStructurePage) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("HTMLStructurePage.add", htmlStructurePage);
            if (id == 1) {
                id = session.selectOne("HTMLStructurePage.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.HTMLStructurePage);
                logger.info("Добавлен html код - " + htmlStructurePage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(HTMLStructurePage htmlStructurePage) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("HTMLStructurePage.update", htmlStructurePage);
            if (id == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.HTMLStructurePage);
                logger.info("Обновлен html код - " + htmlStructurePage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public int delete(long id) {
        int check = -1;
        HTMLStructurePage htmlStructurePage = findHTMLStructurePageById(id);
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("HTMLStructurePage.deleteById", id);
            if (check == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.HTMLStructurePage);
                logger.info("Удален html код - " + htmlStructurePage);
            }
        } finally {
            session.close();
        }
        return check;
    }

    @Override
    public HTMLStructurePage findHTMLStructurePageById(Long id) {
        HTMLStructurePage file = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            file = session.selectOne("HTMLStructurePage.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return file;
    }

    @Override
    public List<HTMLStructurePage> findHTMLCodeByType(TypeHtmlCode typeHtmlCode) {
        List<HTMLStructurePage> htmlStructurePages;
        SqlSession session = factory.getFactory().openSession();
        try {
            htmlStructurePages = session.selectList("HTMLStructurePage.findHTMLCodeByType", typeHtmlCode.name());
        } finally {
            session.close();
        }
        return htmlStructurePages;
    }
}
