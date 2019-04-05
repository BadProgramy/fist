package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.candidateAssignment.DAOCandidateAssignment;
import website.psuti.fist.model.CandidateAssignment;

import java.util.List;

@Service
public class CandidateAssignmentService {
    @Autowired
    private DAOCandidateAssignment daoCandidateAssignment;

    @Transactional
    public long insert(CandidateAssignment student) { return daoCandidateAssignment.insert(student); }

    @Transactional
    public void update(CandidateAssignment student) { daoCandidateAssignment.update(student); }

    @Transactional
    public void delete(long id) { daoCandidateAssignment.delete(id);}

    @Transactional
    public List<CandidateAssignment> getAll() {
        return daoCandidateAssignment.getAll();
    }

    @Transactional
    public CandidateAssignment findById(Long id) {
        return daoCandidateAssignment.findById(id);
    }
}
