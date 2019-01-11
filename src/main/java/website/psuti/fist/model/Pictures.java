package website.psuti.fist.model;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Entity
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /*@Column(unique = true)*/
    private String namePicture;
    private long idPage;
    private String urlPicture;
    private LocalDate date;
    private long keyPicture; //

    @Column(columnDefinition="longblob")
    private byte[] pictureFile;

    public Pictures() {
        date = LocalDate.now();
    }

    public Pictures(String namePicture, String urlPicture, LocalDate date) {
        this.namePicture = namePicture;
        this.urlPicture = urlPicture;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }

    public long getIdPage() {
        return idPage;
    }

    public void setIdPage(long idPage) {
        this.idPage = idPage;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getKeyPicture() {
        return keyPicture;
    }

    public void setKeyPicture(long keyPicture) {
        this.keyPicture = keyPicture;
    }

    public byte[] getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(byte[] pictureFile) {
        this.pictureFile = pictureFile;
    }
}
