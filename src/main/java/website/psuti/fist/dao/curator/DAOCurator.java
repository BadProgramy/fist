package website.psuti.fist.dao.curator;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.Curator;

import java.util.List;

@Mapper
public interface DAOCurator {
    List<Curator> getAll();
    long insert (Curator curator);
    void update(Curator curator);
    void delete(long id);

    Curator findById(long id);
}
