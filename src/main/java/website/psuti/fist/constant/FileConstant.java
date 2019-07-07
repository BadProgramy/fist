package website.psuti.fist.constant;

public enum FileConstant {
    COUNT_FILE_FOR_OUTPUT_PAGE(10);

    private int count; //количество выводдимой информации для запроса к бд

    FileConstant(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
