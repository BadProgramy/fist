package website.psuti.fist.dao.pictures;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.KeyPicture;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOPicturesImpl implements DAOPictures {

    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Pictures> getAll() {
        List<Pictures> pictures;
        SqlSession session = factory.getFactory().openSession();
        try {
            pictures = session.selectList("Pictures.selectAll");
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public long insert(Pictures pictures) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("Pictures.add", pictures);
            if (id == 1) {
                id = session.selectOne("Pictures.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.PICTURES);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Pictures pictures) {
        int check = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            check = session.update("Pictures.update", pictures);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.PICTURES);
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
            check = session.delete("Pictures.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.PICTURES);
        } finally {
            session.close();
        }
        return check;
    }

    @Override
    public List<Pictures> findPicturesByKey(KeyPicture keyPicture) {
        List<Pictures> pictures = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            pictures = session.selectList("Pictures.selectPicturesByKey", keyPicture.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public Pictures findPictureByName(String namePicture) {
        Pictures pictures = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            pictures = session.selectOne("Pictures.selectPicturesByName", namePicture);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public Pictures findPictureById(long id) {
        Pictures pictures = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            pictures = session.selectOne("Pictures.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pictures;
    }
}
