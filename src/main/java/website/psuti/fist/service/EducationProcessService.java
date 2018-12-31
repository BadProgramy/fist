package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.educationProcess.DAOEducationProcess;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.model.Pictures;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

@Service
public class EducationProcessService {

    @Autowired
    private DAOEducationProcess daoEducationProcess;

    @Transactional
    public List<EducationProcess> getAll() {
        return daoEducationProcess.getAll();
    }

    @Transactional
    public List<EducationProcess> educationProcess() {
        return daoEducationProcess.educationProcess();
    }
}
