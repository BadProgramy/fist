package website.psuti.fist.model;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String uniqueNameUUID;
    @Enumerated(EnumType.STRING)
    private Extension extension;
    private LocalDate date;

    @Transient
    private MultipartFile file;

    @Transient
    private String dateStringLocalDate;

    public File() {
        date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueNameUUID() {
        return uniqueNameUUID;
    }

    public void setUniqueNameUUID(String uniqueNameUUID) {
        this.uniqueNameUUID = uniqueNameUUID;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDateStringLocalDate() {
        return dateStringLocalDate;
    }

    public void setDateStringLocalDate(String dateStringLocalDate) {
        this.dateStringLocalDate = dateStringLocalDate;
    }
}
