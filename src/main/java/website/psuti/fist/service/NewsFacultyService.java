package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.newsFaculty.DAONewsFaculty;
import website.psuti.fist.model.NewsOfFaculty;

import java.util.List;

@Service
public class NewsFacultyService {

    @Autowired
    private DAONewsFaculty daoNewsFaculty;

    @Transactional
    public List<NewsOfFaculty> getAll() {
        return daoNewsFaculty.getAll();
    }

    @Transactional
    public void delete(long idNews) {
        daoNewsFaculty.delete(idNews);
    }

    @Transactional
    public void update(NewsOfFaculty newsOfFaculty) {
        daoNewsFaculty.update(newsOfFaculty);
    }

    @Transactional
    public NewsOfFaculty findById(long id) {
        return daoNewsFaculty.findById(id);
    }
}
