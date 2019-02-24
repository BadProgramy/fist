package website.psuti.fist.dao.employee;

import org.apache.ibatis.annotations.Mapper;
import website.psuti.fist.model.Employee;

import java.util.List;

@Mapper
public interface DAOEmployee {
    List<Employee> getAll();
    long insert (Employee employee);
    void update(Employee employee);
    void delete(long id);

    List<Employee> findEmployeesByNameDepartment(String nameDepartment);
}
