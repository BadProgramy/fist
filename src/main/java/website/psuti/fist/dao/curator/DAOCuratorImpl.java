package website.psuti.fist.dao.curator;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Curator;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOCuratorImpl implements DAOCurator {
    public final Logger logger = LoggerFactory.getLogger(DAOCuratorImpl.class);

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Curator> getAll() {
        List<Curator> curators;
        SqlSession session = factory.getFactory().openSession();
        try {
            curators = session.selectList("Curator.selectAll");
        } finally {
            session.close();
        }
        return curators;
    }

    @Override
    public long insert(Curator curator) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("Curator.add", curator);
            if (id == 1) {
                id = session.selectOne("Curator.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.CURATOR);
                logger.info("Добавлен куратор - " + curator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Curator curator) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("Curator.update", curator);
            if (id == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.CURATOR);
                logger.info("Обновлён куратор - " + curator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) {
        int check = -1;
        Curator curator = findById(id);
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("Curator.deleteById", id);
            if (check == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.CURATOR);
                logger.info("Удален куратор - " + curator);
            }
        } finally {
            session.close();
        }
    }

    @Override
    public Curator findById(long id) {
        Curator curator = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            curator = session.selectOne("Curator.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return curator;
    }
}
