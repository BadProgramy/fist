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
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.PicturesService;
import website.psuti.fist.service.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;

@Controller
public class AdminNewsFacultyController {
    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/news")
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

    @RequestMapping("/admin/news/add")
    public ModelAndView addNewFaculty() {
        ModelAndView modelAndView = new ModelAndView("adminAddNews");
        modelAndView.addObject("date", LocalDate.now());
        modelAndView.addObject("newFaculty", new NewsOfFaculty());
        return modelAndView;
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

    @RequestMapping(value = "/admin/news/delete/{id}",method = RequestMethod.GET)
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

    @RequestMapping(value = "/admin/news/update/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/admin/news/update/submit", method = RequestMethod.POST)
    public String updateNewFaculty(@ModelAttribute NewsOfFaculty newFaculty) throws IOException {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        newFaculty.setDate(LocalDate.parse(newFaculty.getDateStringLocalDate()));
        if (!newFaculty.getPictureFile().isEmpty()) {
            picturesService.delete(newFaculty.getIdPicture());
            newFaculty.setIdPicture(savePicture(newFaculty));
        }
        /*if (Boolean.valueOf(checkDeletePicture)) {
            picturesService.delete(newFaculty.getIdPicture());
            newFaculty.setIdPicture(-1);
        }*/
        newsFacultyService.update(newFaculty);
        return "redirect:../../news";
    }

    private long savePicture(NewsOfFaculty newFaculty) throws IOException {
        writeFile(newFaculty.getPictureFile().getBytes(), newFaculty.getPictureFile().getOriginalFilename());
        Pictures pictures = new Pictures();
        pictures.setUrlPicture(PathConstant.SAVE_PICTURE.getPath() + newFaculty.getPictureFile().getOriginalFilename());
        pictures.setIdPage(2);
        pictures.setKeyPicture(-1);
        pictures.setPictureFile(newFaculty.getPictureFile().getBytes());
        pictures.setNamePicture(newFaculty.getPictureFile().getOriginalFilename());
        return picturesService.insert(pictures);
    }

    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }
}
