package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.constant.PictureConstant;
import website.psuti.fist.model.KeyPicture;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.PicturesService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminPictureController {
    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;



    @RequestMapping(value = "/admin/table/picture/add/submit", method = RequestMethod.POST)
    public String adminAddPicture(@ModelAttribute("picture") Pictures picture) throws IOException {
        picturesService.insert(updatePicture(picture));
        modelAndViewConfiguration.initModelAndView();
        return returnPage(picture);
    }

    @RequestMapping(value = "/admin/table/picture/department/update", method = RequestMethod.GET)
    public String adminPictureDepartmentUpdate( Model model) {
        return pagination("department", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/bestStudent/update", method = RequestMethod.GET)
    public String adminPictureBestStudentUpdate( Model model) {
        return pagination("bestStudent", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/deanTeam/update", method = RequestMethod.GET)
    public String adminPictureDeanTeamUpdate(Model model) {
        return pagination("deanTeam", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/topicFaculty/update", method = RequestMethod.GET)
    public String adminPictureTopicFacultyUpdate(Model model) {
        return pagination("topicFaculty", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/other/update", method = RequestMethod.GET)
    public String adminPictureOtherUpdate(Model model) {
        return pagination("other", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/diploma/update", method = RequestMethod.GET)
    public String adminPictureDiplomaUpdate(Model model) {
        return pagination("diploma", 1, model);
    }

    @RequestMapping(value = "/admin/table/picture/update/submit", method = RequestMethod.POST)
    public String adminPictureUpdateSubmit(@ModelAttribute("item") Pictures item) throws IOException {
        picturesService.update(updatePicture(item));
        modelAndViewConfiguration.initModelAndView();
        return returnPage(item);
    }

    @RequestMapping(value = "/admin/table/picture/delete/id={id}", method = RequestMethod.GET)
    public String adminPictureDelete(@PathVariable("id") Long id) {
        Pictures picture = picturesService.findPictureById(id);
        picturesService.delete(id);
        return returnPage(picture);
    }

    private String returnPage(Pictures item) {
        switch (item.getKeyPicture()) {
            case BEST_STUDENT: return "redirect:../bestStudent/update";//adminPictureBestStudentUpdate(model);
            case TOPIC_FACULTY: return "redirect:../topicFaculty/update";//adminPictureTopicFacultyUpdate(model);
            case DEAN_TEAM: return "redirect:../deanTeam/update";//adminPictureDeanTeamUpdate(model);
            case DEPARTMENT: return "redirect:../department/update";//adminPictureDepartmentUpdate(model);
            case DIPLOMAS: return "redirect:../diploma/update";//adminPictureDiplomaUpdate(model);
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

    @RequestMapping(value = "/admin/table/picture/{name}/update/page/{idPage}", method = RequestMethod.GET)
    private String pagination(@PathVariable String name, @PathVariable Integer idPage, Model model) {
        if (idPage <= 0) idPage = 1;
        KeyPicture keyPicture = KeyPicture.OTHER;
        switch (name) {
            case "diploma" : {
                keyPicture = KeyPicture.DIPLOMAS;
                model.addAttribute("nameApi", "diploma");
                break;
            }
            case "other" : {
                keyPicture = KeyPicture.OTHER;
                model.addAttribute("nameApi", "other");
                break;
            }
            case "topicFaculty" : {
                keyPicture = KeyPicture.TOPIC_FACULTY;
                model.addAttribute("nameApi", "topicFaculty");
                break;
            }
            case "deanTeam" : {
                keyPicture = KeyPicture.DEAN_TEAM;
                model.addAttribute("nameApi", "deanTeam");
                break;
            }
            case "bestStudent" : {
                keyPicture = KeyPicture.BEST_STUDENT;
                model.addAttribute("nameApi", "bestStudent");
                break;
            }
            case "department" : {
                keyPicture = KeyPicture.DEPARTMENT;
                model.addAttribute("nameApi", "department");
                break;
            }
        }
        model.addAttribute("firstPage", idPage);
        List<Pictures> pictures = picturesService.findPicturesByKey(keyPicture);
        model.addAttribute("pageCount", (int)(Math.ceil((double) pictures.size() / PictureConstant.COUNT_PICTURES_FOR_OUTPUT.getCount())));
        List<Pictures> resultPictures = new ArrayList<>();
        for (int i = (idPage - 1) * PictureConstant.COUNT_PICTURES_FOR_OUTPUT.getCount(), j = 0; i < pictures.size() && j < PictureConstant.COUNT_PICTURES_FOR_OUTPUT.getCount(); i++, j++) {
            resultPictures.add(pictures.get(i));
        }
        model.addAttribute("pictures", resultPictures);
        model.addAttribute("picture", new Pictures());
        model.addAttribute("keyPictures", KeyPicture.values());
        model.addAttribute("item", new Pictures());
        return "adminTableUpdatePicture";
    }

/*    private void writeFile(byte[] buffer, String filename) throws IOException {
        //new File(PathConstant.SAVE_PICTURE.getPath() + filename).mkdir();
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }*/
}
