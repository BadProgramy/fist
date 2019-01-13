package website.psuti.fist.dao.newsFaculty;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import website.psuti.fist.model.NewsOfFaculty;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DAONewsFaculty {
    List<NewsOfFaculty> getAll();
    long insert (NewsOfFaculty newsOfFaculty);
    void update(NewsOfFaculty newsOfFaculty);
    void delete(long id);

    NewsOfFaculty findById(long id);
    List<NewsOfFaculty> getLastCountByDateFilledPicture(int count);
    List<NewsOfFaculty> getLastNewsByRangeDate(LocalDate withDate, LocalDate fromDate) throws SQLException;
    List<NewsOfFaculty> getLastTwoNewsFaculty();
}
