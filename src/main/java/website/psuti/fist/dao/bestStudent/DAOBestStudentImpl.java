package website.psuti.fist.dao.bestStudent;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.dao.pictures.DAOPictures;
import website.psuti.fist.model.BestStudent;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOBestStudentImpl implements DAOBestStudent {
    @Autowired
    private Factory factory;

    @Autowired
    private DAOPictures daoPictures;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<BestStudent> getAll() {
        List<BestStudent> bestStudents;
        SqlSession session = factory.getFactory().openSession();
        try {
            bestStudents = session.selectList("BestStudent.selectAll");
            /*for (BestStudent bestStudent: bestStudents) {
                bestStudent.setCharacteristic(bestStudent.getCharacteristic().replace("<br>","\r\n"));
            }*/
        } finally {
            session.close();
        }
        return bestStudents;
    }

    @Override
    public long insert(BestStudent bestStudent) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        bestStudent.setCharacteristic(bestStudent.getCharacteristic().replace("\r\n","<br>").replace("\n","<br>"));
        bestStudent.setGroupInUniversity(bestStudent.getGroupInUniversity().replace("\r\n","<br>").replace("\n","<br>"));
        bestStudent.setName(bestStudent.getName().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("BestStudent.add", bestStudent);
            if (id == 1) {
                id = session.selectOne("BestStudent.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.BEST_STUDENT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(BestStudent bestStudent) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        bestStudent.setCharacteristic(bestStudent.getCharacteristic().replace("\r\n","<br>").replace("\n","<br>"));
        bestStudent.setGroupInUniversity(bestStudent.getGroupInUniversity().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("BestStudent.update", bestStudent);
            if (id == 1) MainPageObjectConstant.addCheck(NameTableBD.BEST_STUDENT);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) throws IllegalArgumentException {
        int check = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("BestStudent.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.BEST_STUDENT);
        } finally {
            session.close();
        }
    }

    @Override
    public List<BestStudent> filledBestStudent() {
        List<BestStudent> bestStudentsAll = getAll();
        for (BestStudent bestStudent: bestStudentsAll) {
            bestStudent.setPicture(daoPictures.findPictureById(bestStudent.getIdPicture()));
        }
        return bestStudentsAll;
    }

    @Override
    public BestStudent findById(long id) {
        BestStudent bestStudent = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            bestStudent = session.selectOne("BestStudent.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return bestStudent;
    }
}
