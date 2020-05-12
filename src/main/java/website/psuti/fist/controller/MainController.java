package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.*;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@Controller
public class  MainController {

    //private final static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView main(Model model) {
        return mainPage(model);
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView mainPage(Model model) {
        ModelAndView modelview = modelAndViewConfiguration.initModelAndView();
        modelview.addAllObjects(model.asMap());
        modelview.setViewName("index");
        //response.setHeader("Cache-Control", "max-age= 3600");
        return modelview;
    }

    //@Cacheable("mainPictures")
    public byte[] getPicture(long idPicture) {
        try {
            byte[] pictureFile = picturesService.findPictureById(idPicture).getPictureFile();
            return pictureFile;
        } catch (Exception ex) {
            return new byte[0];
        }
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

    @RequestMapping(value = "/newsBlog", method = RequestMethod.GET)
    public ModelAndView newsBlog(Model model) {
        return newsBlogPage(1, model);
    }

    @RequestMapping(value = "/newsBlog/page/{idPage}", method = RequestMethod.GET)
    public ModelAndView newsBlogPage(@PathVariable int idPage, Model model) {
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        List<NewsOfFaculty> topics = modelAndViewConfiguration.getNewsOfFaculties();
        //model.addAttribute("pageCount", (topics.size() / (NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount() + 1)) + 1);
        model.addAttribute("pageCount", (int)(Math.ceil((double) topics.size() / NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_NEWSBLOG_OUTPUT.getCount())));
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

    @RequestMapping(value = "/newsBlog/topic/id={id}", method = RequestMethod.GET)
    public String newsBlogTopic(Model model, @PathVariable long id) {
        model.addAllAttributes(modelAndViewConfiguration.initModelAndView().getModelMap());
        model.addAttribute("topic", modelAndViewConfiguration.getItemById(modelAndViewConfiguration.getNewsOfFaculties(), id));
        return "topic";
    }

    //TODO PREPOD//////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/about/faculty", method = RequestMethod.GET)
    public ModelAndView aboutOfFaculty() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("faculty");
        return modelAndView;
    }

    @RequestMapping(value = "/groupLists", method = RequestMethod.GET)
    public ModelAndView groupLists() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("groupLists");
        return modelAndView;
    }


    //TODO STUDENT ////////////////////////////////////////////////////////

    @RequestMapping(value = "/students/rightAndObligations", method = RequestMethod.GET)
    public ModelAndView studentRightAndObligations() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("rightsAndObligations");
        return modelAndView;
    }

    @RequestMapping(value = "/students/costEducation", method = RequestMethod.GET)
    public ModelAndView costEducation() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }
