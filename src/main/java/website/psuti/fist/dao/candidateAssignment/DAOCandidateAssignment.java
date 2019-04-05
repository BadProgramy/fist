package website.psuti.fist.dao.candidateAssignment;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.CandidateAssignment;

import java.util.List;

@Mapper
public interface DAOCandidateAssignment {
    List<CandidateAssignment> getAll();
    long insert (CandidateAssignment student);
    void update(CandidateAssignment student);
    void delete(long id);
}
