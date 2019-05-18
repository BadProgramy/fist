package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.psuti.fist.dao.file.DAOFile;
import website.psuti.fist.model.File;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private DAOFile daoFile;

    public List<File> getAll() {
        return daoFile.getAll();
    }

    public long insert(File file) {
        return daoFile.insert(file);
    }

    public void update(File file) {
        daoFile.update(file);
    }

    public File findFileById(Long id) {
        return daoFile.findFileById(id);
    }

    public int delete(Long id) {
       return daoFile.delete(id);
    }

    public File findFileByNameUnique(String fileNameUnique) {
        return daoFile.findFileByNameUnique(fileNameUnique);
    }
}
