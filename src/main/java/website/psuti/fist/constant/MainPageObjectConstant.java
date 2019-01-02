package website.psuti.fist.constant;

import website.psuti.fist.model.Pictures;

import java.time.LocalDate;

public abstract class MainPageObjectConstant {
    public static final Pictures NO_FOTO = new Pictures(MainPageConstant.NO_FOTO.getName(),
            MainPageConstant.NO_FOTO.getUrl(),
            LocalDate.now());

}
