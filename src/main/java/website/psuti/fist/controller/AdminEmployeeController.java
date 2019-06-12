package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.NameDepartmentConstant;
import website.psuti.fist.model.Employee;
import website.psuti.fist.service.EmployeeService;

@Controller
public class AdminEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping(value = "/admin/table/employee/add/submit", method = RequestMethod.POST)
    public String addEmployeeSubmit(@ModelAttribute("employee") Employee employee) {
        employeeService.add(employee);
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/employee/update", method = RequestMethod.GET)
    public ModelAndView updateEmployee() {
        ModelAndView modelAndView = new ModelAndView("adminTableUpdateEmployee");
        modelAndView.addObject("item", new Employee());
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("employees", employeeService.getAll());
        modelAndView.addObject("nameDepartments", NameDepartmentConstant.values());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/table/employee/update/submit", method = RequestMethod.POST)
    public String adminEmployeeUpdateSubmit(@ModelAttribute("item") Employee item) {
        employeeService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }

    @RequestMapping(value = "/admin/table/employee/delete/id={id}", method = RequestMethod.GET)
    public String adminEmployeeDelete(@PathVariable("id") Long id) {
        //Employee newsOfFaculty = employeeService.findById(id);
        employeeService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }
}
