package website.psuti.fist.model;

import org.hibernate.annotations.Type;
import org.hibernate.type.TextType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class NewsOfFaculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String heading;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    private long idLeftPicture;
    private long idRightPicture;

    private LocalDate date;

    @Transient
    private Pictures leftPicture;

    @Transient
    private Pictures rightPicture;

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
        return text;//text.replaceAll("<br>", "\r\n").replaceAll("<br />", "\r\n");
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIdLeftPicture() {
        return idLeftPicture;
    }

    public void setIdLeftPicture(long idLeftPicture) {
        this.idLeftPicture = idLeftPicture;
    }

    public long getIdRightPicture() {
        return idRightPicture;
    }

    public void setIdRightPicture(long idRightPicture) {
        this.idRightPicture = idRightPicture;
    }

    public Pictures getLeftPicture() {
        return leftPicture;
    }

    public void setLeftPicture(Pictures leftPicture) {
        this.leftPicture = leftPicture;
    }

    public Pictures getRightPicture() {
        return rightPicture;
    }

    public void setRightPicture(Pictures rightPicture) {
        this.rightPicture = rightPicture;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void update(NewsOfFaculty newsOfFaculty) {
        if (newsOfFaculty.getDate()!=null)
            date = newsOfFaculty.getDate();
        if (newsOfFaculty.getHeading()!=null)
            heading = newsOfFaculty.getHeading();
        if (newsOfFaculty.getText()!=null)
            text = newsOfFaculty.getText();
        if (newsOfFaculty.getIdLeftPicture()!=0 || newsOfFaculty.getIdLeftPicture()!=-1)
            idLeftPicture = newsOfFaculty.getIdLeftPicture();
        if (newsOfFaculty.getIdRightPicture()!=0 || newsOfFaculty.getIdRightPicture()!=-1)
            idRightPicture = newsOfFaculty.getIdRightPicture();
    }
}
