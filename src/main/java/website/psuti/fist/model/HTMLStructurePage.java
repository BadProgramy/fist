package website.psuti.fist.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class HTMLStructurePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String htmlCode;
    private String namePage;
    @Enumerated(EnumType.STRING)
    private TypeHtmlCode typeHtmlCode;

    public HTMLStructurePage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public String getNamePage() {
        return namePage;
    }

    public void setNamePage(String namePage) {
        this.namePage = namePage;
    }

    public TypeHtmlCode getTypeHtmlCode() {
        return typeHtmlCode;
    }

    public void setTypeHtmlCode(TypeHtmlCode typeHtmlCode) {
        this.typeHtmlCode = typeHtmlCode;
    }

    @Override
    public String toString() {
        return "HTMLStructurePage{" +
                "id=" + id +
                ", htmlCode='" + htmlCode + '\'' +
                ", namePage='" + namePage + '\'' +
                ", typeHtmlCode=" + typeHtmlCode +
                '}';
    }
}
