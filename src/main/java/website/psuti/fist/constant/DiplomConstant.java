package website.psuti.fist.constant;

public enum DiplomConstant {
    COUNT_DIPLOMAS_FOR_OUTPUT(20);

    private int count; //количество выводдимой информации для запроса к бд

    DiplomConstant(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
