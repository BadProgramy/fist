package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.BestStudent;
import website.psuti.fist.model.KeyPicture;
import website.psuti.fist.model.Pictures;
import website.psuti.fist.service.BestStudentService;
import website.psuti.fist.service.PicturesService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class AdminBestStudentsController {
    @Autowired
    private BestStudentService bestStudentService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private PicturesService picturesService;

    @RequestMapping(value = "/admin/content/bestStudents", method = RequestMethod.GET)
    public ModelAndView bestStudents() {
        ModelAndView modelAndView = new ModelAndView("adminBestStudents");
        modelAndView.addObject("bestStudents",  bestStudentService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/bestStudents/add", method = RequestMethod.GET)
    public ModelAndView addBestStudent() {
        ModelAndView modelAndView = new ModelAndView("adminBestStudentsAdd");
        modelAndView.addObject("bestStudent", new BestStudent());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/bestStudents/add/submit", method = RequestMethod.POST)
    public String addBestStudentSubmit(@ModelAttribute BestStudent bestStudent ) throws IOException {
        if (bestStudent.getPictureFile() != null) {
            bestStudent.setIdPicture(savePicture(bestStudent));
        }
        bestStudentService.insert(bestStudent);
        return "redirect:../";
    }

    @RequestMapping(value = "/admin/content/bestStudents/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateBestStudent(@PathVariable("id") Long id/*, Model model*/) {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        ModelAndView modelAndView = new ModelAndView("adminBestStudentsUpdate");
        BestStudent student = bestStudentService.findById(id);
        student.setCharacteristic(student.getCharacteristic().replace("<br>","\r\n"));
        modelAndView.addObject("bestStudent", student);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/content/bestStudents/update/submit", method = RequestMethod.POST)
    public String updateBestStudent(@ModelAttribute BestStudent bestStudent) throws IOException {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        if (!bestStudent.getPictureFile().isEmpty()) {
            picturesService.delete(bestStudent.getIdPicture());
            bestStudent.setIdPicture(savePicture(bestStudent));
        }
        /*if (Boolean.valueOf(checkDeletePicture)) {
            picturesService.delete(newFaculty.getIdPicture());
            newFaculty.setIdPicture(-1);
        }*/
        bestStudentService.update(bestStudent);
        return "redirect:../../bestStudents";
    }

    @RequestMapping(value = "/admin/content/bestStudents/delete/{id}",method = RequestMethod.GET)
    public String deleteBestStudent(@PathVariable("id") long bestStudentId, Model model) {
/*        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }*/
        picturesService.delete(bestStudentService.findById(bestStudentId).getIdPicture());
        bestStudentService.delete(bestStudentId);
        model.addAttribute("bestStudents", bestStudentService.getAll());
        return "redirect:../../bestStudents";
    }

    @RequestMapping(value = "/admin/table/bestStudent/update", method = RequestMethod.GET)
    public ModelAndView updateBestStudent() {
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateBestStudent");
        modelAndView.addObject("item", new BestStudent());
        modelAndView.addObject("bestStudents", bestStudentService.getAll());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/table/bestStudent/update/submit", method = RequestMethod.POST)
    public String adminBestStudentUpdateSubmit(@ModelAttribute("item") BestStudent item) {
        bestStudentService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/bestStudent/delete/id={id}", method = RequestMethod.GET)
    public String adminBestStudentDelete(@PathVariable("id") Long id) {
        //BestStudent bestStudent = bestStudentService.findById(id);
        picturesService.delete(id);
        return "redirect:../update";
    }

    private long savePicture(BestStudent bestStudent) throws IOException {
        /*if (!bestStudent.getPictureFile().isEmpty())
            writeFile(bestStudent.getPictureFile().getBytes(), bestStudent.getPictureFile().getOriginalFilename());*/
        Pictures pictures = new Pictures();
        pictures.setUrlPicture(PathConstant.SAVE_PICTURE_BEST_STUDENT.getPath() + bestStudent.getPictureFile().getOriginalFilename());
        pictures.setIdPage(2);
        pictures.setKeyPicture(KeyPicture.BEST_STUDENT);
        pictures.setPictureFile(bestStudent.getPictureFile().getBytes());
        pictures.setNamePicture(bestStudent.getPictureFile().getOriginalFilename());
        return picturesService.insert(pictures);
    }

    /*private void writeFile(byte[] buffer, String filename) throws IOException {
        //new File(PathConstant.SAVE_PICTURE_BEST_STUDENT.getPath() + filename).mkdir();
        FileOutputStream fos = new FileOutputStream(PathConstant.SAVE_PICTURE_BEST_STUDENT.getPath() + filename);
        // перевод строки в байты
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }*/
}
