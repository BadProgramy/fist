package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.Department;
import website.psuti.fist.service.DepartmentService;

@Controller
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping("/admin/table/department/update")
    public ModelAndView updateDepartment() {
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateDepartment");
        modelAndView.addObject("item", new Department());
        modelAndView.addObject("departments", departmentService.getAll());
        return modelAndView;
    }


    @RequestMapping("/admin/table/department/update/submit")
    public String adminDepartmentUpdateSubmit(@ModelAttribute("item") Department item) {
        departmentService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping("/admin/table/department/delete/id={id}")
    public String adminDepartmentDelete(@PathVariable("id") long id) {
        //NewsOfFaculty newsOfFaculty = newsFacultyService.findById(id);
        departmentService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }
}
