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



    @RequestMapping("/admin/table/picture/add/submit")
    public String adminAddPicture(Model model, @ModelAttribute("picture") Pictures picture) throws IOException {
        picturesService.insert(updatePicture(picture));
        modelAndViewConfiguration.initModelAndView();
        return returnPage(picture, model);
    }

    @RequestMapping("/admin/table/picture/department/update")
    public String adminPictureDepartmentUpdate( Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.DEPARTMENT));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

    @RequestMapping("/admin/table/picture/bestStudent/update")
    public String adminPictureBestStudentUpdate( Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.BEST_STUDENT));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

    @RequestMapping("/admin/table/picture/deanTeam/update")
    public String adminPictureDeanTeamUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.DEAN_TEAM));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

    @RequestMapping("/admin/table/picture/topicFaculty/update")
    public String adminPictureTopicFacultyUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.TOPIC_FACULTY));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

    @RequestMapping("/admin/table/picture/other/update")
    public String adminPictureOtherUpdate(Model model) {
        model.addAttribute("pictures", picturesService.findPicturesByKey(KeyPicture.OTHER));
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

    @RequestMapping("/admin/table/picture/update/submit")
    public String adminPictureUpdateSubmit(@ModelAttribute("item") Pictures item, Model model) throws IOException {
        picturesService.update(updatePicture(item));
        modelAndViewConfiguration.initModelAndView();
        return returnPage(item, model);
    }

    @RequestMapping("/admin/table/picture/delete/id={id}")
    public String adminPictureDelete(Model model, @PathVariable("id") Long id) {
        Pictures picture = picturesService.findPictureById(id);
        picturesService.delete(id);
        return returnPage(picture, model);
    }

    private String returnPage(Pictures item, Model model) {
        switch (item.getKeyPicture()) {
            case BEST_STUDENT: return "redirect:../bestStudent/update";//adminPictureBestStudentUpdate(model);
            case TOPIC_FACULTY: return "redirect:../topicFaculty/update";//adminPictureTopicFacultyUpdate(model);
            case DEAN_TEAM: return "redirect:../deanTeam/update";//adminPictureDeanTeamUpdate(model);
            case DEPARTMENT: return "redirect:../department/update";//adminPictureDepartmentUpdate(model);
            case OTHER: return "redirect:../other/update";//adminPictureOtherUpdate(model);
            //case DEAN_TEAM: return
            default: return "404";
        }
    }

    private Pictures updatePicture(Pictures item) throws IOException {
        //item.setStyles();
        if (!item.getPictureFileMultipart().isEmpty()) {
            item.setPictureFile(item.getPictureFileMultipart().getBytes());
            item.setNamePicture(item.getPictureFileMultipart().getOriginalFilename());
            //writeFile(item.getPictureFile(), item.getNamePicture());
        } else {
            //item.setPictureFile(picturesService.findPictureById(item.getId()).getPictureFile());
            for (Map.Entry entry : modelAndViewConfiguration.getPicturesCache().entrySet()) {
                if (entry.getKey().equals(item.getId()))
                    item.setPictureFile((byte[]) entry.getValue());
            }
        }
        item.setUrlPicture(PathConstant.SAVE_PICTURE.getPath() +item.getNamePicture());
        item.setDate(LocalDate.parse(item.getDateStringLocalDate()));
        item.setIdPage(-1);
        return item;
    }

/*    private void writeFile(byte[] buffer, String filename) throws IOException {
        //new File(PathConstant.SAVE_PICTURE.getPath() + filename).mkdir();
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }*/
}
