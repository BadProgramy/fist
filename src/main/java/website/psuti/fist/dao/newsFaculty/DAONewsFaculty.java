package website.psuti.fist.dao.newsFaculty;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.NewsOfFaculty;

import java.util.List;

@Mapper
public interface DAONewsFaculty {
    List<NewsOfFaculty> getAll();
    int insert (NewsOfFaculty newsOfFaculty);
    void update(NewsOfFaculty newsOfFaculty);
    void delete(long id);

    NewsOfFaculty findById(long id);
}
