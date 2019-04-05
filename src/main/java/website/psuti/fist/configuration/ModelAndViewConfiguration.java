package website.psuti.fist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.*;
import website.psuti.fist.model.File;
import website.psuti.fist.scheduler.SendMessageScheduler;
import website.psuti.fist.service.*;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ModelAndViewConfiguration {


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

    public HashMap<Long, byte[]> initPicturesCache() {
        if (picturesCache == null) {
            picturesCache = new HashMap<>();
            for (Pictures pictures : picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
        return picturesCache;
    }

    @PostConstruct
    public ModelAndView initModelAndView() {
        initPicturesCache();
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
            employees = new ArrayList<>();
            departments = new ArrayList<>();
            files = new ArrayList<>();
            newsOfFaculties = new ArrayList<>();
            modelAndView = new ModelAndView("", "", "");

            updateCandidateAssignment();
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

            return modelAndView;
        } else if (MainPageObjectConstant.getCheckModelAndView().size() > 0) {
            for (NameTableBD change : MainPageObjectConstant.getCheckModelAndView()) {
                changeModel(change);
            }
            MainPageObjectConstant.clearCheckModel();
            return modelAndView;
        } else return modelAndView;
    }

    public void changeModel(NameTableBD nameTable) {
        if (nameTable.equals(NameTableBD.BEST_STUDENT)) updateBestStudentTable();
        else if (nameTable.equals(NameTableBD.CANDIDATE_ASSIGNMENT)) updateCandidateAssignment();
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
        Reader s = null;
        try {
            s = new InputStreamReader(new FileInputStream(PathConstant.HTML_FILE_FOR_USER_SUBSCRIBE.getPath()) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        char c[] = new char[(int)(new java.io.File(PathConstant.HTML_FILE_FOR_USER_SUBSCRIBE.getPath())).length()];
        try {
            s.read(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlBody = String.copyValueOf(c)
                .replace("#footer", footer)
                .replace("#nameClient", nameClient)
                .replace("#textClient", text)
                .replace("#buttonCheck", buttonName)
                .replace("#buttonHref", buttonHref)
                .replace("#siteFIST", UrlForSearch.getUrlSite())
                .replace("#mainSiteName", "Главная")
                .replace("#newsBlogSite", UrlForSearch.getUrlSite() + UrlForSearch.URL_NEWS_BLOG.getApi())
                .replace("#newsFaculty", "Новости")
                .replace("#aboutFacultyName", "")//о факультете
                .replace("#aboutFaculty", UrlForSearch.getUrlSite() + UrlForSearch.URL_FACULTY.getApi())
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
        } catch (MessagingException e) {
            System.out.println("Я открыл scheduler");
            HashMap<String, String> message = new HashMap<>();
            message.put(header, htmlBody);
            SendMessageEmailConstant.addSendMessage(user, message);
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(applicationContext.getBean(SendMessageScheduler.class), "scheduler");
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
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
