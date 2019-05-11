package website.psuti.fist.dao.HTMLStructurePage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.HTMLStructurePage;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOHtmlStructurePageImpl implements DAOHtmlStructurePage {

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<HTMLStructurePage> getAll() {
        List<HTMLStructurePage> htmlStructurePages;
        SqlSession session = factory.getFactory().openSession();
        try {
            htmlStructurePages = session.selectList("File.selectAll");
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
            if (id == 1) MainPageObjectConstant.addCheck(NameTableBD.HTMLStructurePage);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public int delete(long id) {
        int check = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("HTMLStructurePage.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.HTMLStructurePage);
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
}
