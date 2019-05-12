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
}