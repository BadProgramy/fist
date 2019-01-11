package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.model.MainPage;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private EducationProcessService educationProcessService;

    @Autowired
    private BestStudentService bestStudentService;

    @Autowired
    private NewsFacultyService newsFacultyService;

    /*@Autowired
    private PicturesService picturesService;*/

    @RequestMapping("")
    public String main(Model model) {
        return mainPage(model);
    }

    @RequestMapping("/main")
    public String mainPage(Model model) {
        List<MainPage> temp = mainPageService.getAll();
        model.addAttribute("mainPage", temp.get(temp.size() - 1)); //так как позиция 1ая начинается с 0
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());


        model.addAttribute("logotipFIST", picturesService.findPictureByName(MainPageConstant.LOGOTIP_FIST.getName()));

        List<Pictures> picturesForSlider = picturesService.findPicturesByKey(MainPageConstant.SLIDER_1.getKeyPicture());
        model.addAttribute("slider", picturesForSlider);//слайдеры на месте вывода список направлений подготовки

        model.addAttribute("ItemHeaderPictureSplit", picturesService.findPictureByName(MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getName()));
        model.addAttribute("menuItemMobile", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MOBILE_MENU.getId()));//Меню

        model.addAttribute("ItemHeader1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS.getId()));//Новости
        model.addAttribute("ItemHeader1_1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Актуальное на сегодня
        model.addAttribute("ItemButton1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BUTTON_NEWS.getId()));//Клавиша Больше новостей
        model.addAttribute("ItemHeader2", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_EDUCATIONAL_PROCESS.getId()));//учебный процесс
        model.addAttribute("ItemHeader3", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.SOCIAL_NETWORKS.getId()));//Мы в социальных сетях
        model.addAttribute("ItemHeader4", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.ANONS_AND_DECLORATIONS.getId()));//Анонсы и объяявления
        model.addAttribute("ItemHeader5", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOTO_GALLERY.getId()));//Фотогалерея от инстаграмма
        model.addAttribute("ItemHeader6", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.STUDENT_IT_CLUB.getId()));//Студенческий it club
        model.addAttribute("ItemHeader7", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BEST_STUDENT.getId()));//Лучшие студенты
        model.addAttribute("ItemHeader8", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MAP_LOCATION.getId()));//Расположение
        model.addAttribute("ItemHeader9", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT1.getId()));//контекст 1
        model.addAttribute("ItemHeader10", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT2.getId()));//контекст 2
        model.addAttribute("ItemHeader11", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page

       // model.addAttribute("locations", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.MAP_LOCATION.getId()));
        model.addAttribute("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
        //model.addAttribute("context2", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT2.getId()));
        model.addAttribute("educationProcess", educationProcessService.educationProcess());
        model.addAttribute("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
        model.addAttribute("bestStudents", bestStudentService.filledBestStudent());
        model.addAttribute("logotipPSUTI", picturesService.findPictureByName(MainPageConstant.LOGOTIP_PSUTI.getName()));
        model.addAttribute("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
        model.addAttribute("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/main/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture) {
        return picturesService.findPictureById(idPicture).getPictureFile();
    }

    @RequestMapping("/newsBlog")
    public String newsBlog(){return "newsBlog"; }
}
