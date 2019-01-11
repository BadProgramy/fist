package website.psuti.fist.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
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
import website.psuti.fist.service.MainPageService;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Controller
public class AdminController {
    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private UserService userService;

    private User user;

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
        modelAndView.addObject("news",  newsFacultyService.getLastTenByDate(NewsFacultyConstant.COUNT_NEWS_FACULTY_FOR_OUTPUT_PAGE.getCount()));
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

    /*private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }*/

    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream("src\\main\\resources\\static\\images\\downloadNewsPicture\\" + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }

    @RequestMapping("/admin/news/add/submit")
    public String addNewFacultySubmit(@ModelAttribute NewsOfFaculty newFaculty ) throws IOException {
        newFaculty.setDate(LocalDate.parse(newFaculty.getDateStringLocalDate()));
        writeFile(newFaculty.getPictureFile().getBytes(), newFaculty.getPictureFile().getOriginalFilename());
        Pictures pictures = new Pictures();
        pictures.setUrlPicture("images\\downloadNewsPicture\\"+ newFaculty.getPictureFile().getOriginalFilename());
        pictures.setDate(LocalDate.now());
        pictures.setIdPage(2);
        pictures.setKeyPicture(-1);
        pictures.setNamePicture(newFaculty.getPictureFile().getOriginalFilename());
        picturesService.insert(pictures);
        newsFacultyService.insert(newFaculty);
        return "redirect:../";
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

    @RequestMapping(value = "/admin/news/delete/{id}",method = RequestMethod.DELETE)
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
        modelAndView.addObject("newFaculty", newsFacultyService.findById(id));
        //model.addAttribute("newFaculty", newFaculty);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/news/update/submit", method = RequestMethod.POST)
    public String updateNewFaculty(@ModelAttribute NewsOfFaculty newFaculty) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        //this.newFaculty.update(newFaculty);
        newsFacultyService.update(newFaculty);
        //this.newFaculty = null;
        return "redirect:../../news";
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
