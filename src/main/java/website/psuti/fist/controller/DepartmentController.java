package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.NameDepartmentConstant;
import website.psuti.fist.model.Department;
import website.psuti.fist.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping("/faculty/cathedras")
    public ModelAndView cathedras() {
        List<HTMLOutputDepartmentForThymeleaf> departments = new ArrayList<>();
        for (Department department: modelAndViewConfiguration.getDepartments()) {
            for (Employee employee: modelAndViewConfiguration.getEmployees()) {
                if (department.getIdMainEmployee() == employee.getId() &&
                    employee.getNameDepartment().equals(NameDepartmentConstant.DEPARTMENT.getName())) {
                    departments.add(new HTMLOutputDepartmentForThymeleaf(department.getName(),
                            employee.getQualificationBriefly() + " " + employee.getName(), department.getAddress(),
                            employee.getPhone(), employee.getEmail(), employee.getIdPictureMinor()));
                }
            }
        }
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.addObject("departments", departments);
        modelAndView.setViewName("cathedras");
        return modelAndView;
    }

    class HTMLOutputDepartmentForThymeleaf {
        private String nameDepartment;
        private String employee;
        private String address;
        private String phone;
        private String email;
        private long idPicture;

        public HTMLOutputDepartmentForThymeleaf(String nameDepartment, String employee, String address, String phone, String email, long idPicture) {
            this.nameDepartment = nameDepartment;
            this.employee = employee;
            this.address = address;
            this.phone = phone;
            this.email = email;
            this.idPicture = idPicture;
        }

        public String getNameDepartment() {
            return nameDepartment;
        }

        public void setNameDepartment(String nameDepartment) {
            this.nameDepartment = nameDepartment;
        }

        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getIdPicture() {
            return idPicture;
        }

        public void setIdPicture(long idPicture) {
            this.idPicture = idPicture;
        }
    }
}