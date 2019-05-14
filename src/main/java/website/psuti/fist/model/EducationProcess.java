package website.psuti.fist.model;

import org.hibernate.annotations.Type;

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

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String leftMenuItemStyles;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String rightMenuItemStyles;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String leftPictureStyles;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String rightPictureStyles;

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

    public String getLeftMenuItemStyles() {
        return leftMenuItemStyles;
    }

    public void setLeftMenuItemStyles(String leftMenuItemStyles) {
        this.leftMenuItemStyles = leftMenuItemStyles;
    }

    public String getRightMenuItemStyles() {
        return rightMenuItemStyles;
    }

    public void setRightMenuItemStyles(String rightMenuItemStyles) {
        this.rightMenuItemStyles = rightMenuItemStyles;
    }

    public String getLeftPictureStyles() {
        return leftPictureStyles;
    }

    public void setLeftPictureStyles(String leftPictureStyles) {
        this.leftPictureStyles = leftPictureStyles;
    }

    public String getRightPictureStyles() {
        return rightPictureStyles;
    }

    public void setRightPictureStyles(String rightPictureStyles) {
        this.rightPictureStyles = rightPictureStyles;
    }

    @Override
    public String toString() {
        return "EducationProcess{" +
                "id=" + id +
                ", idMenuItemHeaderInMainPageLeft=" + idMenuItemHeaderInMainPageLeft +
                ", idPictureLeft=" + idPictureLeft +
                ", idMenuItemHeaderInMainPageRight=" + idMenuItemHeaderInMainPageRight +
                ", idPictureRight=" + idPictureRight +
                ", leftMenuItemStyles='" + leftMenuItemStyles + '\'' +
                ", rightMenuItemStyles='" + rightMenuItemStyles + '\'' +
                ", leftPictureStyles='" + leftPictureStyles + '\'' +
                ", rightPictureStyles='" + rightPictureStyles + '\'' +
                ", menuItemHeaderInMainPageLeft=" + menuItemHeaderInMainPageLeft +
                ", menuItemHeaderInMainPageRight=" + menuItemHeaderInMainPageRight +
                ", picturesLeft=" + picturesLeft +
                ", picturesRight=" + picturesRight +
                '}';
    }
}
