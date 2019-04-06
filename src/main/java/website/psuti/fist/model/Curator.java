package website.psuti.fist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Curator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupInUniversity;
    private String nameCuratorTeacher;
    private String nameDepartment;//Пример ИСТ
    private String phone;
    private String nameCuratorStudentAndGroup;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupInUniversity() {
        return groupInUniversity;
    }

    public void setGroupInUniversity(String groupInUniversity) {
        this.groupInUniversity = groupInUniversity;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameCuratorTeacher() {
        return nameCuratorTeacher;
    }

    public void setNameCuratorTeacher(String nameCuratorTeacher) {
        this.nameCuratorTeacher = nameCuratorTeacher;
    }

    public String getNameCuratorStudentAndGroup() {
        return nameCuratorStudentAndGroup;
    }

    public void setNameCuratorStudentAndGroup(String nameCuratorStudentAndGroup) {
        this.nameCuratorStudentAndGroup = nameCuratorStudentAndGroup;
    }
}
