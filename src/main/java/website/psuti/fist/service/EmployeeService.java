package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.psuti.fist.dao.employee.DAOEmployee;
import website.psuti.fist.model.Employee;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private DAOEmployee daoEmployee;

    public List<Employee> findEmployeesByNameDepartment(String nameDepartment) {
        return daoEmployee.findEmployeesByNameDepartment(nameDepartment);
    }

    public List<Employee> getAll() {
        return daoEmployee.getAll();
    }

    public void update(Employee employee) {
        daoEmployee.update(employee);
    }

    public void delete(Long id) {
        daoEmployee.delete(id);
    }
}