/////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/resultOfControl", method = RequestMethod.GET)
    public ModelAndView resultOfControl() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("resultOfControl");
        return modelAndView;
    }
    //TODO faculty /////////////////////////////////////////////////////////////

    @RequestMapping(value = "/faculty/academicSoviet", method = RequestMethod.GET)
    public ModelAndView academicSoviet() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("academicSoviet");
        return modelAndView;
    }

    @RequestMapping(value = "/faculty/diplomasPhoto", method = RequestMethod.GET)
    public ModelAndView diplomasPhoto() {
        return diplomasPhotoPage(1);
        /*ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("diplomasPhoto");
        return modelAndView;*/
    }

    @RequestMapping(value = "/faculty/diplomasPhoto/page/{idPage}", method = RequestMethod.GET)
    public ModelAndView diplomasPhotoPage(@PathVariable int idPage) {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("diplomasPhoto");

        if (idPage <= 0) idPage = 1;
        modelAndView.addObject("firstPage", idPage);
        List<Pictures> diplomas = modelAndViewConfiguration.getPicturesByKeyPicture(KeyPicture.DIPLOMAS);
        modelAndView.addObject("pageCount", (diplomas.size() / (PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount() + 1)) + 1);
        List<Pictures> resultDiplomas = new ArrayList<>();
        for (int i = (idPage - 1) * PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(), j = 0; i < diplomas.size() && j < PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(); i++, j++) {
            resultDiplomas.add(diplomas.get(i));
        }
        modelAndView.addObject("resultDiplomas", resultDiplomas);
       /* ModelAndView modelview = modelAndViewConfiguration.initModelAndView();
        modelview.addAllObjects(modelAndView.asMap());
        modelview.setViewName("newsBlog");*/

        return modelAndView;
    }

    @RequestMapping(value = "/faculty/graduates", method = RequestMethod.GET)
    public ModelAndView graduates() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("graduates");
        return modelAndView;
    }

    @RequestMapping(value = "/faculty/commissionsFIST", method = RequestMethod.GET)
    public ModelAndView commissionsFIST() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("commissionsFIST");
        return modelAndView;
    }

    //TODO контакты/////////////////////////////////////////////////////////////
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ModelAndView contacts() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("contacts");
        return modelAndView;
    }


    //TODO абитуриентам.....................................................................


    @RequestMapping(value = "/abitur/costEducation", method = RequestMethod.GET)
    public ModelAndView abiturCostEducation() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("costEducation");
        return modelAndView;
    }

    //TODO направления(основная страница).....................................................................
    @RequestMapping(value = "/mainTrend", method = RequestMethod.GET)
    public ModelAndView mainTrend() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("mainTrend");
        return modelAndView;
    }

    @RequestMapping(value = "/BI", method = RequestMethod.GET)
    public ModelAndView BI() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("BI");
        return modelAndView;
    }
    @RequestMapping(value = "/IVT", method = RequestMethod.GET)
    public ModelAndView IVT() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("IVT");
        return modelAndView;
    }
    @RequestMapping(value = "/innovatika", method = RequestMethod.GET)
    public ModelAndView innovatika() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("innovatika");
        return modelAndView;
    }
    @RequestMapping(value = "/RSO", method = RequestMethod.GET)
    public ModelAndView RSO() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("RSO");
        return modelAndView;
    }
    @RequestMapping(value = "/uits", method = RequestMethod.GET)
    public ModelAndView uits() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("uits");
        return modelAndView;
    }
    @RequestMapping(value = "/PI", method = RequestMethod.GET)
    public ModelAndView PI() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("PI");
        return modelAndView;
    }
    @RequestMapping(value = "/prikladInfo", method = RequestMethod.GET)
    public ModelAndView prikladInfo() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("prikladInfo");
        return modelAndView;
    }
    @RequestMapping(value = "/IST", method = RequestMethod.GET)
    public ModelAndView IST() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("IST");
        return modelAndView;
    }
    @RequestMapping(value = "/matob", method = RequestMethod.GET)
    public ModelAndView matob() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("matob");
        return modelAndView;
    }
//TODO учебный процесс /////////////////////////////////////////////////////////////
@RequestMapping(value = "/gradStudents", method = RequestMethod.GET)
public ModelAndView gradStudents() {
    ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
    modelAndView.setViewName("gradStudents");
    return modelAndView;
}

    @RequestMapping(value = "/interimControl", method = RequestMethod.GET)
    public ModelAndView interimControl() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("interimControl");
        return modelAndView;
    }

    //TODO а тут для подписки
    @RequestMapping(value = "/user/add/subscriber", method = RequestMethod.GET)
    public ModelAndView addSubscriber(@RequestParam("username") String username) throws Exception {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("enabledAccount");
        modelAndView.addObject("headerAnswer", "СПАСИБО ЧТО ПОДПИСАЛИСЬ");
        modelAndView.addObject("textAnswer", "Теперь вы будете в числе первых узнавать о самых актуальных событиях в нашем факультете");
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

    @RequestMapping(value = "/user/unsubscribe/email={email}", method = RequestMethod.GET)
    public String activationUser(@PathVariable("email") String email,Model model) {
        User user = userService.findUserByName(email);
        if (user != null) {
            userService.delete(user);
        }
        model.addAttribute("headerAnswer", "Отписка");
        model.addAttribute("textAnswer", "Вы отписались от портала ФИСТ ПГУТИ, возвращайтесь скорее :)");
        model.addAllAttributes(modelAndViewConfiguration.initModelAndView().getModelMap());
        return "enabledAccount";
    }
}
