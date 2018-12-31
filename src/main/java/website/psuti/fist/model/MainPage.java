package website.psuti.fist.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String headerPhone;
    private String headerEmail;

    public MainPage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeaderPhone() {
        return headerPhone;
    }

    public void setHeaderPhone(String headerPhone) {
        this.headerPhone = headerPhone;
    }

    public String getHeaderEmail() {
        return headerEmail;
    }

    public void setHeaderEmail(String headerEmail) {
        this.headerEmail = headerEmail;
    }
}
