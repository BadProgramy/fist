package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.KeyPicture;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.PicturesService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class AdminPictureController {
    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;



    @RequestMapping("/admin/picture/add/submit")
    public String adminAddPicture(Model model, @ModelAttribute("picture") Pictures picture) throws IOException {
        picturesService.insert(updatePicture(picture));
        modelAndViewConfiguration.initModelAndView();
        switch (picture.getKeyPicture()) {
            case BEST_STUDENT: return adminPictureBestStudentUpdate(model);
            case TOPIC_FACULTY: return adminPictureTopicFacultyUpdate(model);
            case DEAN_TEAM: adminPictureDeanTeamUpdate(model);
            case DEPARTMENT: adminPictureDepartmentUpdate(model);
            case OTHER: return adminPictureOtherUpdate(model);
            //case DEAN_TEAM: return
            default: return "404";
        }
    }

    @RequestMapping("/admin/picture/department/update")
    public String adminPictureDepartmentUpdate( Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.DEPARTMENT));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/bestStudent/update")
    public String adminPictureBestStudentUpdate( Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.BEST_STUDENT));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/deanTeam/update")
    public String adminPictureDeanTeamUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.DEAN_TEAM));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/topicFaculty/update")
    public String adminPictureTopicFacultyUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.TOPIC_FACULTY));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/other/update")
    public String adminPictureOtherUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.OTHER));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/update/submit")
    public String adminPictureUpdateSubmit(@ModelAttribute("item") Pictures item) throws IOException {
        ModelAndView modelAndView = new ModelAndView("adminUpdatePicture");
        modelAndView.addObject("pictures", picturesService.getAll() );
        modelAndView.addObject("picture", new Pictures());
        modelAndView.addObject("keyPictures", KeyPicture.values());
        modelAndView.addObject("item", new Pictures());
        picturesService.update(updatePicture(item));
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    private Pictures updatePicture(Pictures item) throws IOException {
        item.setUrlPicture(PathConstant.SAVE_PICTURE.getPath() +item.getNamePicture());
        item.setDate(LocalDate.parse(item.getDateStringLocalDate()));
        item.setIdPage(-1);
        //item.setStyles();
        if (!item.getPictureFileMultipart().isEmpty()) {
            item.setPictureFile(item.getPictureFileMultipart().getBytes());
            item.setNamePicture(item.getPictureFileMultipart().getOriginalFilename());
            writeFile(item.getPictureFile(), item.getNamePicture());
        } else {
            //item.setPictureFile(picturesService.findPictureById(item.getId()).getPictureFile());
            for (Map.Entry entry : modelAndViewConfiguration.getPicturesCache().entrySet()) {
                if (entry.getKey().equals(item.getId()))
                    item.setPictureFile((byte[]) entry.getValue());
            }

        }
        return item;
    }

    private byte[] getPicture(long idPicture) {
        for (Map.Entry picture : modelAndViewConfiguration.initPicturesCache().entrySet()) {

            if (picture.getKey().equals(idPicture)) {
                return (byte[]) picture.getValue();
            }
        }
        return null;
    }

    //@Cacheable("mainPictures")
    @ResponseBody
    @RequestMapping(value = "/admin/picture/{idPicture}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable long idPicture, HttpServletResponse response) {
        byte[] b = getPicture(idPicture);
        //response.setHeader("cache-control", "max-age=86400000, must-revalidate");
        return b;
    }

    private void writeFile(byte[] buffer, String filename) throws IOException {
        //new File(PathConstant.SAVE_PICTURE.getPath() + filename).mkdir();
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }
}
