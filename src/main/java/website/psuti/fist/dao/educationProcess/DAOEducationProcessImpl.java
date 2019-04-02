package website.psuti.fist.dao.educationProcess;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.dao.menuItemHeaderInMainPage.DAOMenuItemHeaderInMainPage;
import website.psuti.fist.dao.pictures.DAOPictures;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class DAOEducationProcessImpl implements DAOEducationProcess {

    @Autowired
    private DAOPictures daoPictures;

    @Autowired
    private DataSource dataSource;

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
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("EducationProcess.update", educationProcess);
            if (id == 1) {
/*                MainPageObjectConstant.checkModelAndView.add(NameTableBD.MENU_ITEM_HEADER_IN_MAIN_PAGE);
                MainPageObjectConstant.checkModelAndView.add(NameTableBD.PICTURES);*/
                MainPageObjectConstant.addCheck(NameTableBD.EDUCATION_PROCESS);
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
        SqlSession session = factory.getFactory().openSession();
        try {
            check = session.delete("EducationProcess.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.EDUCATION_PROCESS);
        } finally {
            session.close();
        }
        //return check;
    }

    @Override
    public List<EducationProcess> educationProcess() {
        List<EducationProcess> educationProcesses = new ArrayList<>();
        for (EducationProcess educationProcess: getAll()) {
            educationProcess.setPicturesLeft(daoPictures.findPictureById(educationProcess.getIdPictureLeft()));
            educationProcess.setLeftPictureStyles(daoPictures.findPictureById(educationProcess.getIdPictureLeft()).getStyles());

            educationProcess.setPicturesRight(daoPictures.findPictureById(educationProcess.getIdPictureRight()));
            educationProcess.setRightPictureStyles(daoPictures.findPictureById(educationProcess.getIdPictureRight()).getStyles());

            educationProcess.setMenuItemHeaderInMainPageLeft(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageLeft()));
            educationProcess.setLeftMenuItemStyles(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageLeft()).getStyles());

            educationProcess.setMenuItemHeaderInMainPageRight(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageRight()));
            educationProcess.setRightPictureStyles(daoMenuItemHeaderInMainPage.findItemById(educationProcess.getIdMenuItemHeaderInMainPageRight()).getStyles());

            educationProcesses.add(educationProcess);
        }
        return educationProcesses;
    }
}
