package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.constant.PictureConstant;
import website.psuti.fist.model.KeyPicture;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.PicturesService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminDiplomasController {

    @Autowired
    private PicturesService picturesService;

    @RequestMapping("/admin/content/diplomas")
    public ModelAndView adminDiplomas(Model model) {
        return adminDiplomasPage(1, model);
    }

    @RequestMapping("/admin/content/diplomas/page/{idPage}")
    public ModelAndView adminDiplomasPage(@PathVariable int idPage, Model model) {
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        List<Pictures> diplomas = picturesService.findPicturesByKey(KeyPicture.DIPLOMAS);
        model.addAttribute("pageCount", (int)(Math.ceil((double) diplomas.size() / PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount())));
        List<Pictures> resultDiplomas = new ArrayList<>();
        for (int i = (idPage - 1) * PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(), j = 0; i < diplomas.size() && j < PictureConstant.COUNT_DIPLOMAS_FOR_OUTPUT.getCount(); i++, j++) {
            resultDiplomas.add(diplomas.get(i));
        }
        model.addAttribute("diploms", resultDiplomas);
        ModelAndView modelAndView = new ModelAndView("adminContentDiplom");
        modelAndView.addObject("picture",  new Pictures());
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }



    @RequestMapping("/admin/content/diplomas/add/submit")
    public String addDiplomasSubmit(@ModelAttribute Pictures picture ) throws IOException {
        picturesService.insert(updatePicture(picture));
        return "redirect:../";
    }

    @RequestMapping("/admin/content/diplomas/update/submit")
    public String adminDiplomasUpdateSubmit(@ModelAttribute("item") Pictures item) throws IOException {
        picturesService.update(updatePicture(item));
        return "redirect:../";
    }

    @RequestMapping("/admin/content/diplomas/delete/{id}")
    public String adminDiplomasDelete(@PathVariable("id") Long id) {
        picturesService.delete(id);
        return "redirect:../";
    }

    @RequestMapping(value = "/admin/content/diplomas/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCandidate(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("adminContentDiplomUpdate");
        Pictures diplom = picturesService.findPictureById(id);
        modelAndView.addObject("diplom", diplom);
        return modelAndView;
    }

    private Pictures updatePicture(Pictures item) throws IOException {
        if (!item.getPictureFileMultipart().isEmpty()) {
            item.setPictureFile(item.getPictureFileMultipart().getBytes());
            item.setNamePicture(item.getPictureFileMultipart().getOriginalFilename());
        } else {
            item.setPictureFile(picturesService.findPictureById(item.getId()).getPictureFile());
        }
        item.setUrlPicture(PathConstant.SAVE_PICTURE.getPath() +item.getNamePicture());
        item.setDate(LocalDate.parse(item.getDateStringLocalDate()));
        item.setKeyPicture(KeyPicture.DIPLOMAS);
        item.setIdPage(-1);
        return item;
    }
}
