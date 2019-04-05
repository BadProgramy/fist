package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.psuti.fist.dao.department.DAODepartment;
import website.psuti.fist.model.Department;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DAODepartment daoDepartment;

    public List<Department> getAll() { return daoDepartment.getAll(); }

    public void update(Department department) {
        daoDepartment.update(department);
    }

    public void delete(long id) {
        daoDepartment.delete(id);
    }

    public long add(Department newDepartment) {
        return daoDepartment.insert(newDepartment);
    }
}
