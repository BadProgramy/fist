package website.psuti.fist.constant;

import website.psuti.fist.model.KeyPicture;

//Этот файл нельзя менять без ведома разработчика, так как от их имени зависит управление html контентом!!!
//Добавлять можно
public enum MainPageConstant {
    NO_FOTO("Нет ФОТО", "images/no_foto.png"),
    PHONE(77L),
    EMAIL(76L),
    LOCATION(96L),
    LOGOTIP_PSUTI(1L),
    LOGOTIP_FIST(40L),
    SLIDER_1(3),
/*    SLIDER_2("Слайдер №2", KeyPicture.OTHER),
    SLIDER_3("Слайдер №3", KeyPicture.OTHER),*/
    ITEM_HEADER_PICTURE_SPLIT(6L),
    MOBILE_MENU(44L),
    HEADER_NEWS(45L),
    HEADER_ACTUAL_NEWS(73),
    HEADER_NEWS_PSUTI_FIST(74),
    BUTTON_NEWS(46L),
    HEADER_EDUCATIONAL_PROCESS(47L),
    SOCIAL_NETWORKS(56L),
    ANONS_AND_DECLORATIONS(57L),
    FOTO_GALLERY(58L),
    STUDENT_IT_CLUB(59L),
    BEST_STUDENT(64L),
    CONTEXT_1_FOOTER(65L),
    CONTEXT_2_FOOTER(66L),
    CONTEXT_2_1_FOOTER(72L),
    CONTEXT_2_2_FOOTER(80L),
    CONTEXT_3_FOOTER(67L),
    CONTEXT_3_1_FOOTER(70L),
    CONTEXT_3_2_FOOTER(71L),
    CONTEXT_3_3_FOOTER(81L),
    CONTEXT_4_FOOTER(69L),
    CONTEXT_4_1_FOOTER(82L),
    CONTEXT_4_2_FOOTER(83L),
    CONTEXT_4_3_FOOTER(84L),
    CONTEXT_4_4_FOOTER(85L),
    CONTEXT_4_5_FOOTER(86L),
    CONTEXT_4_6_FOOTER(87L),
    DIALOG_CONTEXT_1(88L),
    DIALOG_CONTEXT_1_1(89L),
    DIALOG_CONTEXT_2(90L),
    DIALOG_CONTEXT_2_1(91L),
    DIALOG_CONTEXT_3(92L),
    DIALOG_CONTEXT_3_1(93L),
    DIALOG_CONTEXT_4(94L),
    DIALOG_CONTEXT_4_1(95L),
    FOOTER_MAIN_PAGE(68L),
    CHARACTER_UNIVERSITY(60L, "character university"),
    DEAN_TEAM_NAME(97),
    DEPARTMENT(98),
    HEADERS(-1, "Header"),
    LABEL_HEADER(-1, "Label"),
    EDUCATION_PROCESS(-1, "EducationProcess"),
    NAVIGATION(-1, "Navigation");

    private long id;
    private String name;
    private String url;
    private KeyPicture keyPicture;
    private String keyWord;

    MainPageConstant() {
    }

    MainPageConstant(long id, String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
    }

    MainPageConstant(long id) {
        this.id = id;
    }

    MainPageConstant(String name) {
        this.name = name;
    }

    MainPageConstant(String name, KeyPicture keyPicture) {
        this.name = name;
        this.keyPicture = keyPicture;
    }

    MainPageConstant(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyPicture getKeyPicture() {
        return keyPicture;
    }

    public void setKeyPicture(KeyPicture keyPicture) {
        this.keyPicture = keyPicture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

}
