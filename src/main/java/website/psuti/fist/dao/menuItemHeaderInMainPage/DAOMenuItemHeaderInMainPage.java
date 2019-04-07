package website.psuti.fist.dao.menuItemHeaderInMainPage;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.model.Pictures;

import java.util.List;
import java.util.Map;

@Mapper
public interface DAOMenuItemHeaderInMainPage {
    List<MenuItemHeaderInMainPage> getAll();
    long insert (MenuItemHeaderInMainPage menuItemHeaderInMainPage);
    void update(MenuItemHeaderInMainPage menuItemHeaderInMainPage);
    void delete(long id);

    List<MenuItemHeaderInMainPage> getMinorHeadersByMainHeader(long idMainHeader);
    Map<MenuItemHeaderInMainPage,List<MenuItemHeaderInMainPage>> getAllHeadersMainPage();
    MenuItemHeaderInMainPage findItemById(long id);
    List<MenuItemHeaderInMainPage> getCharacterUniversity();

    List<MenuItemHeaderInMainPage> findItemByKeyWord(String keyWord);

    List<MenuItemHeaderInMainPage> findItemByIdParent(long id);

    long insertById(MenuItemHeaderInMainPage menuItem);
}
