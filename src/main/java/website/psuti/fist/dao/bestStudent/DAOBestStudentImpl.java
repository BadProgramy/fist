package website.psuti.fist.dao.bestStudent;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.dao.pictures.DAOPictures;
import website.psuti.fist.model.BestStudent;

import java.util.List;

@Primary
@Repository
public class DAOBestStudentImpl implements DAOBestStudent {
    @Autowired
    private Factory factory;

    @Autowired
    private DAOPictures daoPictures;

    @Override
    public List<BestStudent> getAll() {
        List<BestStudent> bestStudents;
        SqlSession session = factory.getFactory().openSession();
        try {
            bestStudents = session.selectList("BestStudent.selectAll");
        } finally {
            session.close();
        }
        return bestStudents;
    }

    @Override
    public int insert(BestStudent bestStudent) {
        return 0;
    }

    @Override
    public void update(BestStudent bestStudent) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<BestStudent> filledBestStudent() {
        List<BestStudent> bestStudentsAll = getAll();
        for (BestStudent bestStudent: bestStudentsAll) {
            bestStudent.setPicture(daoPictures.findPictureById(bestStudent.getIdPicture()));
        }
        return bestStudentsAll;
    }
}
