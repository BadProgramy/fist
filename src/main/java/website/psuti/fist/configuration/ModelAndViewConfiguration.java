package website.psuti.fist.configuration;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;
import website.psuti.fist.FistApplication;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.*;
import website.psuti.fist.model.File;
import website.psuti.fist.scheduler.SendMessageScheduler;
import website.psuti.fist.service.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Component
public class ModelAndViewConfiguration {
    private boolean checkTableBDDATA = false;

    public final Logger logger = LoggerFactory.getLogger(ModelAndViewConfiguration.class);

    @Autowired
    private MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @Autowired
    private CandidateAssignmentService candidateAssignmentService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private EducationProcessService educationProcessService;

    @Autowired
    private BestStudentService bestStudentService;

    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FileService fileService;

    @Autowired CuratorService curatorService;

    @Autowired
    private HTMLStructurePageService htmlStructurePageService;

    @Autowired
    private Sender sender;

    @Autowired
    private ApplicationContext applicationContext;



    //--------------------------------------------------КЭШ------------------------------------------
    private ModelAndView modelAndView;

    private HashMap<Long, byte[]> picturesCache;

    private List<Employee> employees;
    private List<Department> departments;
    private List<File> files;
    private List<NewsOfFaculty> newsOfFaculties;
    //-------------------------------------------------Оперативная память-----------------------------

    @Autowired
    private DataSource dataSource;


