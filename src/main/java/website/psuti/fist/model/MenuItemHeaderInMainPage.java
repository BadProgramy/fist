package website.psuti.fist.model;

import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MenuItemHeaderInMainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //Если -1, то это в главных загаловках (Менюшки), у которых нет родителей
    //Если 0, то это просто названия (загаловки), какого либо контента т.е различные надписи на старнице
    @JoinColumnOrFormula(column = @JoinColumn(name="idMenuItemParentHeaderInMainPage", referencedColumnName="id"))
    private long idMenuItemParentHeaderInMainPage;
    private String name;
    //level это уровень вложенныости
    private int level;
    //pinNumber это последовательность по которым выводится загаловки для главного меню
    private int pinNumber;
    //urlHref возможная ссылка
    private String urlHref;
    //keyWord ключевое слово для поиска элементов
    private String keyWord;
    /*@ElementCollection(targetClass = MenuItemHeaderInMainPage.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "menuItemHeaderInMainPage_menuItemHeaderInMainPage", joinColumns = @JoinColumn(name = "menuItemHeaderInMainPage_id"))
    @Enumerated(EnumType.STRING)
    private List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages;*/

    public MenuItemHeaderInMainPage() {
        /*menuItemHeaderInMainPages = new ArrayList<>();*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   /* public List<MenuItemHeaderInMainPage> getMenuItemHeaderInMainPages() {
        return menuItemHeaderInMainPages;
    }*/

   /* public void setMenuItemHeaderInMainPages(List<MenuItemHeaderInMainPage> menuItemHeaderInMainPages) {
        this.menuItemHeaderInMainPages = menuItemHeaderInMainPages;
    }

    public void addChildMenuItem(MenuItemHeaderInMainPage menuItemHeaderInMainPage) {
        menuItemHeaderInMainPages.add(menuItemHeaderInMainPage);
    }*/

    public long getIdMenuItemParentHeaderInMainPage() {
        return idMenuItemParentHeaderInMainPage;
    }

    public void setIdMenuItemParentHeaderInMainPage(long idMenuItemParentHeaderInMainPage) {
        this.idMenuItemParentHeaderInMainPage = idMenuItemParentHeaderInMainPage;
    }

    /*public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUrlHref() {
        return urlHref;
    }

    public void setUrlHref(String urlHref) {
        this.urlHref = urlHref;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
