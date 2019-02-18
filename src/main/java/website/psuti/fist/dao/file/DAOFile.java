package website.psuti.fist.dao.file;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.File;

import java.util.List;

@Mapper
public interface DAOFile {
    List<File> getAll();
    long insert (File file);
    void update(File file);
    void delete(int id);

    File findFileById(Long id);
}
