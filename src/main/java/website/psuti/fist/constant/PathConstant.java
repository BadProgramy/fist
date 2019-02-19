package website.psuti.fist.constant;

import java.io.IOException;

public enum PathConstant {
    SAVE_PICTURE(""),//"src\\main\\resources\\downloadPictures\\
    SAVE_PICTURE_NEWS_FACULTY(""),//downloadPictures\newsFaculty\
    SAVE_PICTURE_BEST_STUDENT(""),//downloadPictures\bestStudents\
    SAVE_FILE("");//files\

    private String path;

    PathConstant(String path) {
        this.path = path;
    }

    public String getPath() {
      /*  try {
            System.out.println("----------------Канонический-----------"+new java.io.File(".").getCanonicalPath());
            System.out.println("----------------Абсолютный-----------"+new java.io.File(".").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
