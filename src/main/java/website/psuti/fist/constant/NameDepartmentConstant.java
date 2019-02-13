package website.psuti.fist.constant;

public enum NameDepartmentConstant {
    STRUCTURE_DEAN_TEAM("Деканат"),
    DEPARTMENT("Кафедра");


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
