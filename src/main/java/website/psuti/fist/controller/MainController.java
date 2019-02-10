package website.psuti.fist.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.constant.MainPageObjectConstant;
import website.psuti.fist.constant.NameTableBD;
import website.psuti.fist.constant.NewsFacultyConstant;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    //private final static Logger log = LoggerFactory.getLogger(Application.class);

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

    private ModelAndView modelAndView;
    private HashMap<Long, byte[]> picturesCache;


    private HashMap<Long, byte[]> initPicturesCache() {
        if (picturesCache == null) {
            picturesCache = new HashMap<>();
            for (Pictures pictures : picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
        return picturesCache;
    }

    private ModelAndView initModelAndView() {
        initPicturesCache();
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
            modelAndView = new ModelAndView("", "", "");
            List<MenuItemHeaderInMainPage> menuItems = menuItemHeaderInMainPagesService.getAll();
            List<Pictures> pictures = picturesService.getAll();

            modelAndView.addObject("email", getItemById(menuItems, MainPageConstant.EMAIL.getId())); //почта
            modelAndView.addObject("phone", getItemById(menuItems, MainPageConstant.PHONE.getId())); //телефон
            modelAndView.addObject("location", getItemById(menuItems, MainPageConstant.LOCATION.getId())); //адрес
            modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());

            modelAndView.addObject("logotipFIST", getItemById(pictures, MainPageConstant.LOGOTIP_FIST.getId()));
            modelAndView.addObject("slider", picturesService.findPicturesByKey(MainPageConstant.SLIDER_1.getKeyPicture()));//слайдеры на месте вывода список направлений подготовки

            modelAndView.addObject("ItemHeaderPictureSplit", getItemById(pictures, MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getId()));
            modelAndView.addObject("menuItemMobile", getItemById(menuItems, MainPageConstant.MOBILE_MENU.getId()));//Меню


            modelAndView.addObject("ItemHeader1", getItemById(menuItems, MainPageConstant.HEADER_NEWS.getId()));//Новости
            modelAndView.addObject("ItemHeader1_1", getItemById(menuItems, MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Актуальное на сегодня
            modelAndView.addObject("ItemButton1", getItemById(menuItems, MainPageConstant.BUTTON_NEWS.getId()));//Клавиша Больше новостей
            modelAndView.addObject("ItemHeader2", getItemById(menuItems, MainPageConstant.HEADER_EDUCATIONAL_PROCESS.getId()));//учебный процесс
            modelAndView.addObject("ItemHeader3", getItemById(menuItems, MainPageConstant.SOCIAL_NETWORKS.getId()));//Мы в социальных сетях
            modelAndView.addObject("ItemHeader4", getItemById(menuItems, MainPageConstant.ANONS_AND_DECLORATIONS.getId()));//Анонсы и объяявления
            modelAndView.addObject("ItemHeader5", getItemById(menuItems, MainPageConstant.FOTO_GALLERY.getId()));//Фотогалерея от инстаграмма
            modelAndView.addObject("ItemHeader6", getItemById(menuItems, MainPageConstant.STUDENT_IT_CLUB.getId()));//Студенческий it club
            modelAndView.addObject("ItemHeader7", getItemById(menuItems, MainPageConstant.BEST_STUDENT.getId()));//Лучшие студенты
            modelAndView.addObject("ItemHeader8", getItemById(menuItems, MainPageConstant.CONTEXT_1_FOOTER.getId()));//Расположение
            modelAndView.addObject("ItemHeader9", getItemById(menuItems, MainPageConstant.CONTEXT_2_FOOTER.getId()));//Подписка
            modelAndView.addObject("ItemHeader9_1", getItemById(menuItems, MainPageConstant.CONTEXT_2_1_FOOTER.getId()));//Введите свой email, чтобы ....
            modelAndView.addObject("ItemHeader9_2", getItemById(menuItems, MainPageConstant.CONTEXT_2_2_FOOTER.getId()));//Email адрес сюда..
            modelAndView.addObject("ItemHeader10", getItemById(menuItems, MainPageConstant.CONTEXT_3_FOOTER.getId()));//ФИСТ в соц сетях
            modelAndView.addObject("ItemHeader10_1", getItemById(menuItems, MainPageConstant.CONTEXT_3_1_FOOTER.getId()));//VK
            modelAndView.addObject("ItemHeader10_2", getItemById(menuItems, MainPageConstant.CONTEXT_3_2_FOOTER.getId()));//INST
            modelAndView.addObject("ItemHeader10_3", getItemById(menuItems, MainPageConstant.CONTEXT_3_3_FOOTER.getId()));//TWIT
            modelAndView.addObject("ItemHeader12", getItemById(menuItems, MainPageConstant.CONTEXT_4_FOOTER.getId()));//Подразделения ПГУТИ
            modelAndView.addObject("ItemHeader12_1", getItemById(menuItems, MainPageConstant.CONTEXT_4_1_FOOTER.getId()));//Приемная комиссия
            modelAndView.addObject("ItemHeader12_2", getItemById(menuItems, MainPageConstant.CONTEXT_4_2_FOOTER.getId()));//Телефонная книга ПГУТИ
            modelAndView.addObject("ItemHeader12_3", getItemById(menuItems, MainPageConstant.CONTEXT_4_3_FOOTER.getId()));//Библиотека ПГУТИ
            modelAndView.addObject("ItemHeader12_4", getItemById(menuItems, MainPageConstant.CONTEXT_4_4_FOOTER.getId()));//Центр занятости ПГУТИ
            modelAndView.addObject("ItemHeader12_5", getItemById(menuItems, MainPageConstant.CONTEXT_4_5_FOOTER.getId()));//Профком студентов ПГУТИ
            modelAndView.addObject("ItemHeader12_6", getItemById(menuItems, MainPageConstant.CONTEXT_4_6_FOOTER.getId()));//Электронно информационная среда
            modelAndView.addObject("ItemHeader11", getItemById(menuItems, MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page

            modelAndView.addObject("DialogItem1", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_1.getId()));//адрес корпуса №1
            modelAndView.addObject("DialogItem1_1", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_1_1.getId()));//г. Самара, Льва Толстого 23
            modelAndView.addObject("DialogItem2", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_2.getId()));//адрес корпуса №2
            modelAndView.addObject("DialogItem2_1", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_2_1.getId()));//г. Самара, Московское шоссе 77
            modelAndView.addObject("DialogItem3", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_3.getId()));//Сайт:
            modelAndView.addObject("DialogItem3_1", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_3_1.getId()));//fist.psuti.ru
            modelAndView.addObject("DialogItem4", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_4.getId()));//Телефон:
            modelAndView.addObject("DialogItem4_1", getItemById(menuItems, MainPageConstant.DIALOG_CONTEXT_4_1.getId()));//+7(846) 228-00-05



            // model.addAttribute("locations", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_1_FOOTER.getId()));
            modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
            //model.addAttribute("context2", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_3_FOOTER.getId()));
            modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
            modelAndView.addObject("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
            modelAndView.addObject("bestStudents", bestStudentService.filledBestStudent());
            modelAndView.addObject("logotipPSUTI", getItemById(pictures, MainPageConstant.LOGOTIP_PSUTI.getId()));
            modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
            modelAndView.addObject("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());

            modelAndView.addObject("ItemHeader1_3", getItemById(menuItems, MainPageConstant.HEADER_NEWS.getId()));//Новости
            modelAndView.addObject("ItemHeader1_2", getItemById(menuItems, MainPageConstant.HEADER_NEWS_PSUTI_FIST.getId()));//Новости про ПГУТИ и ФИСТ
            return modelAndView;
        } else if (MainPageObjectConstant.checkModelAndView.size() > 0) {
            for (NameTableBD change : MainPageObjectConstant.checkModelAndView) {
                changeModel(change);
            }
            MainPageObjectConstant.checkModelAndView.clear();
            return modelAndView;
        } else return modelAndView;
    }

    public void changeModel(NameTableBD nameTable) {
        if (nameTable.equals(NameTableBD.BEST_STUDENT)) updateBestStudentTable();
        else if (nameTable.equals(NameTableBD.EDUCATION_PROCESS)) updateEducationProcessTable();
        else if (nameTable.equals(NameTableBD.MENU_ITEM_HEADER_IN_MAIN_PAGE)) updateMenuItemHeaderInMainPageTable();
        else if (nameTable.equals(NameTableBD.NEWS_OF_FACULTY)) updateNewsOfFacultyTable();
        else if (nameTable.equals(NameTableBD.PICTURES)) updatePicturesTable();
        else if (nameTable.equals(NameTableBD.USERS)) updateUsersTable();
        else if (nameTable.equals(NameTableBD.USERS_ROLE)) updateUsersRoleTable();
    }

    private void updateBestStudentTable() {
        modelAndView.addObject("bestStudents", bestStudentService.filledBestStudent());
    }

    private void updateEducationProcessTable() {
        modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
    }

    private void updateMenuItemHeaderInMainPageTable() {
        List<MenuItemHeaderInMainPage> items = menuItemHeaderInMainPagesService.getAll();
        modelAndView.addObject("email", getItemById(items, MainPageConstant.EMAIL.getId())); //почта
        modelAndView.addObject("phone", getItemById(items, MainPageConstant.PHONE.getId())); //почта
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());
        modelAndView.addObject("menuItemMobile", getItemById(items, MainPageConstant.MOBILE_MENU.getId()));//Меню
        modelAndView.addObject("ItemHeader1", getItemById(items, MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Новости
        modelAndView.addObject("ItemHeader1_1", getItemById(items, MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Актуальное на сегодня
        modelAndView.addObject("ItemButton1", getItemById(items, MainPageConstant.BUTTON_NEWS.getId()));//Клавиша Больше новостей
        modelAndView.addObject("ItemHeader2", getItemById(items, MainPageConstant.HEADER_EDUCATIONAL_PROCESS.getId()));//учебный процесс
        modelAndView.addObject("ItemHeader3", getItemById(items, MainPageConstant.SOCIAL_NETWORKS.getId()));//Мы в социальных сетях
        modelAndView.addObject("ItemHeader4", getItemById(items, MainPageConstant.ANONS_AND_DECLORATIONS.getId()));//Анонсы и объяявления
        modelAndView.addObject("ItemHeader5", getItemById(items, MainPageConstant.FOTO_GALLERY.getId()));//Фотогалерея от инстаграмма
        modelAndView.addObject("ItemHeader6", getItemById(items, MainPageConstant.STUDENT_IT_CLUB.getId()));//Студенческий it club
        modelAndView.addObject("ItemHeader7", getItemById(items, MainPageConstant.BEST_STUDENT.getId()));//Лучшие студенты
        modelAndView.addObject("ItemHeader8", getItemById(items, MainPageConstant.CONTEXT_1_FOOTER.getId()));//Расположение
        modelAndView.addObject("ItemHeader9", getItemById(items, MainPageConstant.CONTEXT_2_FOOTER.getId()));//контекст 1
        modelAndView.addObject("ItemHeader10", getItemById(items, MainPageConstant.CONTEXT_3_FOOTER.getId()));//контекст 2
        modelAndView.addObject("ItemHeader11", getItemById(items, MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page
        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
        modelAndView.addObject("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
        modelAndView.addObject("ItemHeader1_3", getItemById(items, MainPageConstant.HEADER_NEWS.getId()));//Новости
        modelAndView.addObject("ItemHeader1_2", getItemById(items, MainPageConstant.HEADER_NEWS_PSUTI_FIST.getId()));//Новости про ПГУТИ и ФИСТ
    }

    private void updateNewsOfFacultyTable() {
        modelAndView.addObject("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());
    }

    private void updatePicturesTable() {
        List<Pictures> listPictures = picturesService.getAll();
        modelAndView.addObject("logotipFIST", getItemById(listPictures, MainPageConstant.LOGOTIP_FIST.getId()));
        modelAndView.addObject("slider", picturesService.findPicturesByKey(MainPageConstant.SLIDER_1.getKeyPicture()));//слайдеры на месте вывода список направлений подготовки
        modelAndView.addObject("ItemHeaderPictureSplit", getItemById(listPictures, MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getId()));
        modelAndView.addObject("logotipPSUTI", getItemById(listPictures, MainPageConstant.LOGOTIP_PSUTI.getId()));
        if (picturesCache != null) {
            picturesCache.clear();
            picturesCache = new HashMap<>();
            for (Pictures pictures : picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
    }

    private void updateUsersTable() {

    }

    private void updateUsersRoleTable() {

    }

    @RequestMapping("")
    public ModelAndView main(Model model) {
        return mainPage(model);
    }

    @RequestMapping("/main")
    public ModelAndView mainPage(Model model) {
        ModelAndView modelview = initModelAndView();
        modelview.addAllObjects(model.asMap());
        modelview.setViewName("index");
        //response.setHeader("Cache-Control", "max-age= 3600");
        return modelview;
    }

    //@Cacheable("mainPictures")
    public byte[] getPicture(long idPicture) {
        for (Map.Entry picture : initPicturesCache().entrySet()) {

            if (picture.getKey().equals(idPicture)) {
                return (byte[]) picture.getValue();
            }
        }
        return null;
    }

    //@Cacheable("mainPictures")
    @ResponseBody
    @RequestMapping(value = "/main/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture, HttpServletResponse response) {
        byte[] b = getPicture(idPicture);
        response.setHeader("cache-control", "max-age=86400, must-revalidate");
        return b;
    }

    @RequestMapping("/newsBlog")
    public ModelAndView newsBlog(Model model) {
        return newsBlogPage(1, model);
    }

    @RequestMapping("/newsBlog/page/{idPage}")
    public ModelAndView newsBlogPage(@PathVariable int idPage, Model model) {
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        List<NewsOfFaculty> topics = newsFacultyService.getAll();
        model.addAttribute("pageCount", (topics.size() / (NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount() + 1)) + 1);
        List<NewsOfFaculty> resultTopic = new ArrayList<>();
        for (int i = (idPage - 1) * NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount(), j = 0; i < topics.size() && j < NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount(); i++, j++) {
            resultTopic.add(topics.get(i));
        }
        model.addAttribute("resultTopic", resultTopic);
        ModelAndView modelview = initModelAndView();
        modelview.addAllObjects(model.asMap());
        modelview.setViewName("newsBlog");
        return modelview;
    }

    //TODO PREPOD//////////////////////////////////////////////////////////////////////////
    @RequestMapping("/about/faculty")
    public ModelAndView aboutOfFaculty() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("faculty");
        return modelAndView;
    }

    @RequestMapping("/deanTeam")
    public ModelAndView prepod() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("deanTeam");
        return modelAndView;
    }

    @RequestMapping("/deanTeam(shadow)")
    public ModelAndView prepod2() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("deanTeam(shadow)");
        return modelAndView;
    }

    @RequestMapping("/bogomolova")
    public ModelAndView bogomolova() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("bogomolova");
        return modelAndView;
    }

    @RequestMapping("/chernova")
    public ModelAndView chernova() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("chernova");
        return modelAndView;
    }

    @RequestMapping("/tychkova")
    public ModelAndView tychkova() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("tychkova");
        return modelAndView;
    }

    @RequestMapping("/belova")
    public ModelAndView belova() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("belova");
        return modelAndView;
    }

    @RequestMapping("/konyaeva")
    public ModelAndView konyaeva() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("konyaeva");
        return modelAndView;
    }

    @RequestMapping("/vorobeva")
    public ModelAndView vorobeva() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("vorobeva");
        return modelAndView;
    }
    //TODO STUDENT ////////////////////////////////////////////////////////

    @RequestMapping("/students/rightAndObligations")
    public ModelAndView studentRightAndObligations() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("rightsAndObligations");
        return modelAndView;
    }

    @RequestMapping("/students/groupCurators")
    public ModelAndView groupCurators() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("groupCurators");
        return modelAndView;
    }

    @RequestMapping("/students/costEducation")
    public ModelAndView costEducation() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }


    //TODO faculty /////////////////////////////////////////////////////////////

    @RequestMapping("/faculty/academicSoviet")
    public ModelAndView academicSoviet() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("academicSoviet");
        return modelAndView;
    }

    @RequestMapping("/faculty/cathedras")
    public ModelAndView cathedras() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("cathedras");
        return modelAndView;
    }

    @RequestMapping("/faculty/diplomasPhoto")
    public ModelAndView diplomasPhoto() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("diplomasPhoto");
        return modelAndView;
    }

    @RequestMapping("/faculty/graduates")
    public ModelAndView graduates() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("graduates");
        return modelAndView;
    }

    @RequestMapping("/faculty/commissionsFIST")
    public ModelAndView commissionsFIST() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("commissionsFIST");
        return modelAndView;
    }

    //TODO контакты/////////////////////////////////////////////////////////////
    @RequestMapping("/contacts")
    public ModelAndView contacts() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("contacts");
        return modelAndView;
    }


    //TODO абитуриентам.....................................................................
    @RequestMapping("/abitur/trainingDirections")
    public ModelAndView trainingDirections() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("trainingDirections");
        return modelAndView;
    }

    @RequestMapping("/trainingDirections2")
    public ModelAndView trainingDirections2() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("trainingDirections2");
        return modelAndView;
    }

    @RequestMapping("/abitur/costEducation")
    public ModelAndView abiturCostEducation() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }

    //TODO направления(основная страница).....................................................................
    @RequestMapping("/mainTrend")
    public ModelAndView mainTrend() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("mainTrend");
        return modelAndView;
    }

    private Object getItemById(List<?> list, long id) {
        if(list.get(0) instanceof MenuItemHeaderInMainPage) {
            for (MenuItemHeaderInMainPage item: (List<MenuItemHeaderInMainPage>) list) {
                if (item.getId() == id) return item;
            }
        } else if (list.get(0) instanceof Pictures) {
            for (Pictures pictures: (List<Pictures>) list) {
                if (pictures.getId() == id) return pictures;
            }
        }
        return new Object();
    }
}
