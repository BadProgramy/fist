package website.psuti.fist.dao.candidateAssignment;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.CandidateAssignment;
import website.psuti.fist.service.RequestPostConnection;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class DAOCandidateAssignmentImpl implements DAOCandidateAssignment {
    @Autowired
    private Factory factory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<CandidateAssignment> getAll() {
        List<CandidateAssignment> CandidateAssignments;
        SqlSession session = factory.getFactory().openSession();
        try {
            CandidateAssignments = session.selectList("CandidateAssignment.selectAll");
        } finally {
            session.close();
        }
        return CandidateAssignments;
    }

    @Override
    public long insert(CandidateAssignment student) {
        SqlSession session = factory.getFactory().openSession();
        long id = -1;
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.insert("CandidateAssignment.add", student);
            if (id == 1) {
                id = session.selectOne("CandidateAssignment.getLastIdInsert");
                MainPageObjectConstant.addCheck(NameTableBD.CANDIDATE_ASSIGNMENT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(CandidateAssignment student) {
        int id = -1;
        SqlSession session = factory.getFactory().openSession();
        try {
            RequestPostConnection.requestions(dataSource);
            id = session.update("CandidateAssignment.update", student);
            if (id == 1) MainPageObjectConstant.addCheck(NameTableBD.CANDIDATE_ASSIGNMENT);
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
            check = session.delete("CandidateAssignment.deleteById", id);
            if (check == 1) MainPageObjectConstant.addCheck(NameTableBD.CANDIDATE_ASSIGNMENT);
        } finally {
            session.close();
        }
    }
}
