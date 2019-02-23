package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.Extension;
import website.psuti.fist.model.File;
import website.psuti.fist.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class AdminFileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping("/admin/table/files/update")
    public ModelAndView addFile() {
        ModelAndView modelAndView = new ModelAndView("adminUpdateFile");
        modelAndView.addObject("file", new File());
        modelAndView.addObject("files", fileService.getAll());
        modelAndView.addObject("extensions", Extension.values());
        return modelAndView;
    }

    @RequestMapping("/admin/table/files/add/submit")
    public String addFileSubmit(@ModelAttribute File file, Model model ) throws IOException {
        model.addAttribute("file", new File());
        model.addAttribute("files", fileService.getAll());
        model.addAttribute("extensions", Extension.values());
        file.setDate(LocalDate.parse(file.getDateStringLocalDate()));
        file.setUniqueName(UUID.randomUUID().toString());
        file.setName(file.getFile().getOriginalFilename());
        if (file.getFile() != null) {
            writeFile(file.getFile().getBytes(), file.getUniqueName());
        }
        fileService.insert(file);
        return "redirect:../update";
    }

    @RequestMapping("/admin/table/files/update/submit")
    public String updateFileSubmit(@ModelAttribute File file) throws IOException {
        file.setDate(LocalDate.parse(file.getDateStringLocalDate()));
        if (!file.getFile().isEmpty()) {
            if (file.getName().equals(""))
                file.setName(file.getFile().getOriginalFilename());
            file.setUniqueName(UUID.randomUUID().toString());
            writeFile(file.getFile().getBytes(), file.getUniqueName());
        } /*else {
            File temp = fileService.findFileById(file.getId());
            file.setName(temp.getName());
            file.setUniqueName(temp.getUniqueName());
        }*/
        fileService.update(file);
        return "redirect:../update";
    }

    @RequestMapping("/admin/table/files/delete/id={id}")
    public String adminPictureDelete(@PathVariable("id") Long id) {
        File file = fileService.findFileById(id);
        fileService.delete(id);
        return "redirect:../update";
    }


    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_FILE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }

    @RequestMapping(value = "/files/id={idFile}")
    public String file(@PathVariable("idFile") Long idFile, HttpServletRequest request) throws IOException {
        return getFile((website.psuti.fist.model.File) modelAndViewConfiguration.getItemById(modelAndViewConfiguration.getFiles(), idFile), request);
    }

    private String getFile(website.psuti.fist.model.File file, HttpServletRequest request) throws IOException {
        switch (file.getExtension()) {
            case APPLICATION_PDF_VALUE: return "redirect:" + file.getUniqueName();// предпосылка outputPDFFile() \|/
        }
        return "redirect";
    }

    @ResponseBody
    @RequestMapping(value = "/files/{fileNameUnique}", produces = MediaType.APPLICATION_PDF_VALUE)
    private byte[] outputPDFFile(@PathVariable("fileNameUnique") String fileNameUnique) throws IOException {
        java.io.File ff = new java.io.File(PathConstant.SAVE_FILE.getPath() + fileNameUnique);
        return Files.readAllBytes(ff.toPath());
    }
}
