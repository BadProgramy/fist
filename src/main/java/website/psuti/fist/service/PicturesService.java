package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.pictures.DAOPictures;
import website.psuti.fist.model.Pictures;

import java.util.List;

@Service
public class PicturesService {

    @Autowired
    private DAOPictures daoPictures;

    @Transactional
    public long insert(Pictures pictures) { return daoPictures.insert(pictures); }

    @Transactional
    public void update(Pictures pictures) { daoPictures.update(pictures);}

    @Transactional
    public List<Pictures> getAll() {
        return daoPictures.getAll();
    }

    @Transactional
    public Pictures findPictureById(long id) {
        return daoPictures.findPictureById(id);
    }

    @Transactional
    public List<Pictures> findPicturesByKey(long keyPicture) {
        return daoPictures.findPicturesByKey(keyPicture);
    }

    @Transactional
    public Pictures findPictureByName(String name) {
        return daoPictures.findPictureByName(name);
    }
}
