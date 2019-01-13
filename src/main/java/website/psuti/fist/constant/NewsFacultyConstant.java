package website.psuti.fist.constant;

public enum NewsFacultyConstant {
    COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE(10),
    COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT(5);

    private int count; //количество выводдимой информации для запроса к бд

    NewsFacultyConstant(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
