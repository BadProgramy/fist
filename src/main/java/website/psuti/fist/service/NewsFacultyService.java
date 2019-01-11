package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.newsFaculty.DAONewsFaculty;
import website.psuti.fist.model.NewsOfFaculty;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewsFacultyService {

    @Autowired
    private DAONewsFaculty daoNewsFaculty;

    @Transactional
    public long insert(NewsOfFaculty newsOfFaculty) {
        return daoNewsFaculty.insert(newsOfFaculty);
    }

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

    @Transactional
    public List<NewsOfFaculty> getLastTenByDateFilledPicture(int count) {
        return daoNewsFaculty.getLastTenByDateFilledPicture(count);
    }

    @Transactional
    public List<NewsOfFaculty> getLastNewsByRangeDate(LocalDate withDate, LocalDate fromDate) throws SQLException {
        return daoNewsFaculty.getLastNewsByRangeDate(withDate, fromDate);
    }

    @Transactional
    public List<NewsOfFaculty> getLastTwoNewsFaculty() {
        return daoNewsFaculty.getLastTwoNewsFaculty();
    }
}
