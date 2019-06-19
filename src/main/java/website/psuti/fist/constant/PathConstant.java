package website.psuti.fist.constant;

import java.io.IOException;

public enum PathConstant {
    SAVE_PICTURE(""),//"src\\main\\resources\\downloadPictures\\
    SAVE_PICTURE_NEWS_FACULTY(""),//src\main\resources\downloadPictures\newsFaculty\
    SAVE_PICTURE_BEST_STUDENT(""),//src\main\resources\downloadPictures\bestStudents\
    HTML_FILE_FOR_USER_ADD_CMS("cms.html"),//src\main\resources\downloadPictures\sendEmailHtml\cms.html
    HTML_FILE_FOR_USER_SUBSCRIBE("subscriber.html"),//src\main\resources\downloadPictures\sendEmailHtml\subscriber.html
    SAVE_FILE("fist/files/");//files\

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
