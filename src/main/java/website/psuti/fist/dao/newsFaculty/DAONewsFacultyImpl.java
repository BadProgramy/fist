package website.psuti.fist.dao.newsFaculty;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.NewsOfFaculty;

import java.util.List;

@Primary
@Repository
public class DAONewsFacultyImpl implements DAONewsFaculty {

    @Autowired
    private Factory factory;

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
    public int insert(NewsOfFaculty newsOfFaculty) {
        return 0;
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
}
