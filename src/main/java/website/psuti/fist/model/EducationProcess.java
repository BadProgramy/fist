package website.psuti.fist.model;

import javax.persistence.*;

@Entity
public class EducationProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long idMenuItemHeaderInMainPageLeft;
    private long idPictureLeft;
    private long idMenuItemHeaderInMainPageRight;
    private long idPictureRight;

    @Transient
    private MenuItemHeaderInMainPage menuItemHeaderInMainPageLeft;

    @Transient
    private MenuItemHeaderInMainPage menuItemHeaderInMainPageRight;

    @Transient
    private Pictures picturesLeft;

    @Transient
    private Pictures picturesRight;


    public EducationProcess() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMenuItemHeaderInMainPageLeft() {
        return idMenuItemHeaderInMainPageLeft;
    }

    public void setIdMenuItemHeaderInMainPageLeft(long idMenuItemHeaderInMainPageLeft) {
        this.idMenuItemHeaderInMainPageLeft = idMenuItemHeaderInMainPageLeft;
    }

    public long getIdPictureLeft() {
        return idPictureLeft;
    }

    public void setIdPictureLeft(long idPictureLeft) {
        this.idPictureLeft = idPictureLeft;
    }

    public long getIdMenuItemHeaderInMainPageRight() {
        return idMenuItemHeaderInMainPageRight;
    }

    public void setIdMenuItemHeaderInMainPageRight(long idMenuItemHeaderInMainPageRight) {
        this.idMenuItemHeaderInMainPageRight = idMenuItemHeaderInMainPageRight;
    }

    public long getIdPictureRight() {
        return idPictureRight;
    }

    public void setIdPictureRight(long idPictureRight) {
        this.idPictureRight = idPictureRight;
    }

    public MenuItemHeaderInMainPage getMenuItemHeaderInMainPageLeft() {
        return menuItemHeaderInMainPageLeft;
    }

    public void setMenuItemHeaderInMainPageLeft(MenuItemHeaderInMainPage menuItemHeaderInMainPageLeft) {
        this.menuItemHeaderInMainPageLeft = menuItemHeaderInMainPageLeft;
    }

    public MenuItemHeaderInMainPage getMenuItemHeaderInMainPageRight() {
        return menuItemHeaderInMainPageRight;
    }

    public void setMenuItemHeaderInMainPageRight(MenuItemHeaderInMainPage menuItemHeaderInMainPageRight) {
        this.menuItemHeaderInMainPageRight = menuItemHeaderInMainPageRight;
    }

    public Pictures getPicturesLeft() {
        return picturesLeft;
    }

    public void setPicturesLeft(Pictures picturesLeft) {
        this.picturesLeft = picturesLeft;
    }

    public Pictures getPicturesRight() {
        return picturesRight;
    }

    public void setPicturesRight(Pictures picturesRight) {
        this.picturesRight = picturesRight;
    }
}
