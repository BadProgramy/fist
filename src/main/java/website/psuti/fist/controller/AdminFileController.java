package website.psuti.fist.controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.FileConstant;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.Extension;
import website.psuti.fist.model.File;
import website.psuti.fist.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminFileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping(value = "/admin/table/files/update", method = RequestMethod.GET)
    public ModelAndView addFile(Model model) {
        return addFilePage(1, model);
    }

    @RequestMapping(value = "/admin/table/files/update/page/{idPage}", method = RequestMethod.GET)
    public ModelAndView addFilePage(@PathVariable int idPage, Model model) {
        return getModelForPagination(idPage, model, "adminTableUpdateFile");
    }

    @RequestMapping(value = "/admin/content/files/add", method = RequestMethod.GET)
    public ModelAndView addContentFile(Model model) {
        return addContentFilePage(1, model);
    }

    @RequestMapping(value = "/admin/content/files/add/page/{idPage}", method = RequestMethod.GET)
    public ModelAndView addContentFilePage(@PathVariable int idPage, Model model) {
        return getModelForPagination(idPage, model, "adminContentAddFile");
    }

    private ModelAndView getModelForPagination(int idPage, Model model, String viewName) {
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        List<File> files = fileService.getAll();
        model.addAttribute("pageCount", (int)(Math.ceil((double) files.size() / FileConstant.COUNT_FILE_FOR_OUTPUT_PAGE.getCount())));
        List<File> resultFiles = new ArrayList<>();
        for (int i = (idPage - 1) * FileConstant.COUNT_FILE_FOR_OUTPUT_PAGE.getCount(), j = 0; i < files.size() && j < FileConstant.COUNT_FILE_FOR_OUTPUT_PAGE.getCount(); i++, j++) {
            resultFiles.add(files.get(i));
        }
        model.addAttribute("files", resultFiles);
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("file",  new File());
        modelAndView.addObject("extensions", Extension.values());
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/table/files/add/submit", method = RequestMethod.POST)
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
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/content/files/add/submit", method = RequestMethod.POST)
    public String addContentFileSubmit(@ModelAttribute File file, Model model) throws IOException {
        addFileSubmit(file, model);
        return "redirect:../add";
    }

    @RequestMapping(value = "/admin/table/files/update/submit", method = RequestMethod.POST)
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
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/files/delete/id={id}", method = RequestMethod.GET)
    public String adminPictureDelete(@PathVariable("id") Long id) {
        File file = fileService.findFileById(id);
        fileService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }


    private void writeFile(byte[] buffer, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_FILE.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }

    @RequestMapping(value = "/files/id={idFile}", method = RequestMethod.GET)
    public String file(@PathVariable("idFile") Long idFile, HttpServletRequest request) throws IOException {
        return getFile((website.psuti.fist.model.File) modelAndViewConfiguration.getItemById(modelAndViewConfiguration.getFiles(), idFile), request);
    }

    private String getFile(website.psuti.fist.model.File file, HttpServletRequest request) throws IOException {
        switch (file.getExtension()) {
            case APPLICATION_PDF_VALUE: return "redirect:../files/pdf/" + file.getUniqueName();// предпосылка outputPDFFile() \|/
            default: return "redirect:../files/download/" + file.getUniqueName();// предпосылка outputPDFFile() \|/
        }
    }

    @ResponseBody
    @RequestMapping(value = "/files/pdf/{fileNameUnique}", produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    private byte[] outputPDFFile(@PathVariable("fileNameUnique") String fileNameUnique) throws IOException {
        java.io.File ff = new java.io.File(PathConstant.SAVE_FILE.getPath() + fileNameUnique);
        return Files.readAllBytes(ff.toPath());
    }

    @ResponseBody
    @RequestMapping(value = "/files/download/{fileNameUnique}", produces = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.GET)
    private byte[] outputDownloadFile(@PathVariable("fileNameUnique") String fileNameUnique, HttpServletResponse response) throws IOException {
        java.io.File ff = new java.io.File(PathConstant.SAVE_FILE.getPath() + fileNameUnique);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileService.findFileByNameUnique(fileNameUnique).getName());
        return Files.readAllBytes(ff.toPath());
    }
}
