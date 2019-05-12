package website.psuti.fist.constant;

public enum HtmlStructurePageConstant {
    COMMISSIONS_FIST(1L),
    CONTACTS(2L),
    COST_EDUCATION(3L),
    FACULTY(4L),
    GRAD_STUDENTS(5L),
    GRADUATES(6L),
    GROUP_LISTS(7L),
    INNOVATIKA(8L),
    INTERIM_CONTROL(9L),
    IST(10L),
    IVT(11L),
    MAIN_TREND(12L),
    MATOB(13L),
    PI(14L),
    PRIKLAD_INFO(15L),
    RESULT_OF_CONTROL(16L),
    RIGHT_AND_OBLIGATION(17L),
    RSO(18L),
    UITS(20L);


    private Long id;

    HtmlStructurePageConstant(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
