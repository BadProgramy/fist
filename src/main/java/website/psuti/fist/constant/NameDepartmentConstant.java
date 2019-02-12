package website.psuti.fist.constant;

public enum NameDepartmentConstant {
    STRUCTURE_DEAN_TEAM("Состав деканата");

    private String name;

    NameDepartmentConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
