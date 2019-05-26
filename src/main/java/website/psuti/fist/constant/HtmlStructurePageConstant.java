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
    BI(19L),
    UITS(20L),
    ACADEMIC_SOVIET(21L),
    BI_HEAD(22L),
    CANDIDATES_FOR_EXPULSION_HEAD(23L),
    CATHEDRAS_HEAD(24L),
    CHARACTERISTIC_EMPLOYEE_HEAD(25L),
    COMISSIONS_FIST_HEAD(26L),
    CONTACTS_HEAD(27L),
    COST_EDUCATION_HEAD(28L),
    DEAN_TEAM_HEAD(29L),
    DIPLOMAS_PHOTO_HEAD(30L),
    ENABLED_ACCOUNT_HEAD(31L),
    FACULTY_HEAD(32L),
    GRAD_STUDENTS_HEAD(33L),
    GRADUATES_HEAD(34L),
    GROUP_CURATORS_HEAD(35L),
    GROUP_LISTS_HEAD(36L),
    INDEX_HEAD(37L),
    INNOVATIKA_HEAD(38L),
    INTERIM_CONTROL_HEAD(39L),
    IST_HEAD(40L),
    IVT_HEAD(41L),
    MAIN_TREND_HEAD(42L),
    MATOB_HEAD(43L),
    NEWS_BLOG_HEAD(44L),
    PI_HEAD(45L),
    PRIKLAD_INFO_HEAD(46L),
    RESULT_OF_CONTROL_HEAD(47L),
    RIGHT_AND_OBLIGATIONS_HEAD(48L),
    RSO_HEAD(49L),
    SEARCH_HEAD(50L),
    TOPIC_HEAD(51L),
    UITS_HEAD(52L),
    ACADEMIC_SOVIET_HEAD(53L)
    ;


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
