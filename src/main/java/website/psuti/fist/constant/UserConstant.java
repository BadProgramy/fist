package website.psuti.fist.constant;

public enum UserConstant {
    COUNT_USER_FOR_OUTPUT(1);

    private int count; //количество выводдимой информации для запроса к бд

    UserConstant(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
