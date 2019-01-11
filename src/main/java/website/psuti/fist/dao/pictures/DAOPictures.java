package website.psuti.fist.dao.pictures;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.Pictures;

import java.util.List;

@Mapper
public interface DAOPictures {
    List<Pictures> getAll();
    long insert (Pictures pictures);
    void update(Pictures pictures);
    void delete(int id);

    Pictures findPictureById(long id);
    List<Pictures> findPicturesByKey(long keyPicture);
    Pictures findPictureByName(String namePicture);
}
