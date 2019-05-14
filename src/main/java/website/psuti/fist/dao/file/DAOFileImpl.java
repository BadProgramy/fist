package website.psuti.fist.dao.file;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.File;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOFileImpl implements DAOFile{
    public final Logger logger = LoggerFactory.getLogger(DAOFileImpl.class);

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<File> getAll() {
        List<File> files;
        SqlSession session = factory.getFactory().openSession();
        try {
            files = session.selectList("File.selectAll");
        } finally {
            session.close();
        }
        return files;
    }

    @Override
    public long insert(File file) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("File.add", file);
            if (id == 1) {
                id = session.selectOne("File.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.FILE);
                logger.info("Добавлен файл - " + file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(File file) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("File.update", file);
            if (id == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.FILE);
                logger.info("Обновлен файл - " + file);
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
        File file = findFileById(id);
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("File.deleteById", id);
            if (check == 1) {
                MainPageObjectConstant.addCheck(NameTableBD.FILE);
                logger.info("Удален файл - " + file);
            }
        } finally {
            session.close();
        }
        return check;
    }

    @Override
    public File findFileById(Long id) {
        File file = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            file = session.selectOne("File.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return file;
    }
}
