package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.curator.DAOCurator;
import website.psuti.fist.model.Curator;

import java.util.List;

@Service
public class CuratorService {
    @Autowired
    private DAOCurator daoCurator;

    @Transactional
    public long insert(Curator curator) { return daoCurator.insert(curator); }

    @Transactional
    public void update(Curator curator) { daoCurator.update(curator); }

    @Transactional
    public void delete(long id) { daoCurator.delete(id);}

    @Transactional
    public List<Curator> getAll() {
        return daoCurator.getAll();
    }

    @Transactional
    public Curator findById(long id) { return daoCurator.findById(id); }
}
