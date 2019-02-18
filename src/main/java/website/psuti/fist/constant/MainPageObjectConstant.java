package website.psuti.fist.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MainPageObjectConstant {

    private static List<NameTableBD> checkModelAndView;

    static {
        if (checkModelAndView == null) {
            checkModelAndView = new ArrayList<>();
        }
    }

    public static void addCheck(NameTableBD nameTableBD) {
        checkModelAndView.add(nameTableBD);
        //modelAndViewConfiguration.initModelAndView();
    }

    public static List<NameTableBD> getCheckModelAndView() {
        return checkModelAndView;
    }

    public static void clearCheckModel() {
        checkModelAndView.clear();
    }
}
