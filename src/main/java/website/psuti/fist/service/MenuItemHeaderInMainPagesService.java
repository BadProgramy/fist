package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.menuItemHeaderInMainPage.DAOMenuItemHeaderInMainPage;
import website.psuti.fist.model.MenuItemHeaderInMainPage;

import java.util.List;
import java.util.Map;

@Service
public class MenuItemHeaderInMainPagesService {

    @Autowired
    private DAOMenuItemHeaderInMainPage daoMenuItemHeaderInMainPage;

    @Transactional
    public void update(MenuItemHeaderInMainPage menuItemHeaderInMainPage) { daoMenuItemHeaderInMainPage.update(menuItemHeaderInMainPage); }

    @Transactional
    public List<MenuItemHeaderInMainPage> getAll() {
        return daoMenuItemHeaderInMainPage.getAll();
    }

    @Transactional
    public Map<MenuItemHeaderInMainPage,List<MenuItemHeaderInMainPage>> getAllHeadersMainPage() {
        return daoMenuItemHeaderInMainPage.getAllHeadersMainPage();
    }

    @Transactional
    public List<MenuItemHeaderInMainPage> getMinorHeadersByMainHeader(long idMainHeader) {
        return daoMenuItemHeaderInMainPage.getMinorHeadersByMainHeader(idMainHeader);
    }

    @Transactional
    public MenuItemHeaderInMainPage findItemById(long id) {
        return daoMenuItemHeaderInMainPage.findItemById(id);
    }

    @Transactional
    public List<MenuItemHeaderInMainPage> getCharacterUniversity() {
        return daoMenuItemHeaderInMainPage.getCharacterUniversity();
    }

    public List<MenuItemHeaderInMainPage> findItemByKeyWord(String keyWord) {
        return daoMenuItemHeaderInMainPage.findItemByKeyWord(keyWord);
    }
}
