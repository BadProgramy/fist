package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.bestStudent.DAOBestStudent;
import website.psuti.fist.model.BestStudent;

import java.util.List;

@Service
public class BestStudentService {
    @Autowired
    private DAOBestStudent daoBestStudent;

    @Transactional
    public long insert(BestStudent bestStudent) { return daoBestStudent.insert(bestStudent); }

    @Transactional
    public void update(BestStudent bestStudent) { daoBestStudent.update(bestStudent); }

    @Transactional
    public void delete(long id) { daoBestStudent.delete(id);}

    @Transactional
    public List<BestStudent> getAll() {
        return daoBestStudent.getAll();
    }

    @Transactional
    public List<BestStudent> filledBestStudent() {
        return daoBestStudent.filledBestStudent();
    }

    @Transactional
    public BestStudent findById(long id) { return daoBestStudent.findById(id); }

}