    public HashMap<Long, byte[]> initPicturesCache() {
        if (picturesCache == null) {
            picturesCache = new HashMap<>();
            for (Pictures pictures : picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
        return picturesCache;
    }

    public void filledDataBase() throws IOException, SQLException {
        Connection connection = dataSource.getConnection();
        for (NameTableBD tableBD: NameTableBD.values()) {
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                /*BufferedReader in = new BufferedReader();*/
                Scanner in = new Scanner(classLoader.getResource("SQLDump/SQLInsert/fist_" + tableBD.getName() + ".sql").openStream());
                String str;
                String result = "";
                while (in.hasNextLine()) {
                    result += in.nextLine() + "\r\n";
                    if (result.toCharArray()[result.length() - 4] == ')' && result.toCharArray()[result.length() - 3] == ';') {
                        connection.createStatement().executeUpdate(result);
                        result = "";
                    }
                }
                logger.info("Выполнил запросы к " + tableBD.getName());
                in.close();
            } catch (java.sql.SQLSyntaxErrorException ex) {

            } catch (java.sql.SQLIntegrityConstraintViolationException ex) {

            }
        }
        /*fore
        dataSource.getConnection().createStatement().executeUpdate(new String(Files.readAllBytes(Paths.get("website.psuti.fist.SQLDump/SQLInsert/fist_best_student.sql"))));
*/    }

    @PostConstruct
    public ModelAndView initModelAndView(){
        try {
            if (!checkTableBDDATA){
                checkTableBDDATA = true;
                filledDataBase();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initPicturesCache();
        if (modelAndView == null) {
            updateCashBD();

            return modelAndView;
        } else if (MainPageObjectConstant.getCheckModelAndView().size() > 0) {
            for (NameTableBD change : MainPageObjectConstant.getCheckModelAndView()) {
                changeModel(change);
            }
            MainPageObjectConstant.clearCheckModel();
            return modelAndView;
        } else return modelAndView;
    }

    public void updateCash() {
        if (picturesCache != null) picturesCache.clear();
        picturesCache = new HashMap<>();
        for (Pictures pictures : picturesService.getAll()) {
            picturesCache.put(pictures.getId(), pictures.getPictureFile());
        }
        updateCashBD();
    }

    private void updateCashBD() {
        modelAndView = new ModelAndView();
        employees = new ArrayList<>();
        departments = new ArrayList<>();
        files = new ArrayList<>();
        newsOfFaculties = new ArrayList<>();
        modelAndView = new ModelAndView("", "", "");

        updateHTMLStructurePages();
        updateCandidateAssignment();
        updateCurator();
        updateBestStudentTable();
        updateEducationProcessTable();
        updateMenuItemHeaderInMainPageTable();
        updateNewsOfFacultyTable();
        updatePicturesTable();
        updateUsersRoleTable();
        updateUsersTable();
        updateEmployee();
        updateDepartment();
        updateFile();
    }

    public void changeModel(NameTableBD nameTable) {
        if (nameTable.equals(NameTableBD.BEST_STUDENT)) updateBestStudentTable();
        else if (nameTable.equals(NameTableBD.HTMLStructurePage)) updateHTMLStructurePages();
        else if (nameTable.equals(NameTableBD.CANDIDATE_ASSIGNMENT)) updateCandidateAssignment();
        else if (nameTable.equals(NameTableBD.CURATOR)) updateCurator();
        else if (nameTable.equals(NameTableBD.EDUCATION_PROCESS)) updateEducationProcessTable();
        else if (nameTable.equals(NameTableBD.MENU_ITEM_HEADER_IN_MAIN_PAGE)) updateMenuItemHeaderInMainPageTable();
        else if (nameTable.equals(NameTableBD.NEWS_OF_FACULTY)) updateNewsOfFacultyTable();
        else if (nameTable.equals(NameTableBD.PICTURES)) updatePicturesTable();
        else if (nameTable.equals(NameTableBD.USERS)) updateUsersTable();
        else if (nameTable.equals(NameTableBD.USERS_ROLE)) updateUsersRoleTable();
        else if (nameTable.equals(NameTableBD.EMPLOYEE)) updateEmployee();
        else if (nameTable.equals(NameTableBD.DEPARTMENT)) updateDepartment();
        else if (nameTable.equals(NameTableBD.FILE)) updateFile();
    }

    private void updateHTMLStructurePages() {
        modelAndView.addObject("commissionsFISTHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.COMMISSIONS_FIST.getId()));
        modelAndView.addObject("contactsHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.CONTACTS.getId()));
        modelAndView.addObject("costEducationHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.COST_EDUCATION.getId()));
        modelAndView.addObject("facultyHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.FACULTY.getId()));
        modelAndView.addObject("gradStudentsHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GRAD_STUDENTS.getId()));
        modelAndView.addObject("graduatesHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GRADUATES.getId()));
        modelAndView.addObject("groupListsHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GROUP_LISTS.getId()));
        modelAndView.addObject("innovatikaHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.INNOVATIKA.getId()));
        modelAndView.addObject("interimControlHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.INTERIM_CONTROL.getId()));
        modelAndView.addObject("istHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.IST.getId()));
        modelAndView.addObject("ivtHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.IVT.getId()));
        modelAndView.addObject("mainTrendHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.MAIN_TREND.getId()));
        modelAndView.addObject("matobHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.MATOB.getId()));
        modelAndView.addObject("piHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.PI.getId()));
        modelAndView.addObject("prikladInfoHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.PRIKLAD_INFO.getId()));
        modelAndView.addObject("resultOfControlHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RESULT_OF_CONTROL.getId()));
        modelAndView.addObject("rightsAndObligationHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RIGHT_AND_OBLIGATION.getId()));
        modelAndView.addObject("rsoHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RSO.getId()));
        modelAndView.addObject("biHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.BI.getId()));
        modelAndView.addObject("uitsHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.UITS.getId()));
        modelAndView.addObject("cathedrasHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.CATHEDRAS_HEAD.getId()));
        modelAndView.addObject("academicSovietHTMLStructure", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.ACADEMIC_SOVIET.getId()));
        modelAndView.addObject("biHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.BI_HEAD.getId()));
        modelAndView.addObject("candidateForExpulsionHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.CANDIDATES_FOR_EXPULSION_HEAD.getId()));
        modelAndView.addObject("characteristicEmployeeHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.CHARACTERISTIC_EMPLOYEE_HEAD.getId()));
        modelAndView.addObject("commissionsFISTHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.COMISSIONS_FIST_HEAD.getId()));
        modelAndView.addObject("contactsHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.CONTACTS_HEAD.getId()));
        modelAndView.addObject("costEducationHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.COST_EDUCATION_HEAD.getId()));
        modelAndView.addObject("deanTeamHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.DEAN_TEAM_HEAD.getId()));
        modelAndView.addObject("diplomasPhotoHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.DIPLOMAS_PHOTO_HEAD.getId()));
        modelAndView.addObject("enabledAccountHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.ENABLED_ACCOUNT_HEAD.getId()));
        modelAndView.addObject("facultyHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.FACULTY_HEAD.getId()));
        modelAndView.addObject("gradStudentsHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GRAD_STUDENTS_HEAD.getId()));
        modelAndView.addObject("graduatesHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GRADUATES_HEAD.getId()));
        modelAndView.addObject("groupCuratorsHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GROUP_CURATORS_HEAD.getId()));
        modelAndView.addObject("groupListsHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.GROUP_LISTS_HEAD.getId()));
        modelAndView.addObject("indexHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.INDEX_HEAD.getId()));
        modelAndView.addObject("innovatikaHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.INNOVATIKA_HEAD.getId()));
        modelAndView.addObject("interimControlHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.INTERIM_CONTROL_HEAD.getId()));
        modelAndView.addObject("istHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.IST_HEAD.getId()));
        modelAndView.addObject("ivtHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.IVT_HEAD.getId()));
        modelAndView.addObject("mainTrendHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.MAIN_TREND_HEAD.getId()));
        modelAndView.addObject("matobHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.MATOB_HEAD.getId()));
        modelAndView.addObject("newsBlogHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.NEWS_BLOG_HEAD.getId()));
        modelAndView.addObject("piHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.PI_HEAD.getId()));
        modelAndView.addObject("prikladInfoHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.PRIKLAD_INFO_HEAD.getId()));
        modelAndView.addObject("resultOfControlHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RESULT_OF_CONTROL_HEAD.getId()));
        modelAndView.addObject("rightAndObligationHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RIGHT_AND_OBLIGATIONS_HEAD.getId()));
        modelAndView.addObject("rsoHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.RSO_HEAD.getId()));
        modelAndView.addObject("searchHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.SEARCH_HEAD.getId()));
        modelAndView.addObject("topicHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.TOPIC_HEAD.getId()));
        modelAndView.addObject("uitsHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.UITS_HEAD.getId()));
        modelAndView.addObject("academicSovietHead", htmlStructurePageService.findHTMLStructurePageById(HtmlStructurePageConstant.ACADEMIC_SOVIET_HEAD.getId()));
    }

    private void updateCurator() {
        modelAndView.addObject("curators", curatorService.getAll());
    }

    private void updateCandidateAssignment() {
        modelAndView.addObject("candidateAssignments", candidateAssignmentService.getAll());
    }

    private void updateFile() {
        files = fileService.getAll();
    }

    private void updateEmployee() {
        employees = employeeService.getAll();
        modelAndView.addObject("deanTeams", employeeService.findEmployeesByNameDepartment(NameDepartmentConstant.STRUCTURE_DEAN_TEAM.getName()));
    }

    private void updateDepartment() { departments = departmentService.getAll(); }

    private void updateBestStudentTable() {
        modelAndView.addObject("bestStudents", bestStudentService.filledBestStudent());
    }

    private void updateEducationProcessTable() {
        modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
    }

    private void updateMenuItemHeaderInMainPageTable() {
        List<MenuItemHeaderInMainPage> items = menuItemHeaderInMainPagesService.getAll();
        modelAndView.addObject("email", getItemById(items, MainPageConstant.EMAIL.getId())); //почта
        modelAndView.addObject("phone", getItemById(items, MainPageConstant.PHONE.getId())); //телефон
        modelAndView.addObject("location", getItemById(items, MainPageConstant.LOCATION.getId())); //адрес
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAllHeadersMainPage());
        modelAndView.addObject("menuItemMobile", getItemById(items, MainPageConstant.MOBILE_MENU.getId()));//Меню
        modelAndView.addObject("ItemHeader1", getItemById(items, MainPageConstant.HEADER_NEWS.getId()));//Новости
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
        modelAndView.addObject("ItemHeader9_1", getItemById(items, MainPageConstant.CONTEXT_2_1_FOOTER.getId()));//Введите свой email, чтобы ....
        modelAndView.addObject("ItemHeader9_2", getItemById(items, MainPageConstant.CONTEXT_2_2_FOOTER.getId()));//Email адрес сюда..
        modelAndView.addObject("username", "");
        modelAndView.addObject("ItemHeader10", getItemById(items, MainPageConstant.CONTEXT_3_FOOTER.getId()));//ФИСТ в соц сетях
        modelAndView.addObject("ItemHeader10_1", getItemById(items, MainPageConstant.CONTEXT_3_1_FOOTER.getId()));//VK
        modelAndView.addObject("ItemHeader10_2", getItemById(items, MainPageConstant.CONTEXT_3_2_FOOTER.getId()));//INST
        modelAndView.addObject("ItemHeader10_3", getItemById(items, MainPageConstant.CONTEXT_3_3_FOOTER.getId()));//TWIT
        modelAndView.addObject("ItemHeader12", getItemById(items, MainPageConstant.CONTEXT_4_FOOTER.getId()));//Подразделения ПГУТИ
        modelAndView.addObject("ItemHeader12_1", getItemById(items, MainPageConstant.CONTEXT_4_1_FOOTER.getId()));//Приемная комиссия
        modelAndView.addObject("ItemHeader12_2", getItemById(items, MainPageConstant.CONTEXT_4_2_FOOTER.getId()));//Телефонная книга ПГУТИ
        modelAndView.addObject("ItemHeader12_3", getItemById(items, MainPageConstant.CONTEXT_4_3_FOOTER.getId()));//Библиотека ПГУТИ
        modelAndView.addObject("ItemHeader12_4", getItemById(items, MainPageConstant.CONTEXT_4_4_FOOTER.getId()));//Центр занятости ПГУТИ
        modelAndView.addObject("ItemHeader12_5", getItemById(items, MainPageConstant.CONTEXT_4_5_FOOTER.getId()));//Профком студентов ПГУТИ
        modelAndView.addObject("ItemHeader12_6", getItemById(items, MainPageConstant.CONTEXT_4_6_FOOTER.getId()));//Электронно информационная среда
        modelAndView.addObject("ItemHeader11", getItemById(items, MainPageConstant.FOOTER_MAIN_PAGE.getId()));//footer main page

        modelAndView.addObject("DialogItem1", getItemById(items, MainPageConstant.DIALOG_CONTEXT_1.getId()));//адрес корпуса №1
        modelAndView.addObject("DialogItem1_1", getItemById(items, MainPageConstant.DIALOG_CONTEXT_1_1.getId()));//г. Самара, Льва Толстого 23
        modelAndView.addObject("DialogItem2", getItemById(items, MainPageConstant.DIALOG_CONTEXT_2.getId()));//адрес корпуса №2
        modelAndView.addObject("DialogItem2_1", getItemById(items, MainPageConstant.DIALOG_CONTEXT_2_1.getId()));//г. Самара, Московское шоссе 77
        modelAndView.addObject("DialogItem3", getItemById(items, MainPageConstant.DIALOG_CONTEXT_3.getId()));//Сайт:
        modelAndView.addObject("DialogItem3_1", getItemById(items, MainPageConstant.DIALOG_CONTEXT_3_1.getId()));//fist.psuti.ru
        modelAndView.addObject("DialogItem4", getItemById(items, MainPageConstant.DIALOG_CONTEXT_4.getId()));//Телефон:
        modelAndView.addObject("DialogItem4_1", getItemById(items, MainPageConstant.DIALOG_CONTEXT_4_1.getId()));//+7(846) 228-00-05

        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
        modelAndView.addObject("characterUniversity", menuItemHeaderInMainPagesService.getCharacterUniversity());
        modelAndView.addObject("subtitles", menuItemHeaderInMainPagesService.getMinorHeadersByMainHeader(MainPageConstant.CONTEXT_2_FOOTER.getId()));
        modelAndView.addObject("ItemHeader1_3", getItemById(items, MainPageConstant.HEADER_NEWS.getId()));//Новости
        modelAndView.addObject("ItemHeader1_2", getItemById(items, MainPageConstant.HEADER_NEWS_PSUTI_FIST.getId()));//Новости про ПГУТИ и ФИСТ
        modelAndView.addObject("deanTeamName", getItemById(items, MainPageConstant.DEAN_TEAM_NAME.getId()));//Состав деканата
        modelAndView.addObject("departmentName", getItemById(items, MainPageConstant.DEPARTMENT.getId()));//кафедры
        modelAndView.addObject("candidateAssignment", getItemById(items, MainPageConstant.CANDIDATE_ASSIGNMENT.getId()));//КАНДИДАТЫ НА ОТЧИСЛЕНИЕ

        modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
    }

    private void updateNewsOfFacultyTable() {
        newsOfFaculties = newsFacultyService.getAll();
        modelAndView.addObject("newsOfFaculty", newsFacultyService.getLastTwoNewsFaculty());
    }

    private void updatePicturesTable() {
        List<Pictures> listPictures = picturesService.getAll();
        modelAndView.addObject("logotipFIST", getItemById(listPictures, MainPageConstant.LOGOTIP_FIST.getId()));
        modelAndView.addObject("slider", picturesService.findPictureById(MainPageConstant.SLIDER_1.getId()));
        modelAndView.addObject("ItemHeaderPictureSplit", getItemById(listPictures, MainPageConstant.ITEM_HEADER_PICTURE_SPLIT.getId()));
        modelAndView.addObject("logotipPSUTI", getItemById(listPictures, MainPageConstant.LOGOTIP_PSUTI.getId()));
        modelAndView.addObject("educationProcess", educationProcessService.educationProcess());
        if (picturesCache != null)
        {
            picturesCache = new HashMap<>();
            picturesCache.clear();
            for (Pictures pictures : picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
    }

    private void updateUsersTable() {

    }

    private void updateUsersRoleTable() {

    }

    public void sendMessageSubscriber(String header, String text, User user,
                                      String buttonName, String buttonHref, String nameClient, String footer) {
        String resultRead = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {

            Scanner in = new Scanner(classLoader.getResource(PathConstant.HTML_FILE_FOR_USER_SUBSCRIBE.getPath()).openStream());
            while (in.hasNextLine())
                resultRead += in.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlBody = resultRead
                .replace("#footer", footer)
                .replace("#nameClient", nameClient)
                .replace("#textClient", text)
                .replace("#buttonCheck", buttonName)
                .replace("#buttonHref", buttonHref)
                .replace("#siteFIST", UrlForSearch.getUrlSite())
                .replace("#mainSiteName", "Главная")
                .replace("#newsBlogSite", UrlForSearch.getUrlSite() + UrlForSearch.URL_NEWS_BLOG.getApi())
                .replace("#newsFaculty", "Новости")
                //.replace("#aboutFacultyName", "")//о факультете
                //.replace("#aboutFaculty", UrlForSearch.getUrlSite() + UrlForSearch.URL_FACULTY.getApi())
                .replace("#deanTeamName", "Состав деканата")
                .replace("#deanTeam", UrlForSearch.getUrlSite() + UrlForSearch.URL_DEAN_TEAM.getApi())
                .replace("#unsubscribeName", "Отписаться")
                .replace("#unsubscribe", UrlForSearch.getUrlSite() + "/user/unsubscribe/email=" + user.getUsername())
                .replace("#contactName", "Контакты")
                .replace("#contact", UrlForSearch.getUrlSite() + UrlForSearch.URL_CONTACT.getApi())

                .replace("#headerTop", "Факультет информационных систем и технологий")
                .replace("#logotipFIST", UrlForSearch.getUrlSite() + "/main/picture/"+ MainPageConstant.LOGOTIP_FIST.getId())
                .replace("#logotipPSUTI", UrlForSearch.getUrlSite() + "/main/picture/"+ MainPageConstant.LOGOTIP_PSUTI.getId())
                .replace("#logotipTwitter", UrlForSearch.getUrlSite() + "/main/picture/"+ 73)
                .replace("#logotipInstagram", UrlForSearch.getUrlSite() + "/main/picture/"+ 72)
                .replace("#logotipVK", UrlForSearch.getUrlSite() + "/main/picture/"+ 71)
                .replace("#headerEmailHtml", header);

        try {
            sender.send(header, htmlBody, user.getUsername());
        } catch (Exception e) {
            System.out.println("Я открыл scheduler");
            HashMap<String, String> message = new HashMap<>();
            message.put(header, htmlBody);
            SendMessageEmailConstant.addSendMessage(user, message);
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(applicationContext.getBean(SendMessageScheduler.class), "scheduler");
            e.printStackTrace();
        }
    }

    public Object getItemById(List<?> list, long id) {
        if(list.get(0) instanceof MenuItemHeaderInMainPage) {
            for (MenuItemHeaderInMainPage item: (List<MenuItemHeaderInMainPage>) list) {
                if (item.getId() == id) return item;
            }
        } else if (list.get(0) instanceof Pictures) {
            for (Pictures pictures: (List<Pictures>) list) {
                if (pictures.getId() == id) return pictures;
            }
        } else if (list.get(0) instanceof Employee) {
            for (Employee employee: (List<Employee>) list) {
                if (employee.getId() == id) return employee;
            }
        } else if (list.get(0) instanceof Department) {
            for (Department department: (List<Department>) list) {
                if (department.getId() == id) return department;
            }
        } else if (list.get(0) instanceof File) {
            for (File file: (List<File>) list) {
                if (file.getId() == id) return file;
            }
        } else if (list.get(0) instanceof NewsOfFaculty) {
            for (NewsOfFaculty topic: (List<NewsOfFaculty>) list) {
                if (topic.getId() == id) return topic;
            }
        }
        return new Object();
    }

    public File getFileByName(String nameFile) {
        for (File file: files) {
            if (file.getName().equals(nameFile)) {
                return file;
            }
        }
        return null;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<File> getFiles() {
        return files;
    }

    public HashMap<Long, byte[]> getPicturesCache() {
        return picturesCache;
    }

    public List<NewsOfFaculty> getNewsOfFaculties() {
        return newsOfFaculties;
    }

    public List<Pictures> getPicturesByKeyPicture(KeyPicture keyPicture) {
        return picturesService.findPicturesByKey(keyPicture);
    }
}
