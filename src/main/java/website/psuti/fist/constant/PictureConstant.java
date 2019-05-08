package website.psuti.fist.constant;

public enum PictureConstant {
    FAVICON(2L),
    COUNT_DIPLOMAS_FOR_OUTPUT(20),
    COUNT_PICTURES_FOR_OUTPUT(20);

    private long id;
    private int count;

    PictureConstant(long id) {
        this.id = id;
    }

    PictureConstant(int count) {
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
