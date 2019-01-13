package website.psuti.fist.model;

import org.hibernate.annotations.Type;
import org.hibernate.type.TextType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Entity
public class NewsOfFaculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String heading;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    private long idPicture;

    private LocalDate date;
    @Transient
    private String dateStringLocalDate;

    @Transient
    private Pictures picture;

    @Transient
    private MultipartFile pictureFile;

    public NewsOfFaculty() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return text;
        /*if (text == null) return text;
        return text.replace("<br>", "\r\n")
                .replace("<br />", "\r\n");*/
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(long idPicture) {
        this.idPicture = idPicture;
    }

    public Pictures getPicture() {
        return picture;
    }

    public void setPicture(Pictures picture) {
        this.picture = picture;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDateStringLocalDate() {
        return dateStringLocalDate;
    }

    public void setDateStringLocalDate(String dateStringLocalDate) {
        this.dateStringLocalDate = dateStringLocalDate;
    }

    public void update(NewsOfFaculty newsOfFaculty) {
        if (newsOfFaculty.getDate()!=null)
            date = newsOfFaculty.getDate();
        if (newsOfFaculty.getHeading()!=null)
            heading = newsOfFaculty.getHeading();
        if (newsOfFaculty.getText()!=null)
            text = newsOfFaculty.getText();
        if (newsOfFaculty.getIdPicture()!=0 || newsOfFaculty.getIdPicture()!=-1)
            idPicture = newsOfFaculty.getIdPicture();
    }
}
