package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.*;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;

import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminNewsFacultyController {
    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/content/news", method = RequestMethod.GET)
    public ModelAndView news() {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        //model.addAttribute("newFaculty", new NewsOfFaculty());
        ModelAndView modelAndView = new ModelAndView("adminnews");
        modelAndView.addObject("news",  newsFacultyService.getLastCountByDateFilledPicture(NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount()));
        modelAndView.addObject("withDate", LocalDate.now());
        modelAndView.addObject("fromDate", LocalDate.now());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/news/add", method = RequestMethod.GET)
    public ModelAndView addNewFaculty() {
        ModelAndView modelAndView = new ModelAndView("adminAddNews");
        modelAndView.addObject("date", LocalDate.now());
        modelAndView.addObject("newFaculty", new NewsOfFaculty());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/admin/content/news/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture) {
        /*for (Map.Entry picture: initPicturesCashe().entrySet()) {
            if (picture.getKey().equals(idPicture)) return (byte[]) picture.getValue();
        }*/
        Pictures foto = picturesService.findPictureById(idPicture);
        if (foto!=null)
            return foto.getPictureFile();
        else return new byte[0];
    }

    @RequestMapping(value = "/admin/content/news/add/submit", method = RequestMethod.POST)
    public String addNewFacultySubmit(@ModelAttribute NewsOfFaculty newFaculty ) throws IOException {
        newFaculty.setDate(LocalDateTime.of(LocalDate.parse(newFaculty.getDateStringLocalDate()), LocalTime.now()));
        if (!newFaculty.getPictureFile().isEmpty()) {
            newFaculty.setIdPicture(savePicture(newFaculty));
        }
        newsFacultyService.insert(newFaculty);
        for (User user : userService.getUsersByRoleAndEnable(Role.SUBSCRIBER)) {
            modelAndViewConfiguration.sendMessageSubscriber(newFaculty.getHeading(), newFaculty.getText(), user,
                    "Посмотреть на сайте", UrlForSearch.getUrlSite() + UrlForSearch.URL_NEWS_BLOG.getApi(), "",
                    "Вы получаете это письмо, потому что вы подписаны на новости факультета.");
        }
        return "redirect:../";
    }

    /*@RequestMapping("/test")
    public String test() throws IOException {
        File f ;
        for (Pictures pictures: picturesService.getAll()) {
            if (pictures.getKeyPicture().equals(KeyPicture.DIPLOMAS)) {
                f = new File("src\\main\\resources\\static\\" + pictures.getUrlPicture());
                if (f.exists()) {
                    pictures.setPictureFile(Files.readAllBytes(f.toPath()));
                    picturesService.update(pictures);
                }
            }
        }
        return "newsBlog";
    }*/

    @RequestMapping(value = "/admin/content/news/range", method = RequestMethod.GET)
    public ModelAndView newsByRange(String withDate,
                                    String fromDate) throws SQLException {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        ModelAndView modelAndView = new ModelAndView("adminnews");
        modelAndView.addObject("news", newsFacultyService.getLastNewsByRangeDate(LocalDate.parse(withDate), LocalDate.parse(fromDate)));
        modelAndView.addObject("withDate", LocalDate.parse(withDate));
        modelAndView.addObject("fromDate", LocalDate.parse(fromDate));
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/news/delete/{id}",method = RequestMethod.GET)
    public String deleteNewFaculty(@PathVariable("id") long newsId, Model model) {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        picturesService.delete(newsFacultyService.findById(newsId).getIdPicture());
        newsFacultyService.delete(newsId);
        model.addAttribute("news", newsFacultyService.getAll());
        return "redirect:../../news";
    }

    @RequestMapping(value = "/admin/content/news/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateFaculty(@PathVariable("id") Long id/*, Model model*/) {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        ModelAndView modelAndView = new ModelAndView("adminUpdateNews");
        NewsOfFaculty topic = newsFacultyService.findById(id);
        topic.setText(topic.getText().replace("<br>","\r\n"));
        topic.setHeading(topic.getHeading().replace("<br>","\r\n"));
        modelAndView.addObject("newFaculty", topic);
        modelAndView.addObject("checkDeletePicture", false);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/content/news/update/submit", method = RequestMethod.POST)
    public String updateNewFaculty(@ModelAttribute NewsOfFaculty newFaculty) throws IOException {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        newFaculty.setDate(LocalDateTime.of(LocalDate.parse(newFaculty.getDateStringLocalDate()), LocalTime.now()));
        if (!newFaculty.getPictureFile().isEmpty()) {
            picturesService.delete(newFaculty.getIdPicture());
            newFaculty.setIdPicture(savePicture(newFaculty));
        }
        /*if (Boolean.valueOf(checkDeletePicture)) {
            picturesService.delete(newFaculty.getIdPicture());
            newFaculty.setIdPicture(-1);
        }*/
        newsFacultyService.update(newFaculty);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../../news";
    }

    @RequestMapping(value = "/admin/table/newsOfFaculty/update", method = RequestMethod.GET)
    public ModelAndView updateNewsFaculty(Model model) {
        //ModelAndView modelAndView = new ModelAndView("adminTableUpdateNewsOfFaculty");
        //modelAndView.addObject("item", new NewsOfFaculty());
        //modelAndView.addObject("newsOfFaculty", newsFacultyService.getAll());
        return updateNewsFacultyPage(1, model);
    }

    @RequestMapping(value = "/admin/table/newsOfFaculty/update/page/{idPage}", method = RequestMethod.GET)
    public ModelAndView updateNewsFacultyPage(@PathVariable int idPage, Model model) {
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        List<NewsOfFaculty> news = newsFacultyService.getAll();
        model.addAttribute("pageCount", (int)(Math.ceil((double) news.size() / NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount())));
        List<NewsOfFaculty> resultNewsFaculty = new ArrayList<>();
        for (int i = (idPage - 1) * NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount(), j = 0; i < news.size() && j < NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount(); i++, j++) {
            resultNewsFaculty.add(news.get(i));
        }
        model.addAttribute("newsOfFaculty", resultNewsFaculty);
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateNewsOfFaculty");
        modelAndView.addObject("item",  new NewsOfFaculty());
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/table/newsOfFaculty/update/submit", method = RequestMethod.POST)
    public String adminNewsFacultyUpdateSubmit(@ModelAttribute("item") NewsOfFaculty item) {
        item.setDate(LocalDateTime.of(LocalDate.parse(item.getDateStringLocalDate()), LocalTime.now()));
        newsFacultyService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/newsOfFaculty/delete/id={id}", method = RequestMethod.GET)
    public String adminNewsFacultyDelete(@PathVariable("id") long id) {
        //NewsOfFaculty newsOfFaculty = newsFacultyService.findById(id);
        newsFacultyService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    private long savePicture(NewsOfFaculty newFaculty) throws IOException {
        /*if (!newFaculty.getPictureFile().isEmpty())
            writeFile(newFaculty.getPictureFile().getBytes(), newFaculty.getPictureFile().getOriginalFilename());*/
        Pictures pictures = new Pictures();
        pictures.setUrlPicture(PathConstant.SAVE_PICTURE_NEWS_FACULTY.getPath() + newFaculty.getPictureFile().getOriginalFilename());
        pictures.setIdPage(2);
        pictures.setKeyPicture(KeyPicture.TOPIC_FACULTY);
        pictures.setPictureFile(newFaculty.getPictureFile().getBytes());
        pictures.setNamePicture(newFaculty.getPictureFile().getOriginalFilename());
        return picturesService.insert(pictures);
    }

    /*private void writeFile(byte[] buffer, String filename) throws IOException {
        //new File(PathConstant.SAVE_PICTURE_NEWS_FACULTY.getPath() + filename).mkdir();
            FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE_NEWS_FACULTY.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }*/
}
