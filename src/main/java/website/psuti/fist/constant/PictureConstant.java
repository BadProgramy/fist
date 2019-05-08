package website.psuti.fist.constant;

public enum PictureConstant {
    FAVICON(2L);

    private long id;

    PictureConstant(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
