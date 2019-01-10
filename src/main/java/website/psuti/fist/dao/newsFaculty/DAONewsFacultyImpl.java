package website.psuti.fist.dao.newsFaculty;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.NewsOfFaculty;

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
    private DataSource dataSource;

    @Override
    public List<NewsOfFaculty> getAll() {
        List<NewsOfFaculty> newsOfFaculties;
        SqlSession session = factory.getFactory().openSession();
        try {
            newsOfFaculties = session.selectList("NewsOfFaculty.selectAll");
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }

    @Override
    public long insert(NewsOfFaculty newsOfFaculty) {
        SqlSession session = factory.getFactory().openSession();
        int id = -1;
        try {
            id = session.insert("NewsOfFaculty.add", newsOfFaculty);
            if (id == 1) id = session.selectOne("NewsOfFaculty.getLastIdInsert");
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(NewsOfFaculty newsOfFaculty) {
        SqlSession session = factory.getFactory().openSession();
        try {
            session.update("NewsOfFaculty.update", newsOfFaculty);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) {
        SqlSession session = factory.getFactory().openSession();
        try {
            session.delete("NewsOfFaculty.deleteById", id);
        } finally {
            session.close();
        }
    }

    @Override
    public NewsOfFaculty findById(long id) {
        NewsOfFaculty newsOfFaculty;
        SqlSession session = factory.getFactory().openSession();
        try {
            newsOfFaculty = session.selectOne("NewsOfFaculty.findById", id);
        } finally {
            session.close();
        }
        return newsOfFaculty;
    }

    @Override
    public List<NewsOfFaculty> getLastTenByDate(int count) {
        List<NewsOfFaculty> newsOfFaculties;
        SqlSession session = factory.getFactory().openSession();
        try {
            newsOfFaculties = session.selectList("NewsOfFaculty.selectLastTenByDate", count);
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }

    @Override
    public List<NewsOfFaculty> getLastNewsByRangeDate(LocalDate withDate, LocalDate fromDate) throws SQLException {
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("dateWith", java.sql.Date.valueOf(withDate));
        dateMap.put("dateFrom", java.sql.Date.valueOf(fromDate));
        List<NewsOfFaculty> newsOfFaculties;
        SqlSession session = factory.getFactory().openSession();
        try {
            newsOfFaculties = session.selectList("NewsOfFaculty.selectNewsByRangeDate", dateMap);
        } finally {
            session.close();
        }
        return newsOfFaculties;
    }
}
