package website.psuti.fist.constant;

public enum  UrlForSearch {
    URL_MAIN("/main"),
    URL_FACULTY("/about/faculty"),
    URL_ACADEMIC_SOVIET("/faculty/academicSoviet"),
    URL_DEAN_TEAM("/deanTeam"),
    URL_CATHEDRAS("/faculty/cathedras"),
    URL_DIPLOMAS("/faculty/diplomasPhoto"),
    URL_GRADUATES("/faculty/graduates"),
    URL_COMMISSION_FIST("/faculty/commissionsFIST"),
    URL_MAIN_TREND("/mainTrend"),
    URL_COST_EDUCATION("/abitur/costEducation"),
    URL_RIGHT_AND_OBLIGATIONS("/students/rightAndObligations"),
    URL_GROUP_CURATORS("/students/groupCurators"),
    URL_RESULT_OF_CONTROL("/resultOfControl"),
    URL_GRAD_STUDENTS("/gradStudents"),
    URL_INTERIM_CONTROL("/interimControl"),
    URL_CONTACT("/contacts"),
    URL_NEWS_BLOG("/newsBlog"),
    URL_LIST_GROUP("/groupLists");


    private static final String URL_SITE = "http://109.124.244.164:8081";

    private String api;

    UrlForSearch(String api) {
        this.api = api;
    }

    public static String getUrlSite() {
        return URL_SITE;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public static String[] getListURL() {
        String[] result = new String[UrlForSearch.values().length];
        for (int i = 0; i < result.length; i++) {
            result[i] = UrlForSearch.URL_SITE + UrlForSearch.values()[i].getApi();
        }
        return result;
    }
}
