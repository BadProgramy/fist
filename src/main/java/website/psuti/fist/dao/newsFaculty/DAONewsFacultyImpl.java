package website.psuti.fist.dao.newsFaculty;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.constant.NewsFacultyConstant;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class DAONewsFacultyImpl implements DAONewsFaculty {

    @Autowired
    private Factory factory;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<NewsOfFaculty> getAll() {
        List<NewsOfFaculty> newsOfFaculties = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            newsOfFaculties = session.selectList("NewsOfFaculty.selectAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }

    @Override
    public long insert(NewsOfFaculty newsOfFaculty) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        newsOfFaculty.setText(newsOfFaculty.getText().replace("\r\n","<br>").replace("\n","<br>"));
        newsOfFaculty.setHeading(newsOfFaculty.getHeading().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("NewsOfFaculty.add", newsOfFaculty);
            if (id == 1) {
                id = session.selectOne("NewsOfFaculty.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.NEWS_OF_FACULTY);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(NewsOfFaculty newsOfFaculty) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        newsOfFaculty.setText(newsOfFaculty.getText().replace("\r\n","<br>").replace("\n","<br>"));
        newsOfFaculty.setHeading(newsOfFaculty.getHeading().replace("\r\n","<br>").replace("\n","<br>"));
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("NewsOfFaculty.update", newsOfFaculty);
            if (id == 1) MainPageObjectConstant.addCheck(NameTableBD.NEWS_OF_FACULTY);
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
            check = session.delete("NewsOfFaculty.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.NEWS_OF_FACULTY);
        } finally {
            session.close();
        }
    }

    @Override
    public NewsOfFaculty findById(long id) {
        NewsOfFaculty newsOfFaculty = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            newsOfFaculty = session.selectOne("NewsOfFaculty.findById", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newsOfFaculty;
    }

    @Override
    public List<NewsOfFaculty> getLastCountByDateFilledPicture(int count) {
        List<NewsOfFaculty> newsOfFaculties = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            newsOfFaculties = session.selectList("NewsOfFaculty.selectLastTenByDate", count);
            for (NewsOfFaculty newFaculty : newsOfFaculties) {
                newFaculty.setPicture(picturesService.findPictureById(newFaculty.getIdPicture()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }

    @Override
    public List<NewsOfFaculty> getLastNewsByRangeDate(LocalDate withDate, LocalDate fromDate) {
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("dateWith", java.sql.Date.valueOf(withDate));
        dateMap.put("dateFrom", java.sql.Date.valueOf(fromDate));
        List<NewsOfFaculty> newsOfFaculties = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            newsOfFaculties = session.selectList("NewsOfFaculty.selectNewsByRangeDate", dateMap);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }

    @Override
    public List<NewsOfFaculty> getLastTwoNewsFaculty() {
        List<NewsOfFaculty> newsOfFaculties = null;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            newsOfFaculties = session.selectList("NewsOfFaculty.selectLastTwoNewsFaculty");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }
}
