package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.Department;
import website.psuti.fist.service.DepartmentService;

@Controller
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/admin/table/department/add/submit", method = RequestMethod.POST)
    public String addDepartmentSubmit(@ModelAttribute("newDepartment") Department newDepartment) {
        departmentService.add(newDepartment);
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/department/update", method = RequestMethod.GET)
    public ModelAndView updateDepartment() {
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateDepartment");
        modelAndView.addObject("item", new Department());
        modelAndView.addObject("newDepartment", new Department());
        modelAndView.addObject("departments", departmentService.getAll());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/table/department/update/submit", method = RequestMethod.POST)
    public String adminDepartmentUpdateSubmit(@ModelAttribute("item") Department item) {
        departmentService.update(item);
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/department/delete/id={id}", method = RequestMethod.GET)
    public String adminDepartmentDelete(@PathVariable("id") long id) {
        //NewsOfFaculty newsOfFaculty = newsFacultyService.findById(id);
        departmentService.delete(id);
        return "redirect:../update";
    }
}
