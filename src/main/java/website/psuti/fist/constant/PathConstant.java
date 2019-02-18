package website.psuti.fist.constant;

public enum PathConstant {
    SAVE_PICTURE("src\\main\\resources\\downloadPictures\\"),
    SAVE_FILE("src\\main\\resources\\files\\");

    private String path;

    PathConstant(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
