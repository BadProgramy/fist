package website.psuti.fist.dao.educationProcess;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.EducationProcess;

import java.util.List;

@Mapper
public interface DAOEducationProcess {
    List<EducationProcess> getAll();
    int insert (EducationProcess educationProcess);
    void update(EducationProcess educationProcess);
    void delete(long id);

    List<EducationProcess> educationProcess();
}
