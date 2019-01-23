package website.psuti.fist.model;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class BestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String groupInUniversity;
    private String url;
    private long idPicture;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String characteristic;

    @Transient
    private MultipartFile pictureFile;

    @Transient
    private Pictures picture;

    public BestStudent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(long idPicture) {
        this.idPicture = idPicture;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupInUniversity() {
        return groupInUniversity;
    }

    public void setGroupInUniversity(String groupInUniversity) {
        this.groupInUniversity = groupInUniversity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public Pictures getPicture() {
        return picture;
    }

    public void setPicture(Pictures picture) {
        this.picture = picture;
    }
}
