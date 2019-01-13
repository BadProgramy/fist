package website.psuti.fist.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MainPageObjectConstant {
    @Autowired
    private static MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @Autowired
    private static PicturesService picturesService;

    @Autowired
    private static EducationProcessService educationProcessService;

    @Autowired
    private static BestStudentService bestStudentService;

    @Autowired
    private static NewsFacultyService newsFacultyService;

    public static Map<Boolean, NameTableBD> checkModelAndView;
    static {
        if (checkModelAndView == null) {
            checkModelAndView = new HashMap<>();
        }
    }

    public static final Pictures NO_FOTO = new Pictures(MainPageConstant.NO_FOTO.getName(),
            MainPageConstant.NO_FOTO.getUrl(),
            LocalDate.now());

    private static ModelAndView model;



    public static ModelAndView getModel() {
        return model;
    }

}
