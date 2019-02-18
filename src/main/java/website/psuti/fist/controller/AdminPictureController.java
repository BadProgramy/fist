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
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.PicturesService;

import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/admin/picture/update")
    public String adminMainPageNavigationUpdate(Model model) {
        model.addAttribute("pictures", picturesService.getAll());
        model.addAttribute("item", new Pictures());
        return "adminUpdatePicture";
    }

    @RequestMapping("/admin/picture/update/submit")
    public String adminMainPageNavigationUpdateSubmit(@ModelAttribute("item") Pictures item) throws IOException {
        ModelAndView modelAndView = new ModelAndView("adminUpdatePicture");
        modelAndView.addObject("pictures", picturesService.getAll() );
        modelAndView.addObject("item", new Pictures());
        item.setPictureFile(item.getPictureFileMultipart().getBytes());
        item.setNamePicture(item.getPictureFileMultipart().getOriginalFilename());
        item.setUrlPicture(PathConstant.SAVE_PICTURE +item.getNamePicture());
        writeFile(item.getPictureFile(), item.getNamePicture());
        item.setDate(LocalDate.parse(item.getDateStringLocalDate()));
        picturesService.update(item);
        return "redirect:../update";
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
        response.setHeader("cache-control", "max-age=86400000, must-revalidate");
        return b;
    }

    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }
}
