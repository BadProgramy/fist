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
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {


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


    private ModelAndView initModelAndView() {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
            modelAndView = new ModelAndView("","","");
            modelAndView.addObject("email", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.EMAIL.getId())); //почта
            modelAndView.addObject("phone", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.PHONE.getId())); //почта
            modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());

            modelAndView.addObject("logotipFIST", picturesService.findPictureByName(MainPageConstant.LOGOTIP_FIST.getName()));
            modelAndView.addObject("slider", picturesService.findPicturesByKey(MainPageConstant.SLIDER_1.getKeyPicture()));//слайдеры на месте вывода список направлений подготовки

            modelAndView.addObject("ItemHeaderPictureSplit", picturesService.findPictureByName(MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getName()));
            modelAndView.addObject("menuItemMobile", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MOBILE_MENU.getId()));//Меню

            modelAndView.addObject("ItemHeader1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS.getId()));//Новости
            modelAndView.addObject("ItemHeader1_1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Актуальное на сегодня
            modelAndView.addObject("ItemButton1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BUTTON_NEWS.getId()));//Клавиша Больше новостей
            modelAndView.addObject("ItemHeader2", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_EDUCATIONAL_PROCESS.getId()));//учебный процесс
            modelAndView.addObject("ItemHeader3", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.SOCIAL_NETWORKS.getId()));//Мы в социальных сетях
            modelAndView.addObject("ItemHeader4", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.ANONS_AND_DECLORATIONS.getId()));//Анонсы и объяявления
            modelAndView.addObject("ItemHeader5", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOTO_GALLERY.getId()));//Фотогалерея от инстаграмма
            modelAndView.addObject("ItemHeader6", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.STUDENT_IT_CLUB.getId()));//Студенческий it club
            modelAndView.addObject("ItemHeader7", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BEST_STUDENT.getId()));//Лучшие студенты
            modelAndView.addObject("ItemHeader8", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MAP_LOCATION.getId()));//Расположение
            modelAndView.addObject("ItemHeader9", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT1.getId()));//контекст 1
            modelAndView.addObject("ItemHeader10", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT2.getId()));//контекст 2
            modelAndView.addObject("ItemHeader11", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page

            // model.addAttribute("locations", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.MAP_LOCATION.getId()));
            modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
            //model.addAttribute("context2", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT2.getId()));
            modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
            modelAndView.addObject("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
            modelAndView.addObject("bestStudents", bestStudentService.filledBestStudent());
            modelAndView.addObject("logotipPSUTI", picturesService.findPictureByName(MainPageConstant.LOGOTIP_PSUTI.getName()));
            modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
            modelAndView.addObject("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());

            modelAndView.addObject("ItemHeader1_3", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS.getId()));//Новости
            modelAndView.addObject("ItemHeader1_2", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS_PSUTI_FIST.getId()));//Новости про ПГУТИ и ФИСТ
            return modelAndView;
        } else if (MainPageObjectConstant.checkModelAndView.size() > 0){
            for (Map.Entry<Boolean, NameTableBD> change : MainPageObjectConstant.checkModelAndView.entrySet()) {
                changeModel(change.getValue());
            }
            MainPageObjectConstant.checkModelAndView.clear();
            return modelAndView;
        }

        else return modelAndView;
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
        modelAndView.addObject("email", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.EMAIL.getId())); //почта
        modelAndView.addObject("phone", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.PHONE.getId())); //почта
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());
        modelAndView.addObject("menuItemMobile", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MOBILE_MENU.getId()));//Меню
        modelAndView.addObject("ItemHeader1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS.getId()));//Новости
        modelAndView.addObject("ItemHeader1_1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_ACTUAL_NEWS.getId()));//Актуальное на сегодня
        modelAndView.addObject("ItemButton1", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BUTTON_NEWS.getId()));//Клавиша Больше новостей
        modelAndView.addObject("ItemHeader2", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_EDUCATIONAL_PROCESS.getId()));//учебный процесс
        modelAndView.addObject("ItemHeader3", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.SOCIAL_NETWORKS.getId()));//Мы в социальных сетях
        modelAndView.addObject("ItemHeader4", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.ANONS_AND_DECLORATIONS.getId()));//Анонсы и объяявления
        modelAndView.addObject("ItemHeader5", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOTO_GALLERY.getId()));//Фотогалерея от инстаграмма
        modelAndView.addObject("ItemHeader6", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.STUDENT_IT_CLUB.getId()));//Студенческий it club
        modelAndView.addObject("ItemHeader7", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.BEST_STUDENT.getId()));//Лучшие студенты
        modelAndView.addObject("ItemHeader8", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.MAP_LOCATION.getId()));//Расположение
        modelAndView.addObject("ItemHeader9", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT1.getId()));//контекст 1
        modelAndView.addObject("ItemHeader10", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.CONTEXT2.getId()));//контекст 2
        modelAndView.addObject("ItemHeader11", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page
        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
        modelAndView.addObject("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT1.getId()));
        modelAndView.addObject("ItemHeader1_3", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS.getId()));//Новости
        modelAndView.addObject("ItemHeader1_2", menuItemHeaderInMainPagesService.findItemById(MainPageConstant.HEADER_NEWS_PSUTI_FIST.getId()));//Новости про ПГУТИ и ФИСТ
    }

    private void updateNewsOfFacultyTable() {
        modelAndView.addObject("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());
    }

    private void updatePicturesTable() {
        modelAndView.addObject("logotipFIST", picturesService.findPictureByName(MainPageConstant.LOGOTIP_FIST.getName()));
        modelAndView.addObject("slider", picturesService.findPicturesByKey(MainPageConstant.SLIDER_1.getKeyPicture()));//слайдеры на месте вывода список направлений подготовки
        modelAndView.addObject("ItemHeaderPictureSplit", picturesService.findPictureByName(MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getName()));
        modelAndView.addObject("logotipPSUTI", picturesService.findPictureByName(MainPageConstant.LOGOTIP_PSUTI.getName()));
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
        modelview.addAllObjects(model.asMap()) ;
        modelview.setViewName("index");
        return modelview;
    }

    @ResponseBody
    @RequestMapping(value = "/main/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture) {
        return picturesService.findPictureById(idPicture).getPictureFile();
    }

    @RequestMapping("/newsBlog")
    public ModelAndView newsBlog(Model model){
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
        model.addAttribute("newsOfFaculty", resultTopic);
        ModelAndView modelview = initModelAndView();
        modelview.addAllObjects(model.asMap()) ;
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

    @RequestMapping("/bogomolova")
    public ModelAndView bogomolova(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("bogomolova");
        return modelAndView;
    }

    @RequestMapping("/chernova")
    public ModelAndView chernova(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("chernova");
        return modelAndView;
    }

    @RequestMapping("/tychkova")
    public ModelAndView tychkova(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("tychkova");
        return modelAndView;
    }

    @RequestMapping("/belova")
    public ModelAndView belova(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("belova");
        return modelAndView;
    }

    @RequestMapping("/konyaeva")
    public ModelAndView konyaeva(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("konyaeva");
        return modelAndView;
    }

    @RequestMapping("/vorobeva")
    public ModelAndView vorobeva(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("vorobeva");
        return modelAndView;
    }
    //TODO STUDENT ////////////////////////////////////////////////////////

    @RequestMapping("/students/rightAndObligations")
    public ModelAndView studentRightAndObligations(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("rightsAndObligations");
        return modelAndView;
    }

    @RequestMapping("/students/groupCurators")
    public ModelAndView groupCurators(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("groupCurators");
        return modelAndView;
    }

    @RequestMapping("/students/costEducation")
    public ModelAndView costEducation(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }


    //TODO faculty /////////////////////////////////////////////////////////////

    @RequestMapping("/faculty/academicSoviet")
    public ModelAndView academicSoviet(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("academicSoviet");
        return modelAndView;
    }

    @RequestMapping("/faculty/cathedras")
    public ModelAndView cathedras(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("cathedras");
        return modelAndView;
    }

    @RequestMapping("/faculty/diplomasPhoto")
    public ModelAndView diplomasPhoto(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("diplomasPhoto");
        return modelAndView;
    }

    @RequestMapping("/faculty/graduates")
    public ModelAndView graduates(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("graduates");
        return modelAndView;
    }

    @RequestMapping("/faculty/commissionsFIST")
    public ModelAndView commissionsFIST(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("commissionsFIST");
        return modelAndView;
    }

    //TODO контакты/////////////////////////////////////////////////////////////
    @RequestMapping("/contacts")
    public ModelAndView contacts(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("contacts");
        return modelAndView;
    }


    //TODO абитуриентам.....................................................................
    @RequestMapping("/abitur/trainingDirections")
    public ModelAndView trainingDirections(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("trainingDirections");
        return modelAndView;
    }

    @RequestMapping("/abitur/costEducation")
    public ModelAndView abiturCostEducation(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }
}
