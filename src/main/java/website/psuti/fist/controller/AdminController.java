package website.psuti.fist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.NewsFacultyConstant;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @RequestMapping("/admin/login")
    public String login() {
        return "authentication";
    }

    @RequestMapping("/admin/setting/cash")
    public String updateModelView() {
        modelAndViewConfiguration.updateCash();
        return "admin";
    }




    private byte[] getPicture(long idPicture) {
        //todo по кешу ищет картинку
    /*    for (Map.Entry picture : modelAndViewConfiguration.initPicturesCache().entrySet()) {

            if (picture.getKey().equals(idPicture)) {
                return (byte[]) picture.getValue();
            }
        }
        return null;*/
        return picturesService.findPictureById(idPicture).getPictureFile();
    }

    //@Cacheable("mainPictures")
    @ResponseBody
    @RequestMapping(value = "/admin/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture, HttpServletResponse response) {
        byte[] b = getPicture(idPicture);
        //response.setHeader("cache-control", "max-age=86400000, must-revalidate");
        return b;
    }
}
