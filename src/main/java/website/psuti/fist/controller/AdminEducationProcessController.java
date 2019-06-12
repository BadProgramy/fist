package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.service.EducationProcessService;

@Controller
public class AdminEducationProcessController {

    @Autowired
    private EducationProcessService educationProcessService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping(value = "/admin/table/educationProcess/update", method = RequestMethod.GET)
    public ModelAndView updateEducationProcess() {
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateEducationProcess");
        modelAndView.addObject("item", new EducationProcess());
        modelAndView.addObject("educationProcesses", educationProcessService.getAll());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/table/educationProcess/update/submit", method = RequestMethod.POST)
    public String adminEducationProcessUpdateSubmit(@ModelAttribute("item") EducationProcess item) {
        educationProcessService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/educationProcess/delete/id={id}", method = RequestMethod.GET)
    public String adminEducationProcessDelete(@PathVariable("id") long id) {
        //NewsOfFaculty newsOfFaculty = newsFacultyService.findById(id);
        educationProcessService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }
}
