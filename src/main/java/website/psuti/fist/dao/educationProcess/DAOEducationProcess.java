package website.psuti.fist.dao.educationProcess;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.model.Pictures;

import java.util.List;
import java.util.Map;

@Mapper
public interface DAOEducationProcess {
    List<EducationProcess> getAll();
    int insert (EducationProcess educationProcess);
    void update(EducationProcess educationProcess);
    void delete(long id);

    List<EducationProcess> educationProcess();
}
