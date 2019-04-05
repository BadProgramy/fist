package website.psuti.fist.model;

import org.hibernate.annotations.Type;
import website.psuti.fist.constant.NameDepartmentConstant;

import javax.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long idMainEmployee;
    private String address;
    private long idPictureIcon;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getIdMainEmployee() {
        return idMainEmployee;
    }

    public void setIdMainEmployee(long idMainEmployee) {
        this.idMainEmployee = idMainEmployee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getIdPictureIcon() {
        return idPictureIcon;
    }

    public void setIdPictureIcon(long idPictureIcon) {
        this.idPictureIcon = idPictureIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
