package website.psuti.fist.constant;

public enum NameTableBD {
    DEPARTMENT("department"),
    EMPLOYEE("employee"),
    BEST_STUDENT("best_student"),
    EDUCATION_PROCESS("education_process"),
    MAIN_PAGE("main_page"),
    MENU_ITEM_HEADER_IN_MAIN_PAGE("menu_item_header_in_main_page"),
    NEWS_OF_FACULTY("news_of_faculty"),
    PICTURES("pictures"),
    USERS("users"),
    USERS_ROLE("users_role"),
    FILE("files");

    private String name;

    NameTableBD(String name) {
        this.name = name;
    }

    public NameTableBD getNameTableBD(String name) {
        for (NameTableBD tableBD: NameTableBD.values()) {
            if (tableBD.getName().equals(name)) return tableBD;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
