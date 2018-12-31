package website.psuti.fist.dao.educationProcess;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.dao.menuItemHeaderInMainPage.DAOMenuItemHeaderInMainPage;
import website.psuti.fist.dao.pictures.DAOPictures;
import website.psuti.fist.model.EducationProcess;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class DAOEducationProcessImpl implements DAOEducationProcess {

    @Autowired
    private DAOPictures daoPictures;

    @Autowired
    private DAOMenuItemHeaderInMainPage daoMenuItemHeaderInMainPage;

    @Autowired
    private Factory factory;

    @Override
    public List<EducationProcess> getAll() {
        List<EducationProcess> educationProcesses;
        SqlSession session = factory.getFactory().openSession();
        try {
            educationProcesses = session.selectList("EducationProcess.selectAll");
        } finally {
            session.close();
        }
        return educationProcesses;
    }

    @Override
    public int insert(EducationProcess educationProcess) {
        return 0;
    }

    @Override
    public void update(EducationProcess educationProcess) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<EducationProcess> educationProcess() {
        List<EducationProcess> educationProcesses = new ArrayList<>();
        for (EducationProcess educationProcess: getAll()) {
            educationProcess.setPicturesLeft(daoPictures.findPictureById(educationProcess.getIdPictureLeft()));
            educationProcess.setPicturesRight(daoPictures.findPictureById(educationProcess.getIdPictureRight()));
            educationProcess.setMenuItemHeaderInMainPageLeft(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageLeft()));
            educationProcess.setMenuItemHeaderInMainPageRight(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageRight()));
            educationProcesses.add(educationProcess);
        }
        return educationProcesses;
    }
}
