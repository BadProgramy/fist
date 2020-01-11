package website.psuti.fist.constant;

import java.util.ArrayList;
import java.util.List;

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
