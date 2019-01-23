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
