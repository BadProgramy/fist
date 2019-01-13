package website.psuti.fist.constant;

//Этот файл нельзя менять без ведома разработчика, так как от их имени зависит управление html контентом!!!
//Добавлять можно
public enum MainPageConstant {
    NO_FOTO("Нет ФОТО", "images/no_foto.png"),
    PHONE(77L),
    EMAIL(76L),
    LOGOTIP_PSUTI("Логотип ПГУТИ", 1L),
    LOGOTIP_FIST("Логотип ФИСТ", 40L),
    SLIDER_1("Слайдер №1", 1L),
    SLIDER_2("Слайдер №2", 1L),
    SLIDER_3("Слайдер №3", 1L),
    ITEM_HEADER_PICTURE_SPLIT("Разделитель загаловка", 6L),
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
    MAP_LOCATION(65L),
    CONTEXT1(66L),
    CONTEXT2(67L),
    FOOTER_MAIN_PAGE(68L),
    CHARACTER_UNIVERSITY(60L, "character university");


    private long id;
    private String name;
    private String url;
    private Long keyPicture;
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

    MainPageConstant(String name, Long keyPicture) {
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

    public Long getKeyPicture() {
        return keyPicture;
    }

    public void setKeyPicture(Long keyPicture) {
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
