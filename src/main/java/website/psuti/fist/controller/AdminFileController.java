package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.model.Extension;
import website.psuti.fist.model.File;
import website.psuti.fist.service.FileService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class AdminFileController {
    @Autowired
    private FileService fileService;

    @RequestMapping("/admin/files/add")
    public ModelAndView addFile() {
        ModelAndView modelAndView = new ModelAndView("adminUpdateFile");
        modelAndView.addObject("file", new File());
        modelAndView.addObject("extensions", Extension.values());
        return modelAndView;
    }

    @RequestMapping("/admin/files/add/submit")
    public String addNewFacultySubmit(@ModelAttribute File file ) throws IOException {
        file.setDate(LocalDate.parse(file.getDateStringLocalDate()));
        file.setUniqueNameUUID(UUID.randomUUID().toString());
        file.setName(file.getFile().getOriginalFilename());
        if (file.getFile() != null) {
            writeFile(file.getFile().getBytes(), file.getUniqueNameUUID());
        }
        fileService.insert(file);
        return "redirect:../";
    }

    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream("src\\main\\resources\\files\\" + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }
}
