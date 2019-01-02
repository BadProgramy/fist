package website.psuti.fist.dao.mainPage;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.MainPage;

import java.util.List;

@Mapper
public interface DAOMainPage {
    List<MainPage> getAllMainPage();
    int insert (MainPage mainPage);
    void update(MainPage mainPage);
    void delete(int id);
}
