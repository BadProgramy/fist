package website.psuti.fist.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.*;
import website.psuti.fist.scheduler.SendMessageScheduler;
import website.psuti.fist.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.sql.SQLException;
import java.util.*;

@Controller
public class MainController {

    //private final static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private UserService userService;


    @RequestMapping("")
    public ModelAndView main(Model model) {
        return mainPage(model);
    }

    @RequestMapping("/main")
    public ModelAndView mainPage(Model model) {
        ModelAndView modelview = modelAndViewConfiguration.initModelAndView();
        modelview.addAllObjects(model.asMap());
        modelview.setViewName("index");
        //response.setHeader("Cache-Control", "max-age= 3600");
        return modelview;
    }

    //@Cacheable("mainPictures")
    public byte[] getPicture(long idPicture) {
        for (Map.Entry picture : modelAndViewConfiguration.initPicturesCache().entrySet()) {

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
        response.setHeader("cache-control", "max-age=86400000, must-revalidate");
        return b;
    }

    @ResponseBody
    @RequestMapping(value = "/employees/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhotoEmployee(@PathVariable long idPicture, HttpServletResponse response) {
        byte[] b = getPicture(idPicture);
        response.setHeader("cache-control", "max-age=86400000, must-revalidate");
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
        List<NewsOfFaculty> topics = modelAndViewConfiguration.getNewsOfFaculties();
        model.addAttribute("pageCount", (topics.size() / (NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount() + 1)) + 1);
        List<NewsOfFaculty> resultTopic = new ArrayList<>();
        for (int i = (idPage - 1) * NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount(), j = 0; i < topics.size() && j < NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount(); i++, j++) {
            resultTopic.add(topics.get(i));
        }
        model.addAttribute("resultTopic", resultTopic);
        ModelAndView modelview = modelAndViewConfiguration.initModelAndView();
        modelview.addAllObjects(model.asMap());
        modelview.setViewName("newsBlog");
        return modelview;
    }

    //TODO PREPOD//////////////////////////////////////////////////////////////////////////
    @RequestMapping("/about/faculty")
    public ModelAndView aboutOfFaculty() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("faculty");
        return modelAndView;
    }

    @RequestMapping("/groupLists")
    public ModelAndView groupLists() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("groupLists");
        return modelAndView;
    }


    //TODO STUDENT ////////////////////////////////////////////////////////

    @RequestMapping("/students/rightAndObligations")
    public ModelAndView studentRightAndObligations() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("rightsAndObligations");
        return modelAndView;
    }

    @RequestMapping("/students/groupCurators")
    public ModelAndView groupCurators() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("groupCurators");
        return modelAndView;
    }

    @RequestMapping("/students/costEducation")
    public ModelAndView costEducation() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }
/////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("/resultOfControl")
    public ModelAndView resultOfControl() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("resultOfControl");
        return modelAndView;
    }
    //TODO faculty /////////////////////////////////////////////////////////////

    @RequestMapping("/faculty/academicSoviet")
    public ModelAndView academicSoviet() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("academicSoviet");
        return modelAndView;
    }

    @RequestMapping("/faculty/diplomasPhoto")
    public ModelAndView diplomasPhoto() {
        return diplomasPhotoPage(1);
        /*ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("diplomasPhoto");
        return modelAndView;*/
    }

    @RequestMapping("/faculty/diplomasPhoto/page/{idPage}")
    public ModelAndView diplomasPhotoPage(@PathVariable int idPage) {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("diplomasPhoto");

        if (idPage <= 0) idPage = 1;
        modelAndView.addObject("firstPage", idPage);
        List<Pictures> diplomas = modelAndViewConfiguration.getPicturesByKeyPicture(KeyPicture.DIPLOMAS);
        modelAndView.addObject("pageCount", (diplomas.size() / (DiplomConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount() + 1)) + 1);
        List<Pictures> resultDiplomas = new ArrayList<>();
        for (int i = (idPage - 1) * DiplomConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(), j = 0; i < diplomas.size() && j < DiplomConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(); i++, j++) {
            resultDiplomas.add(diplomas.get(i));
        }
        modelAndView.addObject("resultDiplomas", resultDiplomas);
       /* ModelAndView modelview = modelAndViewConfiguration.initModelAndView();
        modelview.addAllObjects(modelAndView.asMap());
        modelview.setViewName("newsBlog");*/

        return modelAndView;
    }

    @RequestMapping("/faculty/graduates")
    public ModelAndView graduates() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("graduates");
        return modelAndView;
    }

    @RequestMapping("/faculty/commissionsFIST")
    public ModelAndView commissionsFIST() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("commissionsFIST");
        return modelAndView;
    }

    //TODO контакты/////////////////////////////////////////////////////////////
    @RequestMapping("/contacts")
    public ModelAndView contacts() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("contacts");
        return modelAndView;
    }


    //TODO абитуриентам.....................................................................
    @RequestMapping("/abitur/trainingDirections")
    public ModelAndView trainingDirections() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("trainingDirections");
        return modelAndView;
    }

    @RequestMapping("/trainingDirections2")
    public ModelAndView trainingDirections2() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("trainingDirections2");
        return modelAndView;
    }

    @RequestMapping("/abitur/costEducation")
    public ModelAndView abiturCostEducation() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }

    //TODO направления(основная страница).....................................................................
    @RequestMapping("/mainTrend")
    public ModelAndView mainTrend() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("mainTrend");
        return modelAndView;
    }
//TODO учебный процесс /////////////////////////////////////////////////////////////
@RequestMapping("/gradStudents")
public ModelAndView gradStudents() {
    ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
    modelAndView.setViewName("gradStudents");
    return modelAndView;
}

    @RequestMapping("/interimControl")
    public ModelAndView interimControl() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("interimControl");
        return modelAndView;
    }

    //TODO а тут для подписки
    @RequestMapping("/user/add/subscriber")
    public ModelAndView addSubscriber(@RequestParam("username") String username) {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("enabledAccount");
        User user = userService.findUserByName(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEnabled(false);
            user.setAccountNonExpired(false);
            user.setAccountNonLocked(false);
            user.setCredentialsNonExpired(false);
            List<Role> roles = new ArrayList<>();
            roles.add(Role.SUBSCRIBER);
            user.setRole(roles);
        } else if (!user.getRole().contains(Role.SUBSCRIBER)) {
            user.addRole(Role.SUBSCRIBER);
            user.setEnabled(true);
        }
        try {
            userService.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modelAndViewConfiguration.sendMessageSubscriber("Здравствуйте, уважаемый подписчик!", "Для того, чтобы подписаться на новости факультета, нажмите на кнопку!", user,
                "Подписаться", UrlForSearch.getUrlSite() + "/user/enable/email=" + username, "",
                "Вы получаете это письмо, потому что вы хотели подписаться на новости факультета.");
        return modelAndView;
    }

    @RequestMapping("/user/unsubscribe/email={email}")
    public String activationUser(@PathVariable("email") String email) {
        User user = userService.findUserByName(email);
        userService.delete(user);
        return "enabledAccount";
    }
    //TODO кандидаты на отчисление.....................................................................
    @RequestMapping("/candidatesForExpulsion")
    public ModelAndView candidatesForExpulsion() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("candidatesForExpulsion");
        return modelAndView;
    }
}
