package website.psuti.fist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.constant.NewsFacultyConstant;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.model.User;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private UserService userService;

    private User user;

    private HashMap<Long, byte[]> picturesCache;


    private HashMap<Long, byte[]> initPicturesCache() {
        if (picturesCache == null) {
            picturesCache = new HashMap<>();
            for (Pictures pictures: picturesService.getAll()) {
                picturesCache.put(pictures.getId(), pictures.getPictureFile());
            }
        }
        return picturesCache;
    }

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @RequestMapping("/admin/login")
    public String login() {
        return "authentication";
    }

    @RequestMapping("/admin/news")
    public ModelAndView news() {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        //model.addAttribute("newFaculty", new NewsOfFaculty());
        ModelAndView modelAndView = new ModelAndView("adminnews");
        modelAndView.addObject("news",  newsFacultyService.getLastCountByDateFilledPicture(NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount()));
        modelAndView.addObject("withDate", LocalDate.now());
        modelAndView.addObject("fromDate", LocalDate.now());
        return modelAndView;
    }

    /*@ModelAttribute
    LocalDate initLocalDate() {
        return LocalDate.now();
    }*/


    @RequestMapping("/admin/news/add")
    public ModelAndView addNewFaculty() {
        ModelAndView modelAndView = new ModelAndView("adminAddNews");
        modelAndView.addObject("date", LocalDate.now());
        modelAndView.addObject("newFaculty", new NewsOfFaculty());
        return modelAndView;
    }

    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream("src\\main\\resources\\static\\images\\downloadNewsPicture\\" + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }

    @ResponseBody
    @RequestMapping(value = "/admin/news/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture) {
        /*for (Map.Entry picture: initPicturesCashe().entrySet()) {
            if (picture.getKey().equals(idPicture)) return (byte[]) picture.getValue();
        }*/
        Pictures foto = picturesService.findPictureById(idPicture);
        if (foto!=null)
            return foto.getPictureFile();
        else return new byte[0];
    }

    @RequestMapping("/admin/news/add/submit")
    public String addNewFacultySubmit(@ModelAttribute NewsOfFaculty newFaculty ) throws IOException {
        newFaculty.setDate(LocalDate.parse(newFaculty.getDateStringLocalDate()));
        if (newFaculty.getPictureFile() != null) {
            newFaculty.setIdPicture(savePicture(newFaculty));
        }
        newsFacultyService.insert(newFaculty);
        return "redirect:../";
    }

    @RequestMapping("/test")
    public String test() throws IOException {
        File f ;
        for (Pictures pictures: picturesService.getAll()) {
                f = new File("src\\main\\resources\\static\\" + pictures.getUrlPicture());
                pictures.setPictureFile(Files.readAllBytes(f.toPath()));
                picturesService.update(pictures);
        }
        return "newsBlog";
    }

    @RequestMapping("/admin/news/range")
    public ModelAndView newsByRange(String withDate,
                                           String fromDate) throws SQLException {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        ModelAndView modelAndView = new ModelAndView("adminnews");
        modelAndView.addObject("news", newsFacultyService.getLastNewsByRangeDate(LocalDate.parse(withDate), LocalDate.parse(fromDate)));
        modelAndView.addObject("withDate", LocalDate.parse(withDate));
        modelAndView.addObject("fromDate", LocalDate.parse(fromDate));
        return modelAndView;
    }

    private LocalDate convertLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    @RequestMapping(value = "/admin/news/delete/{id}",method = RequestMethod.GET)
    public String deleteNewFaculty(@PathVariable("id") long newsId, Model model) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        newsFacultyService.delete(newsId);
        model.addAttribute("news", newsFacultyService.getAll());
        return "redirect:../../news";
    }

    @RequestMapping(value = "/admin/news/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateFaculty(@PathVariable("id") Long id/*, Model model*/) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        ModelAndView modelAndView = new ModelAndView("adminUpdateNews");
        NewsOfFaculty topic = newsFacultyService.findById(id);
        topic.setText(topic.getText().replace("<br>","\r\n"));
        topic.setHeading(topic.getHeading().replace("<br>","\r\n"));
        modelAndView.addObject("newFaculty", topic);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/news/update/submit", method = RequestMethod.POST)
    public String updateNewFaculty(@ModelAttribute NewsOfFaculty newFaculty) throws IOException {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        newFaculty.setDate(LocalDate.parse(newFaculty.getDateStringLocalDate()));
        if (!newFaculty.getPictureFile().isEmpty()) {
            newFaculty.setIdPicture(savePicture(newFaculty));
        }
        newsFacultyService.update(newFaculty);
        return "redirect:../../news";
    }

    private long savePicture(NewsOfFaculty newFaculty) throws IOException {
        writeFile(newFaculty.getPictureFile().getBytes(), newFaculty.getPictureFile().getOriginalFilename());
        Pictures pictures = new Pictures();
        pictures.setUrlPicture("images\\downloadNewsPicture\\" + newFaculty.getPictureFile().getOriginalFilename());
        pictures.setIdPage(2);
        pictures.setKeyPicture(-1);
        pictures.setPictureFile(newFaculty.getPictureFile().getBytes());
        pictures.setNamePicture(newFaculty.getPictureFile().getOriginalFilename());
        return picturesService.insert(pictures);
    }


    /*@RequestMapping("/admin/login/submit")
    public String testCreateUser() throws SQLException {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.FULLADMIN);
        User user = new User();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setFirstname("Айрат");
        user.setSecondname("Мухутдинов");
        user.setUsername("airat23059");
        user.setPassword("19970808");
        user.setPagevk("https://vk.com/id109488730");
        user.setRole(roles);
        userService.save(user);
        return authPage();
    }*/
}
