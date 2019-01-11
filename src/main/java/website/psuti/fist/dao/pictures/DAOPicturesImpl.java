package website.psuti.fist.dao.pictures;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.Pictures;

import java.util.List;

@Primary
@Repository
public class DAOPicturesImpl implements DAOPictures {

    @Autowired
    private Factory factory;

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
            id = session.insert("Pictures.add", pictures);
            if (id == 1) id = session.selectOne("Pictures.getLastIdInsert");
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Pictures pictures) {
        SqlSession session = factory.getFactory().openSession();
        try {
            session.update("Pictures.update", pictures);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Pictures> findPicturesByKey(long keyPicture) {
        List<Pictures> pictures;
        SqlSession session = factory.getFactory().openSession();
        try {
            pictures = session.selectList("Pictures.selectPicturesByKey", keyPicture);
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public Pictures findPictureByName(String namePicture) {
        Pictures pictures;
        SqlSession session = factory.getFactory().openSession();
        try {
            pictures = session.selectOne("Pictures.selectPicturesByName", namePicture);
        } finally {
            session.close();
        }
        return pictures;
    }

    @Override
    public Pictures findPictureById(long id) {
        Pictures pictures;
        SqlSession session = factory.getFactory().openSession();
        try {
            pictures = session.selectOne("Pictures.findById", id);
        } finally {
            session.close();
        }
        return pictures;
    }
}
