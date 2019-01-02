package website.psuti.fist.dao.menuItemHeaderInMainPage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.dao.Factory;
import website.psuti.fist.model.MenuItemHeaderInMainPage;

import java.util.*;

@Primary
@Repository
public class DAOMenuItemHeaderInMainPageImpl implements DAOMenuItemHeaderInMainPage {

    @Autowired
    private Factory factory;

    @Override
    public List<MenuItemHeaderInMainPage> getAll() {
        List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages;
        SqlSession session = factory.getFactory().openSession();
        try {
            menuItemHeaderInMainPages = session.selectList("MenuItemHeaderInMainPage.selectAll");
        } finally {
            session.close();
        }
        return menuItemHeaderInMainPages;
    }

    @Override
    public int insert(MenuItemHeaderInMainPage menuItemHeaderInMainPage) {
        return 0;
    }

    @Override
    public void update(MenuItemHeaderInMainPage menuItemHeaderInMainPage) {

    }

    @Override
    public void delete(int id) {

    }

    public List<MenuItemHeaderInMainPage> getOnlyMainHeaders() {
        List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages;
        SqlSession session = factory.getFactory().openSession();
        try {
            menuItemHeaderInMainPages = session.selectList("MenuItemHeaderInMainPage.selectOnlyMainHeaders");
        } finally {
            session.close();
        }
        return menuItemHeaderInMainPages;
    }

    @Override
    public List<MenuItemHeaderInMainPage> getMinorHeadersByMainHeader(long idMainHeader) {
        List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages;
        SqlSession session = factory.getFactory().openSession();
        try {
            menuItemHeaderInMainPages = session.selectList("MenuItemHeaderInMainPage.selectMinorHeadersByIdMainHeader", idMainHeader);
        } finally {
            session.close();
        }
        return menuItemHeaderInMainPages;
    }

    @Override
    public Map<MenuItemHeaderInMainPage, List<MenuItemHeaderInMainPage>> getAllHeadersMainPage() {
        Map<MenuItemHeaderInMainPage, List<MenuItemHeaderInMainPage>> headersInMainPage = new LinkedHashMap<>();
        List<MenuItemHeaderInMainPage> mainHeaders = getOnlyMainHeaders();

        for (MenuItemHeaderInMainPage menuItemHeaderInMainPage: mainHeaders) {
            headersInMainPage.put(menuItemHeaderInMainPage, getMinorHeadersByMainHeader(menuItemHeaderInMainPage.getId()));
        }
        return headersInMainPage;
    }

    @Override
    public MenuItemHeaderInMainPage findItemById(long id) {
        MenuItemHeaderInMainPage menuItemHeaderInMainPage;
        SqlSession session = factory.getFactory().openSession();
        try {
            menuItemHeaderInMainPage = session.selectOne("MenuItemHeaderInMainPage.findItemById", id);
        } finally {
            session.close();
        }
        return menuItemHeaderInMainPage;
    }

    @Override
    public Map<String, Integer> getCharacterUniversity() {
        Map<String, Integer> menuItem = new LinkedHashMap<>();
        List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages;
        SqlSession session = factory.getFactory().openSession();
        try {
            menuItemHeaderInMainPages = session.selectList("MenuItemHeaderInMainPage.findCharacterUniversity", MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord());
            for (MenuItemHeaderInMainPage map: menuItemHeaderInMainPages) {
                menuItem.put(map.getName(), map.getLevel());
            }
        } finally {
            session.close();
        }
        return menuItem;
    }

    /*//Пришлось извратиться, другой выход с созданием для него отдельного класса не хочется. Можно было в виде JSON сделать
    //List<Map
    //         <Map <MenuItemHeaderInMainPage, Pictures>, Map<MenuItemHeaderInMainPage, Pictures>
    //    >    >
    @Override
    public List<Map<Map<MenuItemHeaderInMainPage, Pictures>, Map<MenuItemHeaderInMainPage, Pictures>>> educationProcess() {
        List<Map<Map<MenuItemHeaderInMainPage, Pictures>, Map<MenuItemHeaderInMainPage, Pictures>>> menuEducationProcess = new ArrayList<>();

        Map<Map<MenuItemHeaderInMainPage, Pictures>, Map<MenuItemHeaderInMainPage, Pictures>> fullItem = new HashMap<>();
        Map<MenuItemHeaderInMainPage, Pictures> leftItem = new HashMap<>();
        Map<MenuItemHeaderInMainPage, Pictures> rightItem = new HashMap<>();
        SqlSession session = factory.getFactory().openSession();
        try {
            menuEducationProcess = session.selectList("MenuItemHeaderInMainPage.findItemById");
        } finally {
            session.close();
        }
        return menuEducationProcess;
    }*/
}
