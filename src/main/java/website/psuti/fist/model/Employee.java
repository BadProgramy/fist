package website.psuti.fist.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long idDepartment;
    private long idPictureMajor;
    private long idPictureMinor;
    private String nameDepartment;
    private long pinNumber;
    private String name;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String post;
    private String phone;
    private String email;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String qualificationDetailed;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String qualificationBriefly;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String characteristic;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String curator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(long idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public long getIdPictureMajor() {
        return idPictureMajor;
    }

    public void setIdPictureMajor(long idPictureMajor) {
        this.idPictureMajor = idPictureMajor;
    }

    public long getIdPictureMinor() {
        return idPictureMinor;
    }

    public void setIdPictureMinor(long idPictureMinor) {
        this.idPictureMinor = idPictureMinor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualificationDetailed() {
        return qualificationDetailed;
    }

    public void setQualificationDetailed(String qualificationDetailed) {
        this.qualificationDetailed = qualificationDetailed;
    }

    public String getQualificationBriefly() {
        return qualificationBriefly;
    }

    public void setQualificationBriefly(String qualificationBriefly) {
        this.qualificationBriefly = qualificationBriefly;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public long getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(long pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", idDepartment=" + idDepartment +
                ", idPictureMajor=" + idPictureMajor +
                ", idPictureMinor=" + idPictureMinor +
                ", nameDepartment='" + nameDepartment + '\'' +
                ", pinNumber=" + pinNumber +
                ", name='" + name + '\'' +
                ", post='" + post + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qualificationDetailed='" + qualificationDetailed + '\'' +
                ", qualificationBriefly='" + qualificationBriefly + '\'' +
                ", characteristic='" + characteristic + '\'' +
                ", curator='" + curator + '\'' +
                '}';
    }
}
