package website.psuti.fist.model;

public enum TypeHtmlCode {
    BODY("Тело"),
    HEAD("Заголовок");

    private String type;

    TypeHtmlCode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
