package website.psuti.fist.model;

public enum KeyPicture {
    BEST_STUDENT("Лучший студент"),
    TOPIC_FACULTY("Новость факультета"),
    DEAN_TEAM("Состав деканата"),
    DEPARTMENT("Состав кафедры"),
    OTHER("Другое");

    private String name;

    KeyPicture(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
